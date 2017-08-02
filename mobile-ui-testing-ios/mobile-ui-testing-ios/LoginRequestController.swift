import UIKit

class LoginRequestController: UIViewController {
    
    @IBOutlet weak var unauthorizedLabel: UILabel!
    @IBOutlet weak var loginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.configureLocalize()
    }
    
    // MARK: - Action
    
    @IBAction func tapLogin(_ sender: Any) {
        self.move(to: .User)
    }
    
    // MARK: - Private
    
    private func configureLocalize() {
        self.unauthorizedLabel.text = NSLocalizedString("unauthorized", comment: "Not login status text")
        self.loginButton.setTitle(NSLocalizedString("login", comment: "Login"), for: .normal)
    }
}
