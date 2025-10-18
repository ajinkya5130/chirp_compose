import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        InitKoinKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { URL in
                    ExternalUriHandler.shared.onNewUri(uri: URL.absoluteString)
                }
        }
    }

}
