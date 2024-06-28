package com.example.myapplication.Acitivy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private TextView tvAppointmentDate, tvAppointmentTime, tvHospitalName, tvQueueNumber, tvAppointmentId, tvPatientId;

    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvQueueNumber = findViewById(R.id.tvQueueNumber);
        tvAppointmentId = findViewById(R.id.tvAppointmentId);
        tvPatientId = findViewById(R.id.tvPatientId);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentDetailsActivity.this, MapsActivity.class));
            }
        });

        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String hospitalName = getIntent().getStringExtra("hospital_name");

        tvHospitalName.setText(hospitalName);
        tvAppointmentDate.setText("Ngày khám: " + date);
        tvAppointmentTime.setText("Giờ khám dự kiến: " + time);

        generateRandomQueueNumber();
        generateCodes();
        saveToXml(date, time, hospitalName);
    }

    private void generateRandomQueueNumber() {
        int min = 1;
        int max = 100; // Set the max value as per your requirement
        Random random = new Random();
        int randomQueueNumber = random.nextInt((max - min) + 1) + min; // Random number between min and max
        tvQueueNumber.setText("STT: " + randomQueueNumber);
    }

    private void generateCodes() {
        String appointmentId = "YMA" + generateRandomString();
        String patientId = "YMP" + generateRandomString();

        tvAppointmentId.setText("Mã phiếu khám: " + appointmentId);
        tvPatientId.setText("Mã bệnh nhân: " + patientId);
    }

    private String generateRandomString() {
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private void saveToXml(String date, String time, String hospitalName) {
        try {
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "AppointmentDetails");

            xmlSerializer.startTag("", "HospitalName");
            xmlSerializer.text(hospitalName);
            xmlSerializer.endTag("", "HospitalName");

            xmlSerializer.startTag("", "Date");
            xmlSerializer.text(date);
            xmlSerializer.endTag("", "Date");

            xmlSerializer.startTag("", "Time");
            xmlSerializer.text(time);
            xmlSerializer.endTag("", "Time");

            xmlSerializer.startTag("", "QueueNumber");
            xmlSerializer.text(tvQueueNumber.getText().toString());
            xmlSerializer.endTag("", "QueueNumber");

            xmlSerializer.startTag("", "AppointmentId");
            xmlSerializer.text(tvAppointmentId.getText().toString());
            xmlSerializer.endTag("", "AppointmentId");

            xmlSerializer.startTag("", "PatientId");
            xmlSerializer.text(tvPatientId.getText().toString());
            xmlSerializer.endTag("", "PatientId");

            xmlSerializer.endTag("", "AppointmentDetails");
            xmlSerializer.endDocument();

            String xmlString = writer.toString();
            FileOutputStream fos = openFileOutput("appointment_details.xml", Context.MODE_PRIVATE);
            fos.write(xmlString.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
