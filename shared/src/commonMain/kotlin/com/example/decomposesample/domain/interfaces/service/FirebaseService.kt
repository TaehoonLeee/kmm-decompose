package com.example.decomposesample.domain.interfaces.service

import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.AuthResult

interface FirebaseService {

	suspend fun googleAuth(credential: AuthCredential): AuthResult
}