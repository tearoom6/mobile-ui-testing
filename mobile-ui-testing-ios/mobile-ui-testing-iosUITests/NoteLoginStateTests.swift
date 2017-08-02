import XCTest

class NoteLoginStateTests: XCTestCase {
    
    let app = XCUIApplication()
    
    override func setUp() {
        super.setUp()
        continueAfterFailure = false
        app.launch()
        
        loginAsRegisteredUser()
        selectTab(.note)
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testCreateNote() {
        // Input random title & content.
        let title = randomTitle()
        let content = randomContent()
        createNote(title: title, content: content)
        
        // Check specified title is registered.
        XCTAssertTrue(app.cells.staticTexts[title].exists, "\(title) is displayed in the TableView.")
    }
    
    func testCreateNoteWithEmptyContent() {
        // Input random title with empty content.
        let title = randomTitle()
        createNote(title: title, content: "")
        
        // Check specified title is registered.
        XCTAssertTrue(app.cells.staticTexts[title].exists, "\(title) is displayed in the TableView.")
    }
    
    func testCreateNoteWithEmptyTitle() {
        // Input random content with empty title.
        createNote(title: "", content: randomContent())
        
        // Check error message is displayed properly.
        let expectMessage = Localized().string(forKey: "error_required")
        XCTAssertEqual(Note.titleErrorLabel.label, expectMessage, "Error message is displayed properly.")
    }
}
