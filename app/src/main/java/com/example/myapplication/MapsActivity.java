// MapsActivity.java
package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.example.myapplication.ScheduleActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
    private LinearLayout lnbr, btnCart,btnbreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_maps);
        lnbr = findViewById(R.id.homemain);
        lnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        btnbreed = findViewById(R.id.breedlo);
        btnbreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BreedingActivity.class));
            }
        });

        btnCart = findViewById(R.id.shop);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);
        map.getController().setZoom(15.0);

        // Kiểm tra và yêu cầu quyền vị trí
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            setupMap();
        }
    }

    private void setupMap() {
        // Set initial position for the map
        GeoPoint startPoint = new GeoPoint(21.028511, 105.804817);
        map.getController().setCenter(startPoint);

        // Add veterinary hospital locations to the map
        ArrayList<VetHospital> vetHospitals = getVetHospitals();
        for (VetHospital hospital : vetHospitals) {
            Marker marker = new Marker(map);
            marker.setPosition(hospital.getLocation());
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(hospital.getName());
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    // Navigate to the scheduling interface and pass the hospital name
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    intent.putExtra("hospital_name", hospital.getName());
                    startActivity(intent);
                    return true;
                }
            });
            map.getOverlays().add(marker);
        }
    }

    private ArrayList<VetHospital> getVetHospitals() {
        ArrayList<VetHospital> locations = new ArrayList<>();

        // Add veterinary hospital locations in Hanoi
        locations.add(new VetHospital(new GeoPoint(21.028511, 105.804817), "Hà Nội"));
        locations.add(new VetHospital(new GeoPoint(21.021800, 105.825127), "Bệnh viện thú y PetHealth - Số 240 Âu Cơ"));
        locations.add(new VetHospital(new GeoPoint(21.035219, 105.831590), "Bệnh viện thú y Hà Nội - Số 55 Hàng Chuối"));
        locations.add(new VetHospital(new GeoPoint(21.007800, 105.846950), "Bệnh viện thú y Pet Pro - Số 54 Đại Cồ Việt"));
        locations.add(new VetHospital(new GeoPoint(21.002839, 105.849530), "Bệnh viện thú y Petplus - Số 112 Kim Ngưu"));
        locations.add(new VetHospital(new GeoPoint(21.019200, 105.852500), "Bệnh viện thú y Hữu Nghị - Số 9 Ngõ 67 Phùng Khoang"));
        locations.add(new VetHospital(new GeoPoint(21.017393, 105.845520), "Bệnh viện thú y 24h - Số 24 Kim Mã Thượng"));
        locations.add(new VetHospital(new GeoPoint(21.034462, 105.823540), "Bệnh viện thú y Hoàng Quốc Việt - Số 10 Hoàng Quốc Việt"));

        return locations;
    }

    // Define a class to hold the veterinary hospital information
    class VetHospital {
        private GeoPoint location;
        private String name;

        public VetHospital(GeoPoint location, String name) {
            this.location = location;
            this.name = name;
        }

        public GeoPoint getLocation() {
            return location;
        }

        public String getName() {
            return name;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupMap();
            } else {
                // Quyền bị từ chối, bạn có thể hiện thông báo cho người dùng
            }
        }
    }
}
