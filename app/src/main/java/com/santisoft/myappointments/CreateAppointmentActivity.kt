package com.santisoft.myappointments

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_create_appointment.*
import java.util.*

class CreateAppointmentActivity : AppCompatActivity() {

    private var selectedcalendar = Calendar.getInstance()
    private var selectedRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener {

            if(etDescription.text.toString().length < 3){
                etDescription.error = getString(R.string.validate_appoinment_description)
            }else{
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }


        }

        btnConfirmAppointment.setOnClickListener {
            Toast.makeText(this, "Cita Registrada Correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        val specialtyOptions = arrayOf("Specialty A", "Specialty B", "Specialty C")
        spinnerSpecialties.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)

        val doctorsOptions = arrayOf("Doctor A", "Doctor B", "Doctor C")
        spinnerDoctors.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorsOptions)
    }

    fun onClickScheduledDate(v: View?){

        val year = selectedcalendar.get(Calendar.YEAR)
        val month = selectedcalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedcalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
           // Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedcalendar.set(y,m,d)
            etScheduledDate.setText(
                resources.getString(
                R.string.date_format,
                y,
                m.twoDigits(),
                d.twoDigits()
                )
            )
            displayRadioButtons()
        }
        val datePickerDialod =  DatePickerDialog(this, listener, year, month, dayOfMonth)
        val datePicker = datePickerDialod.datePicker
        val calendar = Calendar.getInstance()

        calendar.add(Calendar.DAY_OF_MONTH,1)
            datePicker.minDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH,29)
            datePicker.maxDate = calendar.timeInMillis
                datePickerDialod.show()
    }

    private fun displayRadioButtons(){

        //radioGroup.clearCheck()
        //radioGroup.removeAllViews()
        selectedRadioButton = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00 PM","3:30 PM","4:00 PM","4:30 PM")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

                radioButton.setOnClickListener{view ->
                    selectedRadioButton?.isChecked = false
                    selectedRadioButton = view as RadioButton?
                    selectedRadioButton?.isChecked = true
                }

            if(goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }

    }

    private fun Int.twoDigits() = if(this>=10) this.toString() else "0$this"


    override fun onBackPressed() {
        if(cvStep2.visibility == View.VISIBLE){
            cvStep2.visibility = View.GONE
            cvStep1.visibility = View.VISIBLE

        }else if(cvStep1.visibility == View.VISIBLE) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_exit_title))
            builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
            builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_posotove_button))
            { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ -> dialog.dismiss() }

            val dialog = builder.create()
            dialog.show()
        }
    }
}
