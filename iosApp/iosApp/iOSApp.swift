import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        IosModuleKt.startKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
