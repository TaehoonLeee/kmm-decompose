package com.example.decomposesample.presentation.root

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import com.example.decomposesample.presentation.main.TmdbMain

interface TmdbRoot {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Main(val component: TmdbMain) : Child()
    }
}