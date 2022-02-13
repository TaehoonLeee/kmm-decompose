//
//  ObservableValue.swift
//  iosApp
//
//  Created by Taehoon Lee on 2022/02/13.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import Combine

public class ObservableValue<T: AnyObject> : ObservableObject {
    
    private let observableValue: Value<T>
    
    @Published
    var value: T
    
    private var observer : ((T) -> Void)?
    
    init(_ value: Value<T>) {
        self.observableValue = value
        self.value = observableValue.value
        self.observer = { [weak self] value in self?.value = value }
        
        observableValue.subscribe(observer: self.observer!)
    }
    
    deinit {
        observableValue.unsubscribe(observer: self.observer!)
    }
}
