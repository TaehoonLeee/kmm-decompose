package com.example.decomposesample.domain.interfaces.repository

import dev.gitlive.firebase.auth.FirebaseUser

interface FirebaseRepository {
    fun getCurrentUser(): FirebaseUser?
}