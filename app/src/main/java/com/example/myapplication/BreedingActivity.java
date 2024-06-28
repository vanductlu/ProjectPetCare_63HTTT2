package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AnimalAdapter;
import com.example.myapplication.data.model.Animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BreedingActivity extends AppCompatActivity {
    private LinearLayout lnbr, btnCart, search, breed;
    private Button showButton;
    private Spinner spinner1;
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private List<Animal> animalList;
    private List<Animal> filteredAnimalList;
    private Animal selectedAnimal = null; // Biến để lưu trữ động vật được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeding);
//aapt
        // Initialize UI components
        breed = findViewById(R.id.breedlo);
        showButton = findViewById(R.id.showButton);
        spinner1 = findViewById(R.id.spinner1);
        recyclerView = findViewById(R.id.recyclerView);
        lnbr = findViewById(R.id.homemain);
        lnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Barmain.class));
            }
        });
        search = findViewById(R.id.search1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        btnCart = findViewById(R.id.shop);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StoreActivity.class));
            }
        });
        breed = findViewById(R.id.breedlo);
        breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BreedingActivity.class));
            }
        });

        // Initialize animal list before setting up spinners
        animalList = new ArrayList<>();
        filteredAnimalList = new ArrayList<>();

        // Setup Spinner data
        setupSpinners();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animalAdapter = new AnimalAdapter(this, filteredAnimalList, new AnimalAdapter.OnAnimalClickListener() {
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

        // Set spinner item selected listener
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterAnimalsByName(spinner1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupSpinners() {
        // Create a set to store unique animal names
        Set<String> animalNames = new HashSet<>();
        animalNames.add("All"); // Add an "All" option to show all animals

        // Populate the set with animal names from the list
        for (Animal animal : animalList) {
            animalNames.add(animal.getName());
        }

        // Convert the set to a list and create an adapter
        List<String> animalNameList = new ArrayList<>(animalNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, animalNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
    }

    private void loadAnimals() {
        animalList.add(new Animal("Mèo", "Mèo Anh lông ngắn", R.drawable.meoanhlongngan));
        animalList.add(new Animal("Chó", "Chó cỏ", R.drawable.choco));
        animalList.add(new Animal("Thỏ", "Thỏ Mỹ", R.drawable.tho));
        animalList.add(new Animal("Mèo", "Mèo cỏ", R.drawable.meota));
        animalList.add(new Animal("Chó", "Chó Bec-gie", R.drawable.chobec));
        animalList.add(new Animal("Mèo", "Mèo lông xù", R.drawable.meolongxu));
        animalList.add(new Animal("Chó", "Chó Pitbull", R.drawable.pitbull));
        filterAnimalsByName(spinner1.getSelectedItem().toString());
        setupSpinners(); // Update spinner data after loading animals
    }

    private void filterAnimalsByName(String name) {
        filteredAnimalList.clear();
        if (name.equals("All")) {
            filteredAnimalList.addAll(animalList);
        } else {
            for (Animal animal : animalList) {
                if (animal.getName().equals(name)) {
                    filteredAnimalList.add(animal);
                }
            }
        }
        animalAdapter.notifyDataSetChanged();
    }

    private void showSelectedInfo() {
        if (selectedAnimal != null) {
            // Hiển thị thông tin chi tiết của động vật được chọn
            String message = "Tên động vật: " + selectedAnimal.getName() + "\n" +
                    "Mô tả: " + selectedAnimal.getBreed();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No animal selected!", Toast.LENGTH_SHORT).show();
        }
    }
}