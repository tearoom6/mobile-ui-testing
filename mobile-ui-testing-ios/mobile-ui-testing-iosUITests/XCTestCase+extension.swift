import Foundation
import XCTest

private let DEFAULT_TIMEOUT_SECOND: TimeInterval = 10

extension XCTestCase {
    
    /// Wait until element displayed.
    ///
    /// - Parameters:
    ///   - evaluatedObject: element
    ///   - seconds: timeout (secounds)
    func waitExists(for evaluatedObject: Any, timeout seconds: TimeInterval = DEFAULT_TIMEOUT_SECOND) {
        let predicate = NSPredicate(format: "exists == true")
        let exp = expectation(for: predicate, evaluatedWith: evaluatedObject)
        
        wait(for: [exp], timeout: seconds)
    }
    
    /// Wait until element becomes tappable.
    ///
    /// - Parameters:
    ///   - evaluatedObject: element
    ///   - seconds: timeout (secounds)
    func waitHittable(for evaluatedObject: Any, timeout seconds: TimeInterval = DEFAULT_TIMEOUT_SECOND) {
        let predicate = NSPredicate(format: "isHittable == true")
        let exp = expectation(for: predicate, evaluatedWith: evaluatedObject)
        
        wait(for: [exp], timeout: seconds)
    }
    
}
