package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class rgis extends AppCompatActivity {

    EditText rgemail, rgfullname, rgphone, rgpass, rgcfpass;
    TextView log;
    Button regis;
    CheckBox agree;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    boolean isCheckBoxChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgis);

        // Bind views
        log = findViewById(R.id.txtlgin);
        agree = findViewById(R.id.cbtemp);
        rgemail = findViewById(R.id.txtrgemail);
        rgfullname = findViewById(R.id.txtrgfullname);
        rgphone = findViewById(R.id.txtrgphone);
        rgpass = findViewById(R.id.txtrgpass);
        rgcfpass = findViewById(R.id.txtrgcfpass);
        regis = findViewById(R.id.btnregis);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Lgin.class));
            }
        });

        // Handle insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Checkbox change listener
        agree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isCheckBoxChecked = isChecked;
        });

        // Handle registration button click
        regis.setOnClickListener(v -> {
            if (isCheckBoxChecked) {
                String email = rgemail.getText().toString().trim();
                String fullname = rgfullname.getText().toString().trim();
                String phone = rgphone.getText().toString().trim();
                String password = rgpass.getText().toString().trim();
                String confirmPassword = rgcfpass.getText().toString().trim();

                if (isValidRegistration(email, fullname, phone, password, confirmPassword)) {
                    registerUser(email, password);
                } else {
                    Toast.makeText(rgis.this, "Vui lòng kiểm tra lại thông tin đăng ký", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(rgis.this, "Vui lòng chấp nhận các điều khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidRegistration(String email, String fullname, String phone, String password, String confirmPassword) {
        return isValidEmail(email) && isValidPassword(password) && password.equals(confirmPassword) && !fullname.isEmpty() && !phone.isEmpty();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches("\\A.*(?=.*[a-zA-Z])(?=.*\\d).*\\z");
    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(rgis.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(rgis.this, Lgin.class));
                } else {
                    Toast.makeText(rgis.this, "Đăng ký không thành công: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
