import XCTest

class UserRegisterTests: XCTestCase {
    
    let app = XCUIApplication()
    
    override func setUp() {
        super.setUp()
        continueAfterFailure = false
        app.launch()
        
        logout()
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testRegisterUser() {
        let nickname = temporaryNickname()
        let email    = temporaryMailAddress()
        let password = temporaryPassword()
        
        // Input email & password & nickname.
        registerUser(nickname: nickname, email: email, password: password)
        
        waitExists(for: UserStatus.logoutButton)
        
        // Check registered nickname & email is displayed.
        XCTAssertEqual(UserStatus.nicknameLabel.label, nickname, "Registered nickname is displayed.")
        XCTAssertEqual(UserStatus.emailLabel.label, email, "Registered email is displayed.")
        
        // Check logout button is displayed.
        XCTAssertTrue(UserStatus.logoutButton.isHittable, "Logout button is displayed.")
    }
    
    func testRegisterUserWithEmpty() {
        // Input empty email & password & nickname.
        registerUser(nickname: "", email: "", password: "")
        
        // Check error message is displayed properly in each form.
        let expectMessage = Localized().string(forKey: "error_required")
        XCTAssertEqual(UserRegister.nicknameErrorLabel.label, expectMessage, "Error message is displayed properly.")
        XCTAssertEqual(UserRegister.emailErrorLabel.label, expectMessage, "Error message is displayed properly.")
        XCTAssertEqual(UserRegister.passwordErrorLabel.label, expectMessage, "Error message is displayed properly.")
    }
    
    func testRegisterUserWithAlreadyRegisteredEmail() {
        // Input already registered email.
        registerUser(nickname: temporaryNickname(),
                     email: RegisteredUserEmail,
                     password: temporaryPassword())
        
        waitHittable(for: UserRegister.errorMessageLabel)
        
        // Check error message is displayed properly.
        let expectMessage = Localized().string(forKey: "error_used_email")
        XCTAssertEqual(UserRegister.errorMessageLabel.label, expectMessage, "Error message is displayed properly.")
    }
    
}
