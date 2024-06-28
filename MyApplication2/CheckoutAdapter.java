package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.R;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<CartItem> cartItemList;
    private Cart cart;
    private TextView totalPriceTextView;

    public CheckoutAdapter(List<CartItem> cartItemList, Cart cart, TextView totalPriceTextView) {
        this.cartItemList = cartItemList;
        this.cart = cart;
        this.totalPriceTextView = totalPriceTextView;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.productName.setText(cartItem.getProduct().getName());
        holder.productQuantity.setText("Số lượng: " + cartItem.getQuantity());
        holder.productTotalPrice.setText("Giá: " + cartItem.getTotalPrice() + " VND");

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.removeProduct(cartItem.getProduct());
                cartItemList = cart.getCartItems(); // Cập nhật lại danh sách sau khi xóa
                notifyDataSetChanged();
                totalPriceTextView.setText("Tổng cộng: " + cart.getTotalPrice() + " VND");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productQuantity;
        TextView productTotalPrice;
        Button deleteButton;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productTotalPrice = itemView.findViewById(R.id.product_total_price);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
