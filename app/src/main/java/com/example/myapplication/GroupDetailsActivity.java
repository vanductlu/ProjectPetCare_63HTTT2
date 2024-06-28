package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroupDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        ImageView groupImage = findViewById(R.id.group_image);
        TextView groupName = findViewById(R.id.group_name);
        TextView groupLocation = findViewById(R.id.group_location);
        TextView groupMembers = findViewById(R.id.group_members);
        TextView groupOrganizer = findViewById(R.id.group_organizer);
        Button joinButton = findViewById(R.id.join_button);
        Button backToHomeButton = findViewById(R.id.back_button);

        // Lấy dữ liệu từ Intent
        int imageResource = getIntent().getIntExtra("GROUP_IMAGE", R.drawable.ic_launcher_background);
        String name = getIntent().getStringExtra("GROUP_NAME");
        String location = getIntent().getStringExtra("GROUP_LOCATION");
        String members = getIntent().getStringExtra("GROUP_MEMBERS");
        String organizer = getIntent().getStringExtra("GROUP_ORGANIZER");

        // Thiết lập dữ liệu cho các TextView và ImageView
        groupImage.setImageResource(imageResource);
        groupName.setText(name);
        groupLocation.setText(location);
        groupMembers.setText(members);
        groupOrganizer.setText(organizer);

        // Thiết lập sự kiện nhấn cho nút "Tham gia"
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroupDetailsActivity.this, "Tham gia thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập sự kiện nhấn cho nút "Quay lại trang chủ"
        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailsActivity.this, Barmain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
