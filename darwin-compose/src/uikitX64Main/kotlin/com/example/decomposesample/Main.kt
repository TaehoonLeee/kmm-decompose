package com.example.decomposesample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import com.example.decomposesample.presentation.root.TmdbRootComponent
import com.example.uicompose.RootContent
import di.startKoin
import kotlinx.cinterop.*
import platform.Foundation.NSStringFromClass
import platform.UIKit.*

fun main() {
	val args = emptyArray<String>()
	memScoped {
		val argc = args.size + 1
		val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
		autoreleasepool {
			UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
		}
	}
}

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
	companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

	@ObjCObjectBase.OverrideInit
	constructor() : super()

	init {
		startKoin()
	}

	private val lifecycle = LifecycleRegistry()

	private val root = TmdbRootComponent(componentContext = DefaultComponentContext(lifecycle = lifecycle))

	private var _window: UIWindow? = null
	override fun window() = _window
	override fun setWindow(window: UIWindow?) {
		_window = window
	}

	override fun application(application: UIApplication, didFinishLaunchingWithOptions: Map<Any?, *>?): Boolean {
		window = UIWindow(frame = UIScreen.mainScreen.bounds)
		window!!.rootViewController = Application("Minesweeper") {
			Column {
				Spacer(modifier = Modifier.height(48.dp))
				RootContent(root)
			}
		}
		window!!.makeKeyAndVisible()
		return true
	}

	override fun applicationDidBecomeActive(application: UIApplication) {
		lifecycle.resume()
	}

	override fun applicationWillResignActive(application: UIApplication) {
		lifecycle.stop()
	}

	override fun applicationWillTerminate(application: UIApplication) {
		lifecycle.destroy()
	}
}