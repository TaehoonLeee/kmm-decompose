package com.example.decomposesample.data.repository

import com.example.decomposesample.data.network.NetworkTmdbDataSource
import com.example.decomposesample.domain.interfaces.repository.TmdbRepository

class TmdbRepositoryImpl(
    private val tmdbDataSource: NetworkTmdbDataSource
) : TmdbRepository {
    override fun getMovieList() = tmdbDataSource.pagingData
}