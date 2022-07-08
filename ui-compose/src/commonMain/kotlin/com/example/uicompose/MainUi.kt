package com.example.uicompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.decomposesample.presentation.main.TmdbMain

@Composable
fun MainContent(main: TmdbMain) {
	val model by main.model.subscribeAsState()

}