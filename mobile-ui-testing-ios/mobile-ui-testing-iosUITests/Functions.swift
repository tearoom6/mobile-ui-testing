import XCTest

class Assets {
    
    static var registeredUser: User {
        guard let path = Bundle(for: Assets.self).path(forResource: "data", ofType: "plist") else {
            XCTFail("Failed to load asset.")
            return User(nickname: "", email: "", password: "")
        }
        guard
            let userData = NSDictionary(contentsOfFile: path)?.value(forKey: "testuser") as? NSDictionary,
            let nickname = userData.value(forKey: "nickname") as? String,
            let email = userData.value(forKey: "email") as? String,
            let password = userData.value(forKey: "password") as? String
            else {
                XCTFail("Failed to load test user data.")
                return User(nickname: "", email: "", password: "")
        }
        return User(nickname: nickname, email: email, password: password)
    }
    
}

/// Return unixtime.
///
/// - Returns: unixtime
func unixtime() -> TimeInterval {
    return Date().timeIntervalSince1970
}

/// Generate random nickname.
///
/// - Returns: nickname
func temporaryNickname() -> String {
    return "test.\(arc4random())"
}

/// Generate random email.
///
/// - Returns: email
func temporaryMailAddress() -> String {
    return "test+\(unixtime())@example.com"
}

/// Generate random password.
///
/// - Returns: password
func temporaryPassword() -> String {
    return "\(arc4random())"
}

/// Generate random title.
///
/// - Returns: title
func randomTitle() -> String {
    return "Title: \(unixtime())"
}

/// Generate random content.
///
/// - Returns: content
func randomContent() -> String {
    return "Content: \(unixtime())"
}

class Localized {
    
    func string(forKey key: String) -> String {
        let languageCode = NSLocale.current.languageCode
        
        guard
            let languageBundlePath = Bundle(for: type(of: self)).path(forResource: languageCode, ofType: "lproj"),
            let localizationBundle = Bundle(path: languageBundlePath)
            else {
                XCTFail("Failed to load resource.")
                return ""
        }
        return NSLocalizedString(key, bundle: localizationBundle, comment: "")
    }
    
}
