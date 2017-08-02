import UIKit
import Firebase

class UserRegisterController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var nickname: UITextField!
    @IBOutlet weak var nicknameError: UILabel!
    
    
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var emailError: UILabel!
    
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var passwordError: UILabel!
    
    @IBOutlet weak var errorMessages: UILabel!
    
    @IBOutlet weak var registerButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.nickname.delegate = self
        self.email.delegate = self
        self.password.delegate = self
        
        self.configureLocalize()
        self.clearErrors()
    }
    
    // MARK: - Action
    
    @IBAction func tapRegister(_ sender: Any) {
        self.view.endEditing(true)
        self.clearErrors()
        
        self.configureLocalize()
        self.register()
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    // MARK: - Private
    
    private func configureLocalize() {
        self.nickname.placeholder = NSLocalizedString("nickname", comment: "Nickname")
        self.email.placeholder = NSLocalizedString("email", comment: "email")
        self.password.placeholder = NSLocalizedString("password", comment: "password")
        self.registerButton.setTitle(NSLocalizedString("register", comment: "register"), for: .normal)
        self.loginButton.setTitle(NSLocalizedString("login", comment: "login"), for: .normal)
    }
    
    /// Register new user.
    private func register() {
        self.nickname.trim()
        
        guard self.validInput() else { return }
        
        guard
            let nickname = self.nickname.text,
            let email    = self.email.text,
            let password = self.password.text else { return }
        
        self.showNetworkActivityIndicator()
        
        Auth.auth().createUser(withEmail: email, password: password) { [weak self] (user: User?, error: Error?) in
            
            // Error
            if let error = error {
                self?.hideNetworkActivityIndicator()
                self?.errorMessages.text = Auth.localizedErrorMessage(from: error, for: .register)
            }
            
            // Success
            if let user = user {
                
                let request = user.createProfileChangeRequest()
                request.displayName = nickname
                request.commitChanges { (error: Error?) in
                    
                    self?.hideNetworkActivityIndicator()
                    
                    if let error = error {
                        self?.errorMessages.text = error.localizedDescription
                        return
                    }
                    
                    self?.dismiss(animated: true)
                }
                
            }
        }
    }
    
    /// Validate input value.
    ///
    /// - Returns: true if valid
    private func validInput() -> Bool {
        var valid = true
        
        // Validate nickname.
        switch self.nickname.validate(rule: NoEmptyValidationRule()) {
        case .invalid(let errors):
            nicknameError.text = errors.first?.message
            valid = false
        case .valid:
            break
        }
        
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
        self.nicknameError.clear()
        self.emailError.clear()
        self.passwordError.clear()
        self.errorMessages.clear()
    }
}
