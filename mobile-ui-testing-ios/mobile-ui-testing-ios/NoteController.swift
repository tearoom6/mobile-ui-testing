import UIKit
import Firebase

class NoteController: UIViewController {
    
    @IBOutlet weak var notesView: UIView!
    @IBOutlet weak var loginRequestView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        Auth.auth().addStateDidChangeListener { [weak self] (_, user: User?) in
            guard let viewController = self else { return }
            
            if let _ = user {
                UserState.login.updateView(for: viewController)
            } else {
                UserState.logout.updateView(for: viewController)
            }
        }
    }
    
}
