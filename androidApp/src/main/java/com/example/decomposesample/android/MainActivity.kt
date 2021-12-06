package com.example.decomposesample.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.decomposesample.domain.interactor.FirebaseAuthUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val RC_SIGN_IN = 9001
    }

    private val authUseCase: FirebaseAuthUseCase = get()
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    private val googleSignInClient = GoogleSignIn.getClient(this, gso)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: Button = findViewById(R.id.auth_btn)

        tv.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            runBlocking {
                val result = authUseCase(account.idToken)
                println(result)
            }
        }
    }
}
