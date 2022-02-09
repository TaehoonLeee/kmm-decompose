package com.example.decomposesample.domain.interfaces.repository

import com.example.decomposesample.data.entity.Movies
import com.example.decomposesample.data.entity.status.Result

interface TmdbRepository {

    suspend fun getMovieList(page: Int): Result<Movies>
}