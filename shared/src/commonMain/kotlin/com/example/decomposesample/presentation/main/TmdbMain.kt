package com.example.decomposesample.presentation.main

import com.example.decomposesample.presentation.main.store.TmdbStore
import kotlinx.coroutines.flow.Flow

interface TmdbMain {

    val model: Flow<TmdbStore.State>

    fun onGetMovies(page: Int)
}