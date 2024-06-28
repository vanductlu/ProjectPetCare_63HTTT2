package com.example.myapplication;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.BC.AlarmReceiver;
import com.example.myapplication.data.model.Appointment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    private EditText editTextDate, editTextTime;
    private Button buttonSchedule;
    private ImageButton imageButtonBack;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // POST_NOTIFICATIONS permission is granted
                    scheduleAppointment();
                } else {
                    Toast.makeText(ScheduleActivity.this, "Quyền gửi thông báo bị từ chối", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        buttonSchedule = findViewById(R.id.buttonSchedule);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        String hospitalName = getIntent().getStringExtra("hospital_name");

        // Display the hospital name
        TextView hospitalNameTextView = findViewById(R.id.hospital_name);
        hospitalNameTextView.setText(hospitalName);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giờ hiện tại
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTime.setText(hourOfDay + ":" + minute);
                                mHour = hourOfDay;
                                mMinute = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(ScheduleActivity.this, android.Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Yêu cầu quyền POST_NOTIFICATIONS
                        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
                    } else {
                        scheduleAppointment();
                    }
                } else {
                    scheduleAppointment();
                }
            }
        });
    }

    private void scheduleAppointment() {
        // Chuyển đổi thông tin ngày và giờ thành Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);

        // Kiểm tra xem thời gian đã chọn có hợp lệ không
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            Toast.makeText(this, "Thời gian đã chọn không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin bệnh viện từ Intent
        String hospitalName = getIntent().getStringExtra("hospital_name");

        // Lưu thông tin vào Firebase
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference()
                .child("hospitals").child(hospitalName).child("appointments");

        String appointmentId = appointmentsRef.push().getKey();
        Appointment appointment = new Appointment(editTextDate.getText().toString(),
                editTextTime.getText().toString(), hospitalName);

        appointmentsRef.child(appointmentId).setValue(appointment);

        // Đặt báo thức nếu cần
        setAlarm(calendar.getTimeInMillis());

        // Chuyển sang màn hình chi tiết lịch hẹn
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("date", editTextDate.getText().toString());
        intent.putExtra("time", editTextTime.getText().toString());
        intent.putExtra("hospital_name", hospitalName);
        startActivity(intent);
    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);

        Toast.makeText(this, "Đã đặt lịch khám vào " + editTextDate.getText().toString() + " lúc " + editTextTime.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
