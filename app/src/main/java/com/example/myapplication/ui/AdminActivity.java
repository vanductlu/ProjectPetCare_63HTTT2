package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import com.example.myapplication.Adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize Spinner
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data
        List<String> dataList = new ArrayList<>();
        List<Integer> imageList = new ArrayList<>();

        // Populate with some sample data
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");

        // Add corresponding images (replace with your drawable resource ids)
        imageList.add(R.drawable.person);
        imageList.add(R.drawable.pets);
        imageList.add(R.drawable.cart);

        recyclerAdapter = new RecyclerAdapter(dataList, imageList);
        recyclerView.setAdapter(recyclerAdapter);

        // Spinner item selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle selection logic here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
