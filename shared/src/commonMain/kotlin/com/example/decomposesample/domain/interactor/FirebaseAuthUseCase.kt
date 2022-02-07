package com.example.decomposesample.domain.interactor

import com.example.decomposesample.domain.interfaces.repository.FirebaseRepository
import com.example.decomposesample.domain.interfaces.service.FirebaseService
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.GoogleAuthProvider

class FirebaseAuthUseCase(
	private val firebaseService: FirebaseService,
	private val firebaseRepository: FirebaseRepository
) {
	suspend operator fun invoke(idToken: String): AuthResult {
		val credential = GoogleAuthProvider.credential(idToken, null)
		return firebaseService.googleAuth(credential)
	}
}