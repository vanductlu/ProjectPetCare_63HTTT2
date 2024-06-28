package com.example.myapplication.Acitivy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GroupWalking extends AppCompatActivity {
    private ListView listViewJoinedGroups;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> joinedGroupsList;
    private ImageButton imageButtonBack;
    private Button buttonLeaveGroup;
    private Set<String> joinedGroupsSet;
    private String selectedGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_walking);

        imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Barmain.class));
            }
        });

        listViewJoinedGroups = findViewById(R.id.listViewJoinedGroups);
        buttonLeaveGroup = findViewById(R.id.buttonLeaveGroup);

        joinedGroupsList = getIntent().getStringArrayListExtra("JOINED_GROUPS");
        if (joinedGroupsList == null) {
            joinedGroupsList = new ArrayList<>();
        }

        joinedGroupsSet = new HashSet<>(getSharedPreferences("APP_PREFS", MODE_PRIVATE).getStringSet("JOINED_GROUPS", new HashSet<>()));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, joinedGroupsList);
        listViewJoinedGroups.setAdapter(adapter);

        listViewJoinedGroups.setOnItemClickListener((parent, view, position, id) -> {
            selectedGroupName = joinedGroupsList.get(position);
        });

        buttonLeaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGroupName != null) {
                    leaveGroup(selectedGroupName);
                } else {
                    Toast.makeText(GroupWalking.this, "Please select a group to leave.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void leaveGroup(String groupName) {
        joinedGroupsSet.remove(groupName);
        joinedGroupsList.remove(groupName);

        getSharedPreferences("APP_PREFS", MODE_PRIVATE).edit().putStringSet("JOINED_GROUPS", joinedGroupsSet).apply();
        adapter.notifyDataSetChanged();

        Intent intent = new Intent(GroupWalking.this, GroupDetailsActivity.class);
        intent.putExtra("GROUP_NAME", groupName);
        startActivity(intent);
    }
}
