import UIKit

struct FieldError {
    
    let filed: UITextField
    let message: String
    
    init(field: UITextField, messageKey: String) {
        self.filed = field
        self.message = NSLocalizedString(messageKey, comment: "Message Key: \(messageKey)")
    }
    
}
