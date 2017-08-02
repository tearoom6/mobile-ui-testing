import Foundation
import Firebase

struct Note {
    let key: String?
    let title: String
    let content: String
}

// MARK: - Firebase

extension Note {
    
    static func from(snapshot: DataSnapshot) -> Note? {
        guard
            let dict    = snapshot.value as? [String: String],
            let title   = dict["title"],
            let content = dict["content"] else { return nil }
        
        return Note(key: snapshot.key, title: title, content: content)
    }
    
    func asDictionary() -> [String: String] {
        return [
            "title": title,
            "content": content,
        ]
    }
}
