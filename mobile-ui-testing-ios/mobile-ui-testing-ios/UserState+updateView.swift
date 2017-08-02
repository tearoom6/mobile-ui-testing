extension UserState {
    
    func updateView(for controller: UserController) {
        switch self {
        case .login:
            controller.loginView.isHidden = true
            controller.statusView.isHidden = false
        case .logout:
            controller.loginView.isHidden = false
            controller.statusView.isHidden = true
        }
    }
    
    func updateView(for controller: NoteController) {
        switch self {
        case .login:
            controller.notesView.isHidden = false
            controller.loginRequestView.isHidden = true
        case .logout:
            controller.notesView.isHidden = true
            controller.loginRequestView.isHidden = false
        }
    }
}
