package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private Cart cart;
    private LinearLayout lnbr, btnbread, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // Khởi tạo hoặc khôi phục Cart từ Intent (nếu có)
        if (getIntent().hasExtra("cart")) {
            cart = (Cart) getIntent().getSerializableExtra("cart");
        } else {
            cart = new Cart();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewprd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Pate cho mèo", "Pate cao cấp cho mèo, cung cấp dinh dưỡng cực tốt", R.drawable.patemeo, 200000));
        productList.add(new Product("Thịt đã qua sơ chế cho mọi loại chó", "Thịt chứa nhiều chất đạm, bảo đảm tốt cho đường ruột.", R.drawable.thitcho, 120000));
        productList.add(new Product("Hạt cho mèo", "Loại 1", R.drawable.hatchomeo, 90000));
        productList.add(new Product("Hạt cho chó", "Loại 1", R.drawable.hatchocho, 110000));
        productList.add(new Product("Cá ngừ cho mèo", "Cá ngừ bắt từ ngoài biển, không ngon không lấy tiền", R.drawable.cangu, 150000));
        productList.add(new Product("Thức ăn cho thỏ", "Đồ ăn tốt cho thỏ, ăn không bệnh, không tăng cân không tính tiền", R.drawable.thittho, 150000));

        ProductAdapter adapter = new ProductAdapter(productList, cart, this);
        recyclerView.setAdapter(adapter);

        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang CheckoutActivity và truyền Cart hiện tại
                Intent intent = new Intent(StoreActivity.this, CheckoutActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

        lnbr = findViewById(R.id.homemain);
        lnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Barmain.class));
            }
        });

        btnbread = findViewById(R.id.breedlo);
        btnbread.setOnClickListener(new View.OnClickListener() {
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
    }
}
