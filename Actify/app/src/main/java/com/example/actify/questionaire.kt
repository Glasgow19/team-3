package com.example.actify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import android.view.View
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_questionaire.*


class questionaire : AppCompatActivity() {

    private val TAG = "actify"
    var user = "Anonymous"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionaire)
    }

    private fun setUser(){
        if (FirebaseAuth.getInstance().currentUser != null){
            user = FirebaseAuth.getInstance().currentUser!!.uid
        }else{
            user = "Anon"
        }

    }


    fun onSubmit(view:View){
        Log.d(TAG, "Lorem ipsum")

        //store data
        val db = FirebaseFirestore.getInstance()     // to access Firebase Firestore Datbase
        setUser()

        val answer1 = findViewById<EditText>(R.id.answer1).text.toString()
        val answer2 = findViewById<EditText>(R.id.answer2).text.toString()
        val answer3 = findViewById<EditText>(R.id.answer3).text.toString()
        val answer4 = findViewById<EditText>(R.id.answer4).text.toString()
        val answer6 = findViewById<EditText>(R.id.answer6).text.toString()

        // Get the selected radio button text using radio button on click listener
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_groupq5.checkedRadioButtonId)
        Log.d(TAG, radio.text.toString())
        val answer5 = radio.text.toString()


        Log.d(TAG, "adding the questions")

        val answer = hashMapOf(
            "Age" to Integer.parseInt(answer1),
            "Address" to answer2,
            "Goal" to answer3,
            "Activities" to answer4,
            "Physical" to Integer.parseInt(answer5),
            "Disability" to answer6
        )

        db.collection("surveyanswers").document(user)
            .set(answer, SetOptions.merge())
            .addOnSuccessListener { Log.d(TAG, "Added submitted answer to answers")
            }
            .addOnFailureListener { Log.w(TAG, "Error adding document")
            }


        //update UI
        val randomIntent = Intent(this, homepage::class.java)
        // Start the new activity.
        startActivity(randomIntent)
    }

}
