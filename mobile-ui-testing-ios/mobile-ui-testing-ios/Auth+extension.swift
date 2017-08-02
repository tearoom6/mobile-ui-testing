import Foundation
import Firebase

extension Auth {
    
    enum OperationType {
        case login
        case register
    }
    
    /// Parse Firebase error to app error message.
    ///
    /// - Parameters:
    ///   - error: Firebase error
    ///   - operationType: Operation type
    /// - Returns: app error message
    static func localizedErrorMessage(from error: Error, for operationType: OperationType) -> String {
        typealias ErrorArgs = (key: String, comment: String)
        
        let args: ErrorArgs
        
        let loginFailedArgs    = ErrorArgs(key: "error_login_failed", comment: "Failed to login.")
        let registerFailedArgs = ErrorArgs(key: "error_register_failed", comment: "Failed to register user.")
        let invalidEmailArgs   = ErrorArgs(key: "error_invalidEmail", comment: "Invalid email.")
        
        switch operationType {
        case .login:
            guard let errorCode = AuthErrorCode(rawValue: error._code) else {
                return NSLocalizedString(loginFailedArgs.key, comment: loginFailedArgs.comment)
            }
            
            switch errorCode {
            case .invalidEmail:
                args = invalidEmailArgs
            case .userDisabled:
                args = ErrorArgs(key: "error_login_userDisabled", comment: "Invalid account.")
            case .wrongPassword:
                args = ErrorArgs(key: "error_login_wrongPassword", comment: "Invalid email or password.")
            case .operationNotAllowed: fallthrough
            default:
                args = loginFailedArgs
            }
            
        case .register:
            guard let errorCode = AuthErrorCode(rawValue: error._code) else {
                return NSLocalizedString(registerFailedArgs.key, comment: registerFailedArgs.comment)
            }
            
            switch errorCode {
            case .invalidEmail:
                args = invalidEmailArgs
            case .emailAlreadyInUse:
                args = ErrorArgs(key: "error_used_email", comment: "This email is already registered.")
            case .weakPassword:
                args = ErrorArgs(key: "error_register_weakPassword", comment: "Too weak password.")
            case .operationNotAllowed: fallthrough
            default:
                args = registerFailedArgs
            }
        }
        
        return NSLocalizedString(args.key, comment: args.comment)
    }
    
}
