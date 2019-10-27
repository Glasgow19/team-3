package com.example.actify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.firestore.SetOptions

//import sun.jvm.hotspot.utilities.IntArray


//import sun.jvm.hotspot.utilities.IntArray



// this is the home page for the app
// will add items to individual fragments

open class homepage : AppCompatActivity() {

    private val TAG = "actify"
    val db = FirebaseFirestore.getInstance()     // to access Firebase Firestore Datbase
    var user = "Anonymous"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and create accordingly
        setUser()
    }

    fun gotoevent(view:View){

        val randomIntent = Intent(this, eventinfo::class.java)
        // Start the new activity.
        startActivity(randomIntent)

    }


    private fun setUser(){
        if (FirebaseAuth.getInstance().currentUser != null){
             user = FirebaseAuth.getInstance().currentUser!!.uid
        }else{
            user = "Anon"
        }

    }
    private fun addVote(answer: String, question: String) {
        // [START addVote]
        // Create a new answer
        Log.d(TAG, user)

//        val surveyanswers = db.collection("survey").document("answers")

        val answer = hashMapOf(
            question to answer
        )

        db.collection("surveyanswers").document(user)
            .set(answer, SetOptions.merge())
            .addOnSuccessListener { Log.d(TAG, "Added submitted answer to answers")
        }
            .addOnFailureListener { Log.w(TAG, "Error adding document")
            }


//        // Add a new document with a generated ID
//        db.collection("surveyanswers")
//            .add(answer)
//            .addOnSuccessListener { Log.d(TAG, "Added submitted answer to answers")
//            }
//            .addOnFailureListener { Log.w(TAG, "Error adding document")
//            }
        // [END add_ada_lovelace]
    }


    fun voteButton(view:View){

        val x = view.getId()


        //Get the text view value of counter
        val getVoteQuestion = findViewById<TextView>(R.id.voteQuestionText)
        //Get the val of showCountTextView
        val voteQuestion = getVoteQuestion.text.toString()
        //call method to send to database
        Log.d(TAG,voteQuestion)

        //depending on vote store in DB
        when(view.getId()) {
            R.id.voteYesButton -> addVote("yes", voteQuestion)
            R.id.voteNoButton -> addVote("no", voteQuestion)
            else -> {
                print("YOU MESSED UP!")
            }
        }

    }



}


