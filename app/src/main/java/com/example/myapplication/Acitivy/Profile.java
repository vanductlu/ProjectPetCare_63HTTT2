package com.example.myapplication.Acitivy;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView profileImageView, petImageView;
    private boolean isProfileImage = true;
    private ImageButton imageExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImage);
        petImageView = findViewById(R.id.petImageView);
        imageExit = findViewById(R.id.imageExit);
        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Barmain.class));
            }
        });
        loadUserInfoFromLocal();
        loadPetInfoFromLocal(); // Ensure this method is called to load pet info
        loadImagesFromLocal();

        profileImageView.setOnClickListener(v -> {
            isProfileImage = true;
            openImagePicker();
        });

        petImageView.setOnClickListener(v -> {
            isProfileImage = false;
            openImagePicker();
        });

        findViewById(R.id.saveButton).setOnClickListener(v -> saveInfo(v));
    }

    private void openImagePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                if (isProfileImage) {
                    profileImageView.setImageBitmap(bitmap);
                    saveImageToInternalStorage(bitmap, "profile_image.png");
                } else {
                    petImageView.setImageBitmap(bitmap);
                    saveImageToInternalStorage(bitmap, "pet_image.png");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUserInfo() {
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String birthDate = ((EditText) findViewById(R.id.editTextDate2)).getText().toString();
        String phone = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        String address = ((EditText) findViewById(R.id.editTextDiaChi)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        saveUserInfoToLocal(name, birthDate, phone, address, email);
    }

    private void saveUserInfoToLocal(String name, String birthDate, String phone, String address, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("birthDate", birthDate);
        editor.putString("phone", phone);
        editor.putString("address", address);
        editor.putString("email", email);
        editor.apply();
    }

    private void savePetInfoToLocal() {
        String petName = ((EditText) findViewById(R.id.editTextNamePet)).getText().toString();
        String petInfo = ((EditText) findViewById(R.id.editTextInforPet)).getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("petName", petName);
        editor.putString("petInfo", petInfo);
        editor.apply();
    }

    private void loadUserInfoFromLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String birthDate = sharedPreferences.getString("birthDate", "");
        String phone = sharedPreferences.getString("phone", "");
        String address = sharedPreferences.getString("address", "");
        String email = sharedPreferences.getString("email", "");

        ((EditText) findViewById(R.id.editTextName)).setText(name);
        ((EditText) findViewById(R.id.editTextDate2)).setText(birthDate);
        ((EditText) findViewById(R.id.editTextPhone)).setText(phone);
        ((EditText) findViewById(R.id.editTextDiaChi)).setText(address);
        ((EditText) findViewById(R.id.editTextEmail)).setText(email);
    }

    private void loadPetInfoFromLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
        String petName = sharedPreferences.getString("petName", "");
        String petInfo = sharedPreferences.getString("petInfo", "");

        ((EditText) findViewById(R.id.editTextNamePet)).setText(petName);
        ((EditText) findViewById(R.id.editTextInforPet)).setText(petInfo);
    }

    private void loadImagesFromLocal() {
        Bitmap profileBitmap = loadImageFromInternalStorage("profile_image.png");
        if (profileBitmap != null) {
            profileImageView.setImageBitmap(profileBitmap);
        }

        Bitmap petBitmap = loadImageFromInternalStorage("pet_image.png");
        if (petBitmap != null) {
            petImageView.setImageBitmap(petBitmap);
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap, String filename) {
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromInternalStorage(String filename) {
        try {
            FileInputStream fis = openFileInput(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void saveInfo(View view) {
        saveUserInfo();
        savePetInfoToLocal();
        Toast.makeText(this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
    }

    public void onProfileImageClick(View view) {
        isProfileImage = true;
        openImagePicker();
    }

    public void onPetImageClick(View view) {
        isProfileImage = false;
        openImagePicker();
    }
}
