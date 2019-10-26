package com.example.actify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


//this page will have information about individual event
//will pull data from firebase

class eventinfo : homepage() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventinfo)
    }
}
