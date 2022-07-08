package com.example.decomposesample.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.decomposesample.data.network.NetworkTmdbDataSource
import com.example.decomposesample.data.repository.TmdbRepositoryImpl
import com.example.decomposesample.domain.interactor.GetMovieListUseCase
import com.example.decomposesample.domain.interfaces.repository.TmdbRepository
import com.example.decomposesample.presentation.main.store.TmdbStoreProvider
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

	singleOf(::NetworkTmdbDataSource)
}

val repositoryModule = module {
	singleOf(::TmdbRepositoryImpl) bind TmdbRepository::class
}

val interactorModule = module {
	singleOf(::GetMovieListUseCase)
}

val storeModule = module {
	singleOf(::DefaultStoreFactory) bind StoreFactory::class
	singleOf(::TmdbStoreProvider)

	factoryOf(TmdbStoreProvider::provide)
}

val dataModule = networkModule + repositoryModule + interactorModule + storeModule