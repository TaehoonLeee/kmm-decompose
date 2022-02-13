package com.example.decomposesample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.presentation.main.TmdbMain
import com.example.decomposesample.presentation.main.store.TmdbStore

@Composable
fun TmdbMainContent(component: TmdbMain) {
    val model by component.model.subscribeAsState()

    when (val result = model.movies) {
        is Result.Success -> println(result.data)
    }
}