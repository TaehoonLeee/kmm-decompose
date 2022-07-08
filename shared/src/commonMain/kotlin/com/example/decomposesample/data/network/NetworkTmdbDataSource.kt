package com.example.decomposesample.data.network

import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.util.toResult
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope

class NetworkTmdbDataSource(
    applicationScope: CoroutineScope,
    private val httpClient: HttpClient
) {

    private val pager = Pager(
        clientScope = applicationScope,
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        initialKey = 1,
        getItems = { currentKey, size ->
            val item = httpClient.get("3/trending/all/day") {
                parameter("page", currentKey)
                parameter("api_key", "72a65ce9c10f2d0fe0fd232119fb06be")
            }.body<Movies>()

            PagingResult(
                items = item.results,
                currentKey = currentKey,
                prevKey = { (currentKey - 1).takeIf { it > 0 } },
                nextKey = { (currentKey + 1).takeIf { it < item.totalPage } }
            )
        }
    )

    val pagingData
        get() = pager.pagingData

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
            Result.Error(e.message)
        }
    }
}