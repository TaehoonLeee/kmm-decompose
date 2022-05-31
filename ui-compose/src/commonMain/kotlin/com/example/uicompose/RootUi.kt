package com.example.uicompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.example.decomposesample.presentation.root.TmdbRoot

@Composable
fun RootContent(root: TmdbRoot) {
	Children(routerState = root.routerState) {
		when(val child = it.instance) {
			is TmdbRoot.Child.Main -> MainContent(child.component)
		}
	}
}