package com.example.decomposesample.di

import com.example.decomposesample.data.network.NetworkTmdbDataSource
import com.example.decomposesample.data.repository.TmdbRepositoryImpl
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.domain.interfaces.repository.TmdbRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutinesModule = module {
	single(named("mainDispatcher")) {
		CoroutineScope(SupervisorJob() + Dispatchers.Main)
	}
	single(named("defaultDispatcher")) {
		CoroutineScope(SupervisorJob() + Dispatchers.Default)
	}
}

val networkModule = module {
	single { HttpClient {
		defaultRequest {
			url {
				protocol = URLProtocol.HTTPS
				host = "api.themoviedb.org/3"
			}
		}
		install(ContentNegotiation) {
			json()
		}
	} }

	single {
		NetworkTmdbDataSource(get())
	}
}

val repositoryModule = module {
	single<TmdbRepository> { TmdbRepositoryImpl(get()) }
}

val interactorModule = module {
	single { GetMovieListUseCase(get()) }
}