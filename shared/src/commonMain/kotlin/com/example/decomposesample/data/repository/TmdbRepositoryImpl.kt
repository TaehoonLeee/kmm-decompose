package com.example.decomposesample.data.repository

import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result
import com.example.decomposesample.data.network.NetworkTmdbDataSource
import com.example.decomposesample.domain.interfaces.repository.TmdbRepository

class TmdbRepositoryImpl(
    private val tmdbDataSource: NetworkTmdbDataSource
) : TmdbRepository {
    override suspend fun getMovieList(page: Int): Result<Movies> = tmdbDataSource.getMovieList(page)
}