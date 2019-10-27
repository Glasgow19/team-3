package com.example.actify

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.icu.util.Calendar
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi


class AddEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_event)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateShow = findViewById<TextView>(R.id.add_event_date) as TextView


        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                dateShow.setText( " " + dayOfMonth + " " + monthOfYear + " " + year) }
            , year, month, day )
        dpd.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker(view: View) {

        val c = Calendar.getInstance()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timeShow = findViewById<TextView>(R.id.add_event_time) as TextView

        val tpd = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeShow.setText( " " + hourOfDay + " : " + minute) }
            , hour, minute, true)
        tpd.show()
    }
}
