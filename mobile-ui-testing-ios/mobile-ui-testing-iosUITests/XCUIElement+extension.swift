import Foundation
import XCTest

extension XCUIElement {
    
    func inputText(_ text: String) {
        self.tap()
        self.typeText(text)
    }
    
}
