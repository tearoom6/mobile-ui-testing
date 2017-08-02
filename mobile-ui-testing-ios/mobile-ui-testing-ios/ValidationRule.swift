import UIKit

private let minPasswordLength = 6
private let emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"

protocol ValidationRule {
    
    /// Validate UITextField.
    ///
    /// - Parameter textField: UITextField
    /// - Returns: ValidationResult result
    func validate(textField: UITextField) -> ValidationResult
}

/// Email Validation
struct EmailValidationRule: ValidationRule {
    
    func validate(textField: UITextField) -> ValidationResult {
        guard let text = textField.text, !text.isEmpty else {
            return .invalid([FieldError(field: textField, messageKey: "error_required")])
        }
        
        if !NSPredicate(format:"SELF MATCHES %@", emailRegex).evaluate(with: text) {
            return .invalid([FieldError(field: textField, messageKey: "error_invalid_email")])
        } else {
            return .valid
        }
    }
    
}

/// Password Validation
struct PasswordValidationRule: ValidationRule {
    
    func validate(textField: UITextField) -> ValidationResult {
        guard let text = textField.text, !text.isEmpty else {
            return .invalid([FieldError(field: textField, messageKey: "error_required")])
        }
        
        if text.characters.count < minPasswordLength {
            return .invalid([FieldError(field: textField, messageKey: "error_invalid_password")])
        } else {
            return .valid
        }
    }
    
}

/// Validation for no empty
class NoEmptyValidationRule: ValidationRule {
    
    func validate(textField: UITextField) -> ValidationResult {
        guard let text = textField.text, !text.isEmpty else {
            return .invalid([FieldError(field: textField, messageKey: "error_required")])
        }
        
        return .valid
    }
    
}
