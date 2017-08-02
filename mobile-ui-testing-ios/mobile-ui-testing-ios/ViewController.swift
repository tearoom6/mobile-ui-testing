import UIKit
import PagingMenuController

struct Page {
    let name: String
    let index: Int
}

enum Tab {
    
    case User
    case Note
    
    static let pages: [Page] = [
        Tab.User.page,
        Tab.Note.page
    ]
    
    var page: Page {
        switch self {
        case .User: return Page(name: "User", index: 0)
        case .Note: return Page(name: "Note", index: 1)
        }
    }
}

class ViewController: UIViewController {
    
    var pagingMenuController: PagingMenuController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setPageMenu()
        self.registerNotification()
    }
    
    private func setPageMenu() {
        let options = PagingMenuOptions()
        let pagingMenuController = PagingMenuController(options: options)
        
        self.addChildViewController(pagingMenuController)
        self.view.addSubview(pagingMenuController.view)
        pagingMenuController.didMove(toParentViewController: self)
        
        self.pagingMenuController = pagingMenuController
    }
    
    private func registerNotification() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(changePage(_:)),
                                               name: .movePage,
                                               object: nil)
    }
    
    @objc private func changePage(_ notification: Notification) {
        guard let toPage = notification.userInfo?["toPage"] as? NSNumber else { return }
        self.pagingMenuController?.move(toPage: toPage.intValue)
    }
    
}

private struct PagingMenuOptions: PagingMenuControllerCustomizable {
    
    fileprivate var componentType: ComponentType {
        return .all(menuOptions: MenuOptions(), pagingControllers: pagingControllers)
    }
    
    fileprivate var pagingControllers: [UIViewController] {
        return Tab.pages.map { page -> UIViewController in
            let controller: UIViewController = UIStoryboard(name: page.name, bundle: nil).instantiateInitialViewController()!
            controller.title = page.name
            return controller
        }
    }
    
    fileprivate struct MenuOptions: MenuViewCustomizable {
        var displayMode: MenuDisplayMode {
            return .standard(widthMode: .flexible, centerItem: true, scrollingMode: .scrollEnabledAndBouces)
        }
        
        var height: CGFloat {
            return 44.0
        }
        
        var itemsOptions: [MenuItemViewCustomizable] {
            return Tab.pages.map { page -> MenuItem in
                return MenuItem(text: page.name)
            }
        }
        
        var focusMode: MenuFocusMode {
            return .underline(height: 3.0, color: UIColor.red, horizontalPadding: 0.0, verticalPadding: 0.0)
        }
    }
    
    fileprivate struct MenuItem: MenuItemViewCustomizable {
        let text: String
        
        var displayMode: MenuItemDisplayMode {
            return .text(title: MenuItemText(text: text))
        }
    }
    
}
