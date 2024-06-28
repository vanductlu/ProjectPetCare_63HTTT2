package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Barmain extends AppCompatActivity {
    LinearLayout lnbr;
    LinearLayout btnCart, search, groupCard1, groupCard2, groupCard3, groupCard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barmain);

        lnbr = findViewById(R.id.breedlo);
        lnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BreedingActivity.class));
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

        groupCard1 = findViewById(R.id.group_card_1);
        groupCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg1);
                intent.putExtra("GROUP_NAME", "Meet our lovely dogs walking with us!");
                intent.putExtra("GROUP_LOCATION", "Valencia, Spain");
                intent.putExtra("GROUP_MEMBERS", "8 members");
                intent.putExtra("GROUP_ORGANIZER", "Organized by Laura");
                startActivity(intent);
            }
        });

        groupCard2 = findViewById(R.id.group_card_2);
        groupCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg2);
                intent.putExtra("GROUP_NAME", "Sunset Dog Walkers");
                intent.putExtra("GROUP_LOCATION", "Barcelona, Spain");
                intent.putExtra("GROUP_MEMBERS", "12 members");
                intent.putExtra("GROUP_ORGANIZER", "Organized by Marco");
                startActivity(intent);
            }
        });

        groupCard3 = findViewById(R.id.group_card_3);
        groupCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg3);
                intent.putExtra("GROUP_NAME", "Morning Walkies");
                intent.putExtra("GROUP_LOCATION", "Madrid, Spain");
                intent.putExtra("GROUP_MEMBERS", "15 members");
                intent.putExtra("GROUP_ORGANIZER", "Organized by Anna");
                startActivity(intent);
            }
        });

        groupCard4 = findViewById(R.id.group_card_4);
        groupCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg4);
                intent.putExtra("GROUP_NAME", "Weekend Warriors");
                intent.putExtra("GROUP_LOCATION", "Seville, Spain");
                intent.putExtra("GROUP_MEMBERS", "10 members");
                intent.putExtra("GROUP_ORGANIZER", "Organized by Clara");
                startActivity(intent);
            }
        });
    }
}
