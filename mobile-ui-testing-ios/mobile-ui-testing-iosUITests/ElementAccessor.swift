import Foundation
import XCTest

fileprivate let app = XCUIApplication()

/// Extension to access view element simply in XCTest.
protocol ElementAccessor {
    static var prefix: String { get }
}

extension ElementAccessor {
    
    /// Return id with `prefix`.
    ///
    /// - Parameter identifier: id
    /// - Returns: ID文字列
    static func id(_ identifier: String) -> String {
        return Self.prefix + identifier
    }
    
    /// Find button.
    ///
    /// - Parameter identifier: id
    /// - Returns: button
    static func button(_ identifier: String) -> XCUIElement {
        return app.buttons[Self.id(identifier)]
    }
    
    /// Find text field.
    ///
    /// - Parameter identifier: id
    /// - Returns: text field
    static func textField(_ identifier: String) -> XCUIElement {
        return app.textFields[Self.id(identifier)]
    }
    
    /// Find secure text field.
    ///
    /// - Parameter identifier: id
    /// - Returns: secure text field
    static func secureTextFields(_ identifier: String) -> XCUIElement {
        return app.secureTextFields[Self.id(identifier)]
    }
    
    /// Find text view.
    ///
    /// - Parameter identifier: id
    /// - Returns: text view
    static func textView(_ identifier: String) -> XCUIElement {
        return app.textViews[Self.id(identifier)]
    }
    
    /// Find label.
    ///
    /// - Parameter identifier: id
    /// - Returns: label
    static func staticText(_ identifier: String) -> XCUIElement {
        return app.staticTexts[Self.id(identifier)]
    }
    
    /// Find table.
    ///
    /// - Parameter identifier: id
    /// - Returns: table
    static func tableView(_ identifier: String) -> XCUIElement {
        return app.tables[self.id(identifier)]
    }
    
}
