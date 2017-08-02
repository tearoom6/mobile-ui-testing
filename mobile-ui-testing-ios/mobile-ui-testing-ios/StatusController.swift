import UIKit
import Firebase

class StatusController: UIViewController {
    
    @IBOutlet weak var nickname: UILabel!
    @IBOutlet weak var email: UILabel!
    
    @IBOutlet weak var logoutButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.configureLocalize()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.updateView()
        
        Auth.auth().addStateDidChangeListener { [weak self] (_, user: User?) in
            if let _ = user {
                self?.updateView()
            } else {
                self?.clearView()
            }
        }
    }
    
    // MARK: - Action
    
    @IBAction func tapLogout(_ sender: Any) {
        self.logout()
    }
    
    // MARK: - Private
    
    private func configureLocalize() {
        self.logoutButton.setTitle(NSLocalizedString("logout", comment: "logout"), for: .normal)
    }
    
    private func updateView() {
        guard let user = Auth.auth().currentUser else { return }
        
        self.nickname.text = user.displayName
        self.email.text = user.email
    }
    
    private func clearView() {
        self.nickname.clear()
        self.email.clear()
    }
    
    /// Logout.
    private func logout() {
        do {
            try Auth.auth().signOut()
        } catch let error {
            self.showErrorDialog(message: error.localizedDescription)
            return
        }
    }
    
}
