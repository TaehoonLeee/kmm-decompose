package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class TmdbStoreProvider(
    private val storeFactory: StoreFactory = DefaultStoreFactory()
) {

    fun provide(): TmdbStore = object : TmdbStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = this::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Action.FetchMovies(1)),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl()
    ) { }

    private sealed class Action {
        class FetchMovies(val page: Int) : Action()
    }

    private sealed class Message {
        data class MoviesFetched(
            val movies: Result<Movies>
        ) : Message()
    }

    private class ExecutorImpl :
        KoinComponent,
        CoroutineExecutor<Intent, Action, State, Message, Nothing>()
    {
        val getMovieList = get<GetMovieListUseCase>()

        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {
                is Action.FetchMovies -> scope.launch {
                    dispatch(Message.MoviesFetched(getMovieList(action.page)))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent) {
                is Intent.FetchMovies -> scope.launch {
                    dispatch(Message.MoviesFetched(getMovieList(intent.page)))
                }
            }
        }
    }

    private class ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State = when (msg) {
            is Message.MoviesFetched -> copy(movies = msg.movies)
        }
    }
}