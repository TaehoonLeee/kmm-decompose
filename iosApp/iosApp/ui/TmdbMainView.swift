//
//  TmdbMainView.swift
//  iosApp
//
//  Created by Taehoon Lee on 2022/02/13.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainView : View {
    
    private let component: TmdbMain
    
    @ObservedObject
    private var model: ObservableValue<TmdbStoreState>
    
    init(_ component: TmdbMain) {
        self.component = component
        self.model = ObservableValue(component.model)
    }
    
    var body: some View {
        VStack {
            
        }
    }
}
