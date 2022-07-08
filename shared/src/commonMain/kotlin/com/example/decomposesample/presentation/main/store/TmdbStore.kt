package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.decomposesample.data.entity.Movie
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface TmdbStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        class FetchMovies(val page: Int) : Intent()
    }

    data class State(
        val movies: Flow<PagingData<Movie>> = emptyFlow()
    )
}