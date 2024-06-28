package com.example.myapplication.Acitivy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GroupDetailsActivity extends AppCompatActivity {

    private boolean isJoined = false;
    private Set<String> joinedGroupsSet;
    private ArrayList<String> joinedGroupsList;
    private Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        ImageView groupImage = findViewById(R.id.group_image);
        TextView groupName = findViewById(R.id.group_name);
        TextView groupLocation = findViewById(R.id.group_location);
        TextView groupMembers = findViewById(R.id.group_members);
        TextView groupOrganizer = findViewById(R.id.group_organizer);
        joinButton = findViewById(R.id.join_button);
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

        // Initialize joined groups list and set
        joinedGroupsList = new ArrayList<>();
        joinedGroupsSet = new HashSet<>(getSharedPreferences("APP_PREFS", MODE_PRIVATE).getStringSet("JOINED_GROUPS", new HashSet<>()));

        // Check if already joined
        if (joinedGroupsSet.contains(name)) {
            isJoined = true;
            joinButton.setText("Đã tham gia");
        }

        // Thiết lập sự kiện nhấn cho nút "Tham gia"
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJoined) {
                    isJoined = true;
                    joinButton.setText("Đã tham gia");
                    Toast.makeText(GroupDetailsActivity.this, "Tham gia thành công!", Toast.LENGTH_SHORT).show();

                    // Add to joined groups set
                    joinedGroupsSet.add(name);

                    // Save to shared preferences
                    getSharedPreferences("APP_PREFS", MODE_PRIVATE).edit().putStringSet("JOINED_GROUPS", joinedGroupsSet).apply();
                } else {
                    Toast.makeText(GroupDetailsActivity.this, "Bạn đã tham gia nhóm này rồi!", Toast.LENGTH_SHORT).show();
                }
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
