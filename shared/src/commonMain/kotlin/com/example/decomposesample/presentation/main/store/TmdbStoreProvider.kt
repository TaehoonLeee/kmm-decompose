package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State
import kotlinx.coroutines.launch

internal class TmdbStoreProvider(
    private val storeFactory: StoreFactory,
    private val getMovieList: GetMovieListUseCase
) {

    fun provide(): TmdbStore =
        object : TmdbStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = this::class.simpleName,
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.FetchMovies(1)),
            executorFactory = executor,
            reducer = reducer
        ) {}

    private sealed class Action {
        class FetchMovies(val page: Int) : Action()
    }

    private sealed class Message {
        data class MoviesFetched(
            val movies: Result<Movies>
        ) : Message()
    }

    private val executor = coroutineExecutorFactory<Intent, Action, Message, State, Nothing> {
        onAction<Action.FetchMovies> { action ->
            launch {
                dispatch(Message.MoviesFetched(getMovieList(action.page)))
            }
        }

        onIntent<Intent.FetchMovies> {
            launch {
                dispatch(Message.MoviesFetched(getMovieList(it.page)))
            }
        }
    }

    private val reducer = Reducer<State, Message> { msg: Message ->
        when(msg) {
            is Message.MoviesFetched -> copy(movies = msg.movies)
        }
    }
}