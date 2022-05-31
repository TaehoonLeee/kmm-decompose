package com.example.decomposesample.di

import com.example.decomposesample.data.network.NetworkTmdbDataSource
import com.example.decomposesample.data.repository.TmdbRepositoryImpl
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.domain.interfaces.repository.TmdbRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
	single { HttpClient {
		defaultRequest {
			url {
				protocol = URLProtocol.HTTPS
				host = "api.themoviedb.org"
			}
		}
		install(ContentNegotiation) {
			json(Json { ignoreUnknownKeys = true })
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