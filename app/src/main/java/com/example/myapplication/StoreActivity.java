package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        cart = new Cart();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewprd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Thức ăn cho chó loại 1", "Mô tả thức ăn cho chó loại 1", R.drawable.hat, 100000));
        productList.add(new Product("Thức ăn cho chó loại 2", "Mô tả thức ăn cho chó loại 2", R.drawable.xx, 120000));
        productList.add(new Product("Thức ăn cho mèo loại 1", "Mô tả thức ăn cho mèo loại 1", R.drawable.hutao, 90000));
        productList.add(new Product("Thức ăn cho mèo loại 2", "Mô tả thức ăn cho mèo loại 2", R.drawable.hat, 110000));
        productList.add(new Product("Thức ăn cho chó mèo", "Mô tả thức ăn cho chó mèo", R.drawable.hat, 150000));

        ProductAdapter adapter = new ProductAdapter(productList, cart, this);
        recyclerView.setAdapter(adapter);

        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, CheckoutActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });
    }
}
