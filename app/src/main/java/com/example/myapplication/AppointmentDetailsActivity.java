package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private TextView tvAppointmentDate, tvAppointmentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);

        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");

        tvAppointmentDate.setText("Ngày khám: " + date);
        tvAppointmentTime.setText("Giờ khám dự kiến: " + time);
    }
}
