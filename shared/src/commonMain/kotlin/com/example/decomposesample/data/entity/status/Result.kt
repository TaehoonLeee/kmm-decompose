package com.example.decomposesample.data.entity.status

sealed class Result<T> {

    class Success<T>(val data: T) : Result<T>()

    class Error<T>(val msg: String?) : Result<T>()
}
