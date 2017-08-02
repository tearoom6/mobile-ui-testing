import UIKit
import Firebase

class LoginController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var emailError: UILabel!
    
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var passwordError: UILabel!
    
    @IBOutlet weak var errorMessages: UILabel!
    
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var registerButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.email.delegate = self
        self.password.delegate = self
        
        self.configureLocalize()
        self.clearErrors()
    }
    
    // MARK: - Action
    
    @IBAction func tapLogin(_ sender: Any) {
        self.view.endEditing(true)
        self.clearErrors()
        self.login()
    }
    
    @IBAction func openLogin(_ segue: UIStoryboardSegue) {
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    // MARK: - Private
    
    private func configureLocalize() {
        self.email.placeholder = NSLocalizedString("email", comment: "email")
        self.password.placeholder = NSLocalizedString("password", comment: "password")
        self.loginButton.setTitle(NSLocalizedString("login", comment: "login"), for: .normal)
        self.registerButton.setTitle(NSLocalizedString("register", comment: "register"), for: .normal)
    }
    
    // Login.
    private func login() {
        guard self.validInput() else { return }
        
        guard
            let email = self.email.text,
            let password = self.password.text else { return }
        
        
        self.clearErrors()
        
        self.showNetworkActivityIndicator()
        
        Auth.auth().signIn(withEmail: email, password: password) { [weak self] (user, error) in
            
            self?.hideNetworkActivityIndicator()
            
            // Error
            if let error = error {
                self?.errorMessages.text = Auth.localizedErrorMessage(from: error, for: .login)
                return
            }
            
            // Success
            if let _ = user {
                self?.clearErrors()
            }
        }
    }
    
    /// Validate input value.
    ///
    /// - Returns: true if valid
    private func validInput() -> Bool {
        
        var valid = true
        
        // Validate email.
        switch self.email.validate(rule: EmailValidationRule()) {
        case .invalid(let errors):
            emailError.text = errors.first?.message
            valid = false
        case .valid:
            break
        }
        
        // Validate password.
        switch self.password.validate(rule: PasswordValidationRule()) {
        case .invalid(let errors):
            passwordError.text = errors.first?.message
            valid = false
        case .valid:
            break
        }
        
        return valid
    }
    
    private func clearErrors() {
        self.emailError.clear()
        self.passwordError.clear()
        self.errorMessages.clear()
    }
}
