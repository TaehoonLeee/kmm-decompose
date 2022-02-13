import SwiftUI
import shared

struct ContentView: View {
	
    @State
    private var componentHolder = ComponentHolder {
        TmdbRootComponent(componentContext: $0)
    }

	var body: some View {
        RootView(componentHolder.component)
            .onAppear {
                LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle)
            }.onDisappear {
                LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle)
            }
	}
}
