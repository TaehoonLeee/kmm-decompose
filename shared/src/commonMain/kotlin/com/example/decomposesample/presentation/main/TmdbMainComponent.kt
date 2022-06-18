package com.example.decomposesample.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.decomposesample.presentation.main.store.TmdbStore
import com.example.decomposesample.util.asValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TmdbMainComponent(
    componentContext: ComponentContext
) : TmdbMain, KoinComponent, ComponentContext by componentContext {

    private val store: TmdbStore = instanceKeeper.getStore(::get)

    override val model = store.asValue()

    override fun onGetMovies(page: Int) {
        store.accept(TmdbStore.Intent.FetchMovies(page))
    }
}