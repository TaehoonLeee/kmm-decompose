package com.example.decomposesample.android

import android.app.Application
import com.example.decomposesample.di.coroutinesModule
import com.example.decomposesample.di.repositoryModule
import com.example.decomposesample.di.interactorModule
import com.example.decomposesample.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@Application)
			modules(repositoryModule, interactorModule, networkModule, coroutinesModule)
		}
	}
}