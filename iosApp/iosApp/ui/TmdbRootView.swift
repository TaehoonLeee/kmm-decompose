//
//  TmdbRootView.swift
//  iosApp
//
//  Created by Taehoon Lee on 2022/02/13.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RootView : View {
    
    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, TmdbRootChild>>
                                                
    init(_ component: TmdbRoot) {
        self.routerState = ObservableValue(component.routerState)
    }
    
    var body: some View {
        let child = routerState.value.activeChild.instance
        
        switch child {
        case let main as TmdbRootChild.Main:
            MainView(main.component)
        default:
            EmptyView()
        }
    }
}
