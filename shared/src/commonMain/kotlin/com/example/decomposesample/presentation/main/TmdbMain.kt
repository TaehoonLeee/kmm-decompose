package com.example.decomposesample.presentation.main

import com.arkivanov.decompose.value.Value
import com.example.decomposesample.presentation.main.store.TmdbStore

interface TmdbMain {

    val model: Value<TmdbStore.State>

    fun onGetMovies(page: Int)
}