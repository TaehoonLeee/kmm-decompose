package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State

interface TmdbStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        class FetchMovies(val page: Int) : Intent()
    }

    data class State(
        val movies: Result<Movies>? = null
    )
}