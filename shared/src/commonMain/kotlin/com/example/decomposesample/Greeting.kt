package com.example.decomposesample

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}