import Foundation
import UIKit

extension Notification.Name {
    static let movePage = Notification.Name("movePage")
}

extension Notification {
    
    func duration() -> TimeInterval? {
        let duration: TimeInterval? = self.userInfo?[UIKeyboardAnimationDurationUserInfoKey] as? Double
        return duration
    }
    
    func rect() -> CGRect? {
        let rowRect: NSValue? = self.userInfo?[UIKeyboardFrameEndUserInfoKey] as? NSValue
        let rect: CGRect? = rowRect?.cgRectValue
        return rect
    }
}
