package com.example.decomposesample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.decomposesample.presentation.root.TmdbRootComponent
import com.example.decomposesample.ui.TmdbRootContent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tmdbRoot = TmdbRootComponent(defaultComponentContext())
        
        setContent { 
            TmdbRootContent(component = tmdbRoot)
        }
    }
}
