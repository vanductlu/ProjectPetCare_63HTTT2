package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AnimalAdapter;
import com.example.myapplication.R;
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

        // Initialize UI components
        breed = findViewById(R.id.breedlo);
        showButton = findViewById(R.id.showButton);
        spinner1 = findViewById(R.id.spinner1);
        recyclerView = findViewById(R.id.recyclerView);
        lnbr = findViewById(R.id.homemain);
        lnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        search = findViewById(R.id.search1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnCart = findViewById(R.id.shop);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                showPetInfoDialog();
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
        animalList.add(new Animal("Mèo", "Mèo Anh lông ngắn", R.drawable.meoanhlongngan,
                "Giun sán, viêm da, tiêu chảy, viêm phổi...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Cung cấp chế độ ăn hợp lý, đảm bảo vệ sinh, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

        animalList.add(new Animal("Chó", "Chó cỏ", R.drawable.choco,
                "Bệnh đường ruột, viêm phổi, bệnh dại...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Chế độ ăn uống hợp lý, tiêm phòng đầy đủ, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

        animalList.add(new Animal("Thỏ", "Thỏ Mỹ", R.drawable.tho,
                "Bệnh đường ruột, viêm da, bệnh hô hấp...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Chế độ ăn uống hợp lý, đảm bảo vệ sinh chuồng trại...",
                "Thức ăn có chất béo, thực phẩm chế biến..."));

        animalList.add(new Animal("Mèo", "Mèo cỏ", R.drawable.meota,
                "Giun sán, viêm da, tiêu chảy, viêm phổi...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Cung cấp chế độ ăn hợp lý, đảm bảo vệ sinh, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

        animalList.add(new Animal("Chó", "Chó Bec-gie", R.drawable.chobec,
                "Bệnh đường ruột, viêm phổi, bệnh dại...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Chế độ ăn uống hợp lý, tiêm phòng đầy đủ, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

        animalList.add(new Animal("Mèo", "Mèo lông xù", R.drawable.meolongxu,
                "Giun sán, viêm da, tiêu chảy, viêm phổi...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Cung cấp chế độ ăn hợp lý, đảm bảo vệ sinh, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

        animalList.add(new Animal("Chó", "Chó Pitbull", R.drawable.pitbull,
                "Bệnh đường ruột, viêm phổi, bệnh dại...",
                "Nên khám sức khỏe mỗi 6 tháng.",
                "Chế độ ăn uống hợp lý, tiêm phòng đầy đủ, tập thể dục thường xuyên...",
                "Socola, hành, tỏi, nho, cafein..."));

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

    private void showPetInfoDialog() {
        if (selectedAnimal != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialog = inflater.inflate(R.layout.dialog_pet_info, null);
            builder.setView(dialog);

            TextView diseasesTextView = dialog.findViewById(R.id.textViewDiseases);
            TextView checkupFrequencyTextView = dialog.findViewById(R.id.textViewCheckupFrequency);
            TextView careTipsTextView = dialog.findViewById(R.id.textViewCareTips);
            TextView harmfulFoodsTextView = dialog.findViewById(R.id.textViewHarmfulFoods);

            diseasesTextView.setText("Các chứng bệnh thú cưng hay gặp: " + selectedAnimal.getDiseases());
            checkupFrequencyTextView.setText("Mấy tháng nên khám sức khỏe 1 lần: " + selectedAnimal.getCheckupFrequency());
            careTipsTextView.setText("Nên chăm sóc như nào là đúng cách: " + selectedAnimal.getCareTips());
            harmfulFoodsTextView.setText("Cần tránh xa các thức ăn có hại: " + selectedAnimal.getHarmfulFoods());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialogg = builder.create();
            dialogg.show();
        } else {
            Toast.makeText(this, "No animal selected!", Toast.LENGTH_SHORT).show();
        }
    }

}
