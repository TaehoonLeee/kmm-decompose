package com.example.decomposesample.domain.interfaces.repository

import com.example.decomposesample.data.entity.Movie
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {

    fun getMovieList(): Flow<PagingData<Movie>>
}