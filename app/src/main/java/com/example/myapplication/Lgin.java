package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Lgin extends AppCompatActivity {
    ImageView fb,gg,ap;
    EditText emailedit, matkhauedit;
    TextView dangky, quenmk;
    Button dangnhap;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgin);

        // Bind views
        quenmk = findViewById(R.id.txtforgotpass);
        emailedit = findViewById(R.id.txtlginemail);
        matkhauedit = findViewById(R.id.txtlginpass);
        dangnhap = findViewById(R.id.btnlogin);
        dangky = findViewById(R.id.txtlgin);
        fb=findViewById(R.id.f);
        gg=findViewById(R.id.g);
        ap=findViewById(R.id.a);

        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), rgis.class));
            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(); // Call login method when login button is clicked
            }
        });

        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Resetpassword.class));
            }
        });

        // Handle insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void openFacebookLink(View view) {
        Uri uri = Uri.parse("https://www.facebook.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openGoogleLink(View view) {
        Uri uri = Uri.parse("https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fwww.google.gg%2F&ec=GAZAmgQ&hl=vi&ifkv=AS5LTARKeihBNY5EDkQrv1ywBWCRnvcUf79-yGSct1lL-plsX5bkzNxURSpukU41LmKyRrt0Tqg9EA&passive=true&flowName=GlifWebSignIn&flowEntry=ServiceLogin&dsh=S705176639%3A1718708534409106&ddm=0");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openAppleLink(View view) {
        Uri uri = Uri.parse("https://www.apple.com/vn/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void login() {
        String email = emailedit.getText().toString();
        String matkhau = matkhauedit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui Long Nhap Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(matkhau)) {
            Toast.makeText(this, "Vui Long Nhap Mat Khau", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Lgin.this, MainActivity.class);
                    startActivity(intent);

                }else {Toast.makeText(getApplicationContext(), "Dang nhap 0 thanh cong", Toast.LENGTH_SHORT).show();}

            }
        });
    }
}
