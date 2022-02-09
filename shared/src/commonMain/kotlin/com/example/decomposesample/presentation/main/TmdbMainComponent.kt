package com.example.decomposesample.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.decomposesample.presentation.main.TmdbMain.Model
import com.example.decomposesample.presentation.main.store.TmdbStore
import com.example.decomposesample.presentation.main.store.TmdbStoreProvider
import com.example.decomposesample.util.asValue

class TmdbMainComponent(
    componentContext: ComponentContext
) : TmdbMain, ComponentContext by componentContext {

    private val store: TmdbStore = instanceKeeper.getStore {
        TmdbStoreProvider().provide()
    }

    override val model: Value<Model> = store.asValue().map(::Model)

    override fun onGetMovies(page: Int) {
        store.accept(TmdbStore.Intent.FetchMovies(page))
    }
}