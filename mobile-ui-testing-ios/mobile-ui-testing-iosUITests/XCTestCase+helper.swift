import Foundation
import XCTest

extension XCTestCase {
    
    func selectTab(_ tab: Tab) {
        XCUIApplication().staticTexts[tab.title].tap()
    }
    
    func registerUser(nickname: String, email: String, password: String) {
        selectTab(.user)
        
        Login.registerButton.tap()
        
        // Input email & password & nickname.
        UserRegister.nicknameText.inputText(nickname)
        UserRegister.emailText.inputText(email)
        UserRegister.passwordText.inputText(password)
        
        // Click register button.
        UserRegister.registerButton.tap()
    }
    
    func loginAsRegisteredUser() {
        logout()
        
        let user = Assets.registeredUser
        login(email: user.email, password: user.password)
        waitHittable(for: UserStatus.logoutButton)
    }
    
    func login(email: String, password: String) {
        selectTab(.user)
        
        // REMARK: 実機では `isHittable` の判定だけで十分だが、シミュレータでは `exists` を先に判定する必要あり
        if Login.loginButton.exists && Login.loginButton.isHittable {
            
            // Input email & password.
            Login.emailText.inputText(email)
            Login.passwordText.inputText(password)
            
            // Click login button.
            Login.loginButton.tap()
        }
    }
    
    func logout() {
        selectTab(.user)
        
        // REMARK: 実機では `isHittable` の判定だけで十分だが、シミュレータでは `exists` を先に判定する必要あり
        if UserStatus.logoutButton.exists && UserStatus.logoutButton.isHittable {
            UserStatus.logoutButton.tap()
            waitHittable(for: Login.loginButton)
        }
    }
    
    func createNote(title: String, content: String) {
        selectTab(.note)
        
        // Click create button.
        Note.createButton.tap()
        
        waitHittable(for: Note.saveButton)
        
        Note.clearButton.tap()
        
        // Input title & content.
        Note.titleText.inputText(title)
        Note.contentTextView.inputText(content)
        
        // Click save button.
        Note.saveButton.tap()
    }
}
