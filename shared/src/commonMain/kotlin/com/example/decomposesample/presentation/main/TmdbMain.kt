package com.example.decomposesample.presentation.main

import com.arkivanov.decompose.value.Value
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.presentation.main.store.TmdbStore

interface TmdbMain {

    val model: Value<Model>

    fun onGetMovies(page: Int)

    data class Model(
        val movies: Result<Movies>?
    ) {
        constructor(state: TmdbStore.State): this(state.movies)
    }
}