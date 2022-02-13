//
//  ComponentHolder.swift
//  iosApp
//
//  Created by Taehoon Lee on 2022/02/13.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared

class ComponentHolder<T> {
    let lifecycle: LifecycleRegistry
    let component: T
    
    init(factory: (ComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let component = factory(DefaultComponentContext(lifecycle: lifecycle))
        self.lifecycle = lifecycle
        self.component = component
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
