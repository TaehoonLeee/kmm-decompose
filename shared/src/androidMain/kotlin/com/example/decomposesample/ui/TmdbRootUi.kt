package com.example.decomposesample.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.example.decomposesample.presentation.root.TmdbRoot

@Composable
fun TmdbRootContent(component: TmdbRoot) {
    Children(routerState = component.routerState) {
        when(val child = it.instance) {
            is TmdbRoot.Child.Main -> TmdbMainContent(child.component)
        }
    }
}