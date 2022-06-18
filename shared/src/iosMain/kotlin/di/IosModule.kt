package di

import com.example.decomposesample.di.interactorModule
import com.example.decomposesample.di.networkModule
import com.example.decomposesample.di.repositoryModule
import com.example.decomposesample.di.storeModule

fun startKoin() = org.koin.core.context.startKoin {
    modules(repositoryModule, interactorModule, networkModule, storeModule)
}