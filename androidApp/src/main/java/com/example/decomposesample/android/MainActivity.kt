package com.example.decomposesample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.decomposesample.presentation.root.TmdbRootComponent
import com.example.uicompose.RootContent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tmdbRoot = TmdbRootComponent(defaultComponentContext())
        setContent {
            RootContent(root = tmdbRoot)
        }
    }
}
