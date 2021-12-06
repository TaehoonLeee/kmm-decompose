package com.example.decomposesample.di

import com.example.decomposesample.data.service.FirebaseApiExecutor
import com.example.decomposesample.domain.interactor.FirebaseAuthUseCase
import com.example.decomposesample.domain.interfaces.service.FirebaseService
import org.koin.dsl.module

val firebaseModule = module {
	factory<FirebaseService> { FirebaseApiExecutor() }
}

val interactorModule = module {
	single { FirebaseAuthUseCase(get()) }
}