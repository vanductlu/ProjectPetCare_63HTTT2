package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Resetpassword extends AppCompatActivity {

    Button btnsend;
    TextView txtbtlg;
    EditText txtlginemail;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resetpassword);

        btnsend = findViewById(R.id.btnsend);
        txtbtlg = findViewById(R.id.txtbtlg);
        txtlginemail = findViewById(R.id.txtlginemail);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtlginemail.getText().toString().trim();
                if (email.isEmpty()) {
                    txtlginemail.setError("Email is required");
                    txtlginemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtlginemail.setError("Please provide a valid email");
                    txtlginemail.requestFocus();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Resetpassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Otp.class));
                    } else {
                        Toast.makeText(Resetpassword.this, "Try again! Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        txtbtlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Lgin.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
