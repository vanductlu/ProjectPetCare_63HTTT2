package com.example.myapplication.Acitivy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CheckoutAdapter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.CartItem;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Khôi phục Cart từ Intent
        cart = (Cart) getIntent().getSerializableExtra("cart");
        if (cart == null) {
            // Xử lý trường hợp Cart null (tùy vào logic ứng dụng)
            finish(); // Kết thúc activity nếu không có Cart
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CheckoutAdapter adapter = new CheckoutAdapter(cart.getCartItems(), cart, findViewById(R.id.total_price));
        recyclerView.setAdapter(adapter);

        TextView totalPriceTextView = findViewById(R.id.total_price);
        totalPriceTextView.setText("Tổng cộng: " + cart.getTotalPrice() + " VND");

        Button paymentButton = findViewById(R.id.payment_button);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog();
            }
        });

        findViewById(R.id.imageButtonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại StoreActivity và truyền Cart hiện tại
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showPaymentDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Bạn có muốn thanh toán không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showSuccessDialog();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goBackToStore();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thanh toán thành công")
                .setMessage("Cảm ơn bạn đã mua hàng!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goBackToBarmain();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void goBackToStore() {
        Intent intent = new Intent(CheckoutActivity.this, StoreActivity.class);
        intent.putExtra("cart", cart);
        startActivity(intent);
        finish();
    }
    private void goBackToBarmain() {
        Intent intent = new Intent(CheckoutActivity.this, Barmain.class);
        intent.putExtra("cart", cart);
        startActivity(intent);
        finish();
    }
}
