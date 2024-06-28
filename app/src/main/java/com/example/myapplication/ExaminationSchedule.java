package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;

public class ExaminationSchedule extends AppCompatActivity {
    private TextView tvHospitalName, tvDate, tvTime, tvQueueNumber, tvAppointmentId, tvPatientId;
    private ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_examination_schedule);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvQueueNumber = findViewById(R.id.tvQueueNumber);
        tvAppointmentId = findViewById(R.id.tvAppointmentId);
        tvPatientId = findViewById(R.id.tvPatientId);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        readFromXml();
    }

    private void readFromXml() {
        try {
            FileInputStream fis = openFileInput("appointment_details.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, null);
            int eventType = parser.getEventType();
            String tagName = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if ("HospitalName".equals(tagName)) {
                            tvHospitalName.setText(parser.getText());
                        } else if ("Date".equals(tagName)) {
                            tvDate.setText("Ngày khám: " + parser.getText());
                        } else if ("Time".equals(tagName)) {
                            tvTime.setText("Giờ khám dự kiến: " + parser.getText());
                        } else if ("QueueNumber".equals(tagName)) {
                            tvQueueNumber.setText(parser.getText());
                        } else if ("AppointmentId".equals(tagName)) {
                            tvAppointmentId.setText(parser.getText());
                        } else if ("PatientId".equals(tagName)) {
                            tvPatientId.setText(parser.getText());
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}