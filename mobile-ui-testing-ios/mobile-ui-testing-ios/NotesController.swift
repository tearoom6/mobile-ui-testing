import UIKit
import Firebase

class NoteCell: UITableViewCell {
    @IBOutlet weak var title: UILabel!
}

class NotesController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var notesTable: UITableView!
    @IBOutlet weak var createButton: UIButton!
    
    @IBOutlet weak var formContainer: UIView!
    @IBOutlet weak var formTopMargin: NSLayoutConstraint!
    
    @IBOutlet weak var noteTitle: UITextField!
    @IBOutlet weak var noteTitleError: UILabel!
    @IBOutlet weak var noteContent: UITextView!
    
    @IBOutlet weak var closeButton: UIButton!
    @IBOutlet weak var saveButton: UIButton!
    @IBOutlet weak var clearButton: UIButton!
    
    private let notesRef: DatabaseReference = Database.database().reference().child("notes")
    private var myNoteRef: DatabaseReference?
    private var firebaseNoteObserveHandle: UInt?
    
    private var notes: [Note] = []
    private var selectedIndexPath: IndexPath?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.formTopMargin.constant = 0
        self.clearForm()
        
        Auth.auth().addStateDidChangeListener { [weak self] (auth: Auth, user: User?) in
            if let _ = user {
                self?.observeFirebase()
                self?.startObserveKeyboardNotification()
            } else {
                self?.removeObserveFirebase()
                self?.stopObserveKeyboardNotification()
            }
        }
    }
    
    // MARK: - Action
    
    @IBAction func tapCreate(_ sender: Any) {
        self.saveButton.isHidden = false
        self.showNoteForm()
    }
    
    @IBAction func tapClose(_ sender: Any) {
        self.clearForm()
        self.hideNoteForm()
    }
    
    @IBAction func tapClear(_ sender: Any) {
        self.clearForm()
    }
    
    @IBAction func tapSave(_ sender: Any) {
        self.save()
    }
    
    // MARK: - UITableViewDataSource
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.notes.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "Note") as? NoteCell else { return UITableViewCell() }
        
        let note = self.notes[indexPath.row]
        cell.title.text = note.title
        
        return cell
    }
    
    // MARK: - UITableViewDelegate
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let note = self.notes[indexPath.row]
        
        self.noteTitle.text = note.title
        self.noteTitleError.text = ""
        self.noteContent.text = note.content
        
        self.selectedIndexPath = indexPath
        self.saveButton.isHidden = true
        
        self.view.endEditing(true)
        self.showNoteForm()
    }
    
    // MARK: - Private
    
    private func showNoteForm() {
        self.view.layoutIfNeeded()
        self.formTopMargin.constant = -self.formContainer.bounds.height
        UIView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
    
    private func hideNoteForm() {
        self.view.endEditing(true)
        
        self.view.layoutIfNeeded()
        self.formTopMargin.constant = 0
        UIView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
    
    private func clearForm() {
        self.noteTitle.clear()
        self.noteTitleError.clear()
        self.noteContent.clear()
    }
    
    private func save() {
        guard isInputValid() else { return }
        
        guard
            let title = self.noteTitle.text,
            let content = self.noteContent.text else { return }
        
        let key: String? = self.selectedIndexPath.flatMap { self.notes[$0.row].key }
        
        let note = Note(key: key, title: title, content: content)
        
        self.insert(note: note)
        self.clearForm()
        self.hideNoteForm()
    }
    
    /// Save to Firebase database.
    private func insert(note: Note) {
        guard let user = Auth.auth().currentUser else { return }
        
        let userNoteRef: DatabaseReference = self.notesRef.child(user.uid)
        let value = note.asDictionary()
        
        userNoteRef.childByAutoId().setValue(value)
    }
    
    /// Validate input value.
    ///
    /// - Returns: true if valid
    private func isInputValid() -> Bool {
        var valid = true
        
        // Validate title.
        switch self.noteTitle.validate(rule: NoEmptyValidationRule()) {
        case .invalid(let errors):
            noteTitleError.text = errors.first?.message
            valid = false
        case .valid: break
        }
        
        return valid
    }
    
    private func startObserveKeyboardNotification() {
        NotificationCenter.default.addObserver(self, selector: #selector(willShowKeyboard(notification:)), name: .UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(willHideKeyboard(notification:)), name: .UIKeyboardWillHide, object: nil)
    }
    
    private func stopObserveKeyboardNotification() {
        NotificationCenter.default.removeObserver(self, name: .UIKeyboardWillShow, object: nil)
        NotificationCenter.default.removeObserver(self, name: .UIKeyboardWillHide, object: nil)
    }
    
    @objc private func willShowKeyboard(notification: Notification) {
        guard let duration = notification.duration(), let rect = notification.rect() else { return }
        
        self.view.layoutIfNeeded()
        self.formTopMargin.constant = -(self.formContainer.bounds.height + rect.height)
        UIView.animate(withDuration: duration) {
            self.view.layoutIfNeeded()
        }
    }
    
    @objc private func willHideKeyboard(notification: Notification) {
        guard let duration = notification.duration() else { return }
        
        self.view.layoutIfNeeded()
        self.formTopMargin.constant = 0
        UIView.animate(withDuration: duration) {
            self.view.layoutIfNeeded()
        }
    }
    
    // MARK: - Firebase
    
    private func observeFirebase() {
        guard let user = Auth.auth().currentUser else { return }
        
        let ref = self.notesRef.child(user.uid)
        
        self.myNoteRef = ref
        
        self.firebaseNoteObserveHandle = ref.observe(.value, with: { (snapshot: DataSnapshot) in
            guard let snapshots = snapshot.children.allObjects as? [DataSnapshot] else { return }
            
            self.notes = snapshots.flatMap(Note.from).reversed()
            self.notesTable.reloadData()
        })
    }
    
    private func removeObserveFirebase() {
        guard let ref = self.myNoteRef, let handle = self.firebaseNoteObserveHandle else { return }
        ref.removeObserver(withHandle: handle)
    }
    
}
