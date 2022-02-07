package com.example.decomposesample.di

import com.example.decomposesample.data.repository.FirebaseRepositoryImpl
import com.example.decomposesample.data.repository.UnsplashRepositoryImpl
import com.example.decomposesample.data.service.FirebaseApiExecutor
import com.example.decomposesample.domain.interactor.FirebaseAuthUseCase
import com.example.decomposesample.domain.interactor.GetSearchListUseCase
import com.example.decomposesample.domain.interfaces.repository.FirebaseRepository
import com.example.decomposesample.domain.interfaces.repository.UnsplashRepository
import com.example.decomposesample.domain.interfaces.service.FirebaseService
import org.koin.dsl.module

val firebaseModule = module {
	single<FirebaseService> { FirebaseApiExecutor() }
	single<FirebaseRepository> { FirebaseRepositoryImpl() }

	single<UnsplashRepository> { UnsplashRepositoryImpl() }
}

val interactorModule = module {
	single { FirebaseAuthUseCase(get(), get()) }
	single { GetSearchListUseCase(get()) }
}