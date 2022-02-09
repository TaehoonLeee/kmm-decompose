package com.example.decomposesample.util

import com.example.decomposesample.data.entity.status.Result

fun <T> T.toResult() = Result.Success(this)