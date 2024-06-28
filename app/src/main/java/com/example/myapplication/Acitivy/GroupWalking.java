package com.example.myapplication.Acitivy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import java.util.ArrayList;

public class GroupWalking extends AppCompatActivity {
    private ListView listViewJoinedGroups;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> joinedGroupsList;
    private ImageButton imageButtonBack;
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
        joinedGroupsList = new ArrayList<>();

        // Get joined groups from intent or shared preferences (as an example, we use intent)
        joinedGroupsList = getIntent().getStringArrayListExtra("JOINED_GROUPS");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, joinedGroupsList);
        listViewJoinedGroups.setAdapter(adapter);
    }
}