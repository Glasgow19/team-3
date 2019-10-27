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
import kotlinx.android.synthetic.main.activity_questionaire.answer2
import kotlinx.android.synthetic.main.activity_questionaire.answer4
import kotlinx.android.synthetic.main.activity_questionaire.answer6
import kotlinx.android.synthetic.main.activity_questionaire3.*


class questionaire : AppCompatActivity() {

    private val TAG = "actify"
    var user = "Anonymous"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionaire3)
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

        val age = findViewById<EditText>(R.id.answer1).text.toString()
        val postcode = findViewById<EditText>(R.id.answer2).text.toString()
        val activity = findViewById<EditText>(R.id.answer4).text.toString()
        val goal = findViewById<EditText>(R.id.answer5).text.toString()
        val disability = findViewById<EditText>(R.id.answer6).text.toString()



        // Get the selected radio button text using radio button on click listener
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_button.checkedRadioButtonId)
        Log.d(TAG, radio.text.toString())
//        val answer3 = radio.text.toString()
        val physical = radio.text.toString()


        Log.d(TAG, "adding the questions")

        val answer = hashMapOf(
            "Age" to Integer.parseInt(age),
            "Address" to postcode,
            "Physical" to Integer.parseInt(physical),
            "Activities" to activity,
            "Goal" to goal,
            "Disability" to disability
        )
        Log.d(TAG, answer.toString())


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
