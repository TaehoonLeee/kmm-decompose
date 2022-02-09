package com.example.decomposesample.presentation.main.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.presentation.main.store.TmdbStore.Intent
import com.example.decomposesample.presentation.main.store.TmdbStore.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class TmdbStoreProvider(
    private val storeFactory: StoreFactory = DefaultStoreFactory()
) {

    fun provide(): TmdbStore = object : TmdbStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = this::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl()
    ) { }

    private sealed class Result {
        data class MoviesFetched(
            val movies: com.example.decomposesample.data.entity.status.Result<Movies>
        ) : Result()
    }

    private class ExecutorImpl :
        ReaktiveExecutor<Intent, Unit, State, Result, Nothing>(),
        KoinComponent
    {
        val getMovieList = get<GetMovieListUseCase>()
        val mainDispatcher: CoroutineScope by inject(named("mainDispatcher"))

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent) {
                is Intent.FetchMovies -> mainDispatcher.launch {
                    dispatch(Result.MoviesFetched(getMovieList(intent.page)))
                }
            }
        }
    }

    private class ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State = when (result) {
            is Result.MoviesFetched -> copy(movies = result.movies)
        }
    }
}