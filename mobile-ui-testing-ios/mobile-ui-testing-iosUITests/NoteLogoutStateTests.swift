import XCTest

class NoteLogoutStateTests: XCTestCase {
    
    let app = XCUIApplication()
    
    override func setUp() {
        super.setUp()
        continueAfterFailure = false
        app.launch()
        
        logout()
        selectTab(.note)
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testNotLogin() {
        // Check not login status is displayed.
        XCTAssertTrue(Note.needLoginLabel.exists && Note.needLoginLabel.isHittable,
                      "Unauthorized label is displayed.")
        
        // Check create button is not displayed.
        XCTAssertFalse(Note.createButton.exists && Note.createButton.isHittable,
                       "Create button is not displayed which should appear after login.")
    }
}
