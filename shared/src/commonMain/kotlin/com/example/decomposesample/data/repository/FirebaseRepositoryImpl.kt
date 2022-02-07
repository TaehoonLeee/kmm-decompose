package com.example.decomposesample.data.repository

import com.example.decomposesample.domain.interfaces.repository.FirebaseRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth

class FirebaseRepositoryImpl : FirebaseRepository {

    private val auth = Firebase.auth

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}