import SwiftUI
import shared

struct ContentView: View {
	
    @State
    private var componentHolder = ComponentHolder {
        TmdbRootComponent(componentContext: $0)
    }

	var body: some View {
		Text("greet")
	}
}
