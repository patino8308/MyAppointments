package com.santisoft.myappointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.santisoft.myappointments.model.Appointment
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(id = 1, doctorName = "Medico test", scheduledDate = "12/12/2018", scheduledTime = "3:00 PM")
        )
        appointments.add(
            Appointment(id = 2, doctorName = "Medico test 1", scheduledDate = "17/12/2018", scheduledTime = "4:00 PM")
        )

        appointments.add(
            Appointment(id = 3, doctorName = "Medico test 2", scheduledDate = "15/12/2018", scheduledTime = "7:00 AM")
        )


        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = AppointmentAdapter(appointments)
    }
}
