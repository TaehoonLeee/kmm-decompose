package com.example.decomposesample.domain.interactor

import com.example.decomposesample.domain.interfaces.repository.UnsplashRepository

class GetSearchListUseCase(
    private val unsplashRepository: UnsplashRepository
) {
}