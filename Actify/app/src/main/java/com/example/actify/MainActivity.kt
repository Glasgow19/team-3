package com.example.actify

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View

//import sun.jvm.hotspot.utilities.IntArray


//this is the login activity and the main activity


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG = "actify"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // ...
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            updateUI(currentUser)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.w(TAG, "loged in user")
        // Create an Intent to start the second activity
        val randomIntent = Intent(this, homepage::class.java)
        // Start the new activity.
        startActivity(randomIntent)
    }

    fun signInAnonymously(view: View) {
        // [START signin_anonymously]
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
//                hideProgressDialog()
                // [END_EXCLUDE]
            }
        // [END signin_anonymously]
    }



}
