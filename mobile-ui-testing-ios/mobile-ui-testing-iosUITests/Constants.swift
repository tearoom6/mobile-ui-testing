import XCTest

let RegisteredUserEmail = Assets.registeredUser.email
let RegisteredUserPassword = Assets.registeredUser.password
let RegisteredUserNickname = Assets.registeredUser.nickname

enum Tab {
    case user
    case note
    
    var title: String {
        switch self {
        case .user: return "User"
        case .note: return "Note"
        }
    }
}

class Note: ElementAccessor {
    static let prefix = ""
    static let createButton    = button("notes_button_create")
    static let titleText       = textField("note_input_title")
    static let contentTextView = textView("note_input_content")
    static let titleErrorLabel = staticText("note_label_errorTitle")
    static let saveButton      = button("note_button_save")
    static let updateButton    = button("note_button_save")
    static let closeButton     = button("note_button_close")
    static let clearButton     = button("note_button_clear")
    static let needLoginLabel  = staticText("notes_label_unauthorized")
    static let loginButton     = button("notes_button_redirectToLogin")
    static let table           = tableView("notes_list_title")
}

class Login: ElementAccessor {
    static let prefix = "auth_"
    static let emailText         = textField("input_loginEmail")
    static let passwordText      = secureTextFields("input_loginPassword")
    static let loginButton       = button("button_login")
    static let errorMessageLabel = staticText("label_loginErrors")
    static let registerButton    = button("button_openRegister")
}

class UserRegister: ElementAccessor {
    static let prefix = "auth_"
    static let nicknameText       = textField("input_registerNickname")
    static let emailText          = textField("input_registerEmail")
    static let passwordText       = secureTextFields("input_registerPassword")
    static let nicknameErrorLabel = staticText("label_registerErrorNickname")
    static let emailErrorLabel    = staticText("label_registerErrorEmail")
    static let passwordErrorLabel = staticText("label_registerErrorPassword")
    static let errorMessageLabel  = staticText("label_registerErrors")
    static let registerButton     = button("button_register")
}

class UserStatus: ElementAccessor {
    static let prefix = "auth_"
    static let nicknameLabel = staticText("label_nickname")
    static let emailLabel    = staticText("label_email")
    static let logoutButton  = button("button_logout")
}
