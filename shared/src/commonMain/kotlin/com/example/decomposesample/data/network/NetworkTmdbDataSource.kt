package com.example.decomposesample.data.network

import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.util.toResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NetworkTmdbDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getMovieList(page: Int): Result<Movies> {
        return try {
            httpClient
                .get("3/trending/all/day") {
                    parameter("page", page)
                    parameter("api_key", "72a65ce9c10f2d0fe0fd232119fb06be")
                }
                .body<Movies>()
                .toResult()
        } catch (e: Exception) {
            println(e)
            Result.Error(e.message)
        }
    }
}