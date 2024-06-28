package com.example.myapplication.Acitivy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Barmain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout lnbr;
    LinearLayout btnCart, search, groupCard1, groupCard2, groupCard3, groupCard4;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barmain);
        //navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //navigation bottom
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
                intent.putExtra("GROUP_NAME", "Gặp gỡ những chú chó đáng yêu của chúng tôi đi dạo cùng chúng tôi!");
                intent.putExtra("GROUP_LOCATION", "Đống đa, Hà Nội");
                intent.putExtra("GROUP_MEMBERS", "8 Thành viên");
                intent.putExtra("GROUP_ORGANIZER", "Tổ chức bởi LovePet");
                startActivity(intent);
            }
        });

        groupCard2 = findViewById(R.id.group_card_2);
        groupCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg2);
                intent.putExtra("GROUP_NAME", "Dắt chó đi dạo lúc hoàng hôn");
                intent.putExtra("GROUP_LOCATION", "Hoàng Mai, Hà Nội");
                intent.putExtra("GROUP_MEMBERS", "12 Thành viên");
                intent.putExtra("GROUP_ORGANIZER", "Tổ chức bởi PetLove");
                startActivity(intent);
            }
        });

        groupCard3 = findViewById(R.id.group_card_3);
        groupCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg3);
                intent.putExtra("GROUP_NAME", "Đi bộ buổi sáng");
                intent.putExtra("GROUP_LOCATION", "Hồ Đắc Di, Hà Nội");
                intent.putExtra("GROUP_MEMBERS", "15 Thành viên");
                intent.putExtra("GROUP_ORGANIZER", "Tổ chức bởi Đức");
                startActivity(intent);
            }
        });

        groupCard4 = findViewById(R.id.group_card_4);
        groupCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barmain.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_IMAGE", R.drawable.petg4);
                intent.putExtra("GROUP_NAME", "Chiến binh cuối tuần");
                intent.putExtra("GROUP_LOCATION", "Tây Hồ, Hà Nội");
                intent.putExtra("GROUP_MEMBERS", "10 Thành viên");
                intent.putExtra("GROUP_ORGANIZER", "Tổ chức bởi Lovely");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(Barmain.this, Barmain.class);
            startActivity(intent);
        } else if (id == R.id.nav_phieukham) {
            // Xử lý khi chọn menu Phiếu khám
            Intent intent = new Intent(Barmain.this, ExaminationSchedule.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            // Sign out the user
            firebaseAuth.signOut();
            // Clear the current activity stack and start the login activity
            Intent intent = new Intent(Barmain.this, Lgin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_profile) {
        Intent intent = new Intent(Barmain.this, Profile.class);
        startActivity(intent);
    }
        else if (id == R.id.nav_hotline) {
            String phoneNumber = "0364869849";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
        else if (id == R.id.nav_walkinggGroup) {
            Intent intent = new Intent(Barmain.this, GroupWalking.class);
            // Get joined groups set from shared preferences
            Set<String> joinedGroupsSet = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getStringSet("JOINED_GROUPS", new HashSet<>());
            ArrayList<String> joinedGroupsList = new ArrayList<>(joinedGroupsSet);

            // Pass the joined groups list to the activity
            intent.putStringArrayListExtra("JOINED_GROUPS", joinedGroupsList);
            startActivity(intent);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
