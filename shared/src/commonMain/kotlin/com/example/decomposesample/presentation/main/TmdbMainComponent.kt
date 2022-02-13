package com.example.decomposesample.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.example.decomposesample.presentation.main.store.TmdbStore
import com.example.decomposesample.presentation.main.store.TmdbStoreProvider
import com.example.decomposesample.util.asValue

class TmdbMainComponent(
    componentContext: ComponentContext
) : TmdbMain, ComponentContext by componentContext {

    private val store: TmdbStore = instanceKeeper.getStore {
        TmdbStoreProvider().provide()
    }

    override val model = store.asValue()

    override fun onGetMovies(page: Int) {
        store.accept(TmdbStore.Intent.FetchMovies(page))
    }
}