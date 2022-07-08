package di

import com.example.decomposesample.di.dataModule

fun startKoin() = org.koin.core.context.startKoin {
    modules(dataModule)
}