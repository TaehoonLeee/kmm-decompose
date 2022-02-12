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
import kotlinx.serialization.json.Json
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
				host = "api.themoviedb.org"
			}
		}
		install(ContentNegotiation) {
			json(Json {
				isLenient = true
				prettyPrint = false
				encodeDefaults = true
				ignoreUnknownKeys = true
				useArrayPolymorphism = false
				allowStructuredMapKeys = true
				allowSpecialFloatingPointValues = true
			})
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