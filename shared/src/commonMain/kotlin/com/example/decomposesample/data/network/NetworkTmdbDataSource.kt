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
                .get("trending/all/day") {
                    parameter("page", page)
                    parameter("api_key", "")
                }
                .body<Movies>()
                .toResult()
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}