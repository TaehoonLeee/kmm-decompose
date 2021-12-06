package com.example.decomposesample.data.service

import com.example.decomposesample.domain.interfaces.service.FirebaseService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth

class FirebaseApiExecutor : FirebaseService {

	private val auth = Firebase.auth

	override suspend fun googleAuth(credential: AuthCredential): AuthResult =
		auth.signInWithCredential(credential)
}