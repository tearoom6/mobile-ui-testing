import UIKit
import Firebase

class UserController: UIViewController {
    
    @IBOutlet weak var statusView: UIView!
    @IBOutlet weak var loginView:  UIView!
    
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
