package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AnimalAdapter;
import com.example.myapplication.data.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class BreedingActivity extends AppCompatActivity {

    private Button showButton;
    private Spinner spinner1, spinner2;
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private List<Animal> animalList;
    private Animal selectedAnimal = null; // Biến để lưu trữ động vật được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeding);

        // Initialize UI components
        showButton = findViewById(R.id.showButton);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        recyclerView = findViewById(R.id.recyclerView);

        // Setup Spinner data
        setupSpinners();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animalList = new ArrayList<>();
        animalAdapter = new AnimalAdapter(this, animalList, new AnimalAdapter.OnAnimalClickListener() {
            @Override
            public void onAnimalClick(Animal animal) {
                selectedAnimal = animal; // Cập nhật động vật được chọn
            }
        });
        recyclerView.setAdapter(animalAdapter);

        // Load initial data
        loadAnimals();

        // Set button click listener
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedInfo();
            }
        });
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sample_data, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
    }

    private void loadAnimals() {
        animalList.add(new Animal("Cat", "Mèo Simmy", R.drawable.cat));
        animalList.add(new Animal("Dog", "Đức", R.drawable.dog));
        animalList.add(new Animal("Rabbit", "Angora", R.drawable.rabbit));
        animalList.add(new Animal("Capybara", "Bộ trưởng bộ ngoại giao", R.drawable.cappy));
        animalList.add(new Animal("Con nghiện", "Vua tài xỉu", R.drawable.senna));
        animalList.add(new Animal("Con vợ", " Con wave của t", R.drawable.hutao));
        animalList.add(new Animal("Con cá", "Fishy fishy", R.drawable.shark));
        animalAdapter.notifyDataSetChanged();
    }

    private void showSelectedInfo() {
        if (selectedAnimal != null) {
            // Hiển thị thông tin chi tiết của động vật được chọn
            String message = "Name: " + selectedAnimal.getName() + "\n" +
                    "Breed: " + selectedAnimal.getBreed();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No animal selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
