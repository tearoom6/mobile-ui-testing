import XCTest

class UserLoginTests: XCTestCase {
    
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
    
    func testLoginAsRegisteredAccount() {
        loginAsRegisteredUser()
        
        // Check registered nickname & email is displayed.
        XCTAssertEqual(UserStatus.nicknameLabel.label, RegisteredUserNickname, "Registered nickname is displayed")
        XCTAssertEqual(UserStatus.emailLabel.label, RegisteredUserEmail, "Registered email is displayed")
        
        // Check logout button is displayed.
        XCTAssertTrue(UserStatus.logoutButton.isHittable, "Logout button is displayed")
    }
    
    func testLoginWithInvalidPassword() {
        // Login as registered user with invalid password.
        login(email: RegisteredUserEmail, password: "invalid_password")
        
        waitHittable(for: Login.errorMessageLabel)
        
        // Check error message is displayed properly.
        let expectMessage = Localized().string(forKey: "error_login_wrongPassword")
        XCTAssertEqual(Login.errorMessageLabel.label, expectMessage, "Error message is displayed properly.")
    }
    
}
