package com.example.decomposesample.android

import android.app.Application
import com.example.decomposesample.di.firebaseModule
import com.example.decomposesample.di.interactorModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

	override fun onCreate() {
		super.onCreate()
		Firebase.initialize(this)

		startKoin {
			androidContext(this@Application)
			modules(firebaseModule, interactorModule)
		}
	}
}