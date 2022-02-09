package com.example.decomposesample.domain.interactor

import com.example.decomposesample.domain.interfaces.repository.TmdbRepository

class GetMovieListUseCase(
    private val tmdbRepository: TmdbRepository
) {

    suspend operator fun invoke(page: Int) = tmdbRepository.getMovieList(page)
}