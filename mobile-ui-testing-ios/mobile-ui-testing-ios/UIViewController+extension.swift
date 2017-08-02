import UIKit

extension UIViewController {
    
    /// Show error dialog.
    ///
    /// - Parameter message: String message
    func showErrorDialog(message: String) {
        
        let alert = UIAlertController(title: NSLocalizedString("error", comment: "error"),
                                      message: message,
                                      preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        
        self.present(alert, animated: true)
    }
    
    func showNetworkActivityIndicator() {
        UIApplication.shared.isNetworkActivityIndicatorVisible = true
    }
    
    func hideNetworkActivityIndicator() {
        UIApplication.shared.isNetworkActivityIndicatorVisible = false
    }
    
    func move(to: Tab) {
        let toPage = NSNumber(value: to.page.index)
        NotificationCenter.default.post(name: .movePage,
                                        object: nil,
                                        userInfo: ["toPage" : toPage])
    }
    
}
