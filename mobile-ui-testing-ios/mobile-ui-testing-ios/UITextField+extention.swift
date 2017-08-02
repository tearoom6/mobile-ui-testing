import UIKit

extension UITextField {
    
    /// Validate containing text.
    ///
    /// - Parameter rule: ValidationRule Validation rule
    /// - Returns: ValidationResult result
    func validate(rule: ValidationRule) -> ValidationResult {
        return rule.validate(textField: self)
    }
    
    func clear() {
        self.text = ""
    }
    
    func trim() {
        self.text = self.text?.trimmingCharacters(in: .whitespaces)
    }
    
}
