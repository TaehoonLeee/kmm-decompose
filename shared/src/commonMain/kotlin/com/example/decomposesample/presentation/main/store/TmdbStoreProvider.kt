package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.example.decomposesample.data.entity.Movie
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class TmdbStoreProvider(
    private val storeFactory: StoreFactory,
    private val getMovieList: GetMovieListUseCase
) {

    fun provide(): TmdbStore =
        object : TmdbStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = this::class.simpleName,
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.FetchMovies),
            executorFactory = executor,
            reducer = reducer
        ) {}

    private sealed class Action {
        object FetchMovies : Action()
    }

    private sealed class Message {
        data class MoviesFetched(
            val movies: Flow<PagingData<Movie>>
        ) : Message()
    }

    private val executor = coroutineExecutorFactory<Intent, Action, Message, State, Nothing> {
        onAction<Action.FetchMovies> { action ->
            launch {
                dispatch(Message.MoviesFetched(getMovieList()))
            }
        }
    }

    private val reducer = Reducer<State, Message> { msg: Message ->
        when(msg) {
            is Message.MoviesFetched -> copy(movies = msg.movies)
        }
    }
}