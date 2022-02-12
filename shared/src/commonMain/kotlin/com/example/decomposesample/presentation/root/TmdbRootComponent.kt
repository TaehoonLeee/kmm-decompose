package com.example.decomposesample.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.decomposesample.presentation.main.TmdbMainComponent

class TmdbRootComponent(
    componentContext: ComponentContext
) : TmdbRoot, ComponentContext by componentContext {

    private val router = router<Configuration, TmdbRoot.Child>(
        initialConfiguration = Configuration.Main,
        handleBackButton = true,
        childFactory = ::resolveChild
    )
    override val routerState: Value<RouterState<*, TmdbRoot.Child>> = router.state

    private fun resolveChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): TmdbRoot.Child = when (configuration) {
        is Configuration.Main -> TmdbRoot.Child.Main(TmdbMainComponent(componentContext))
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()
    }
}