package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.model.Animal;
import com.example.myapplication.R;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private Context context;
    private List<Animal> animalList;
    private OnAnimalClickListener onAnimalClickListener;

    public AnimalAdapter(Context context, List<Animal> animalList, OnAnimalClickListener onAnimalClickListener) {
        this.context = context;
        this.animalList = animalList;
        this.onAnimalClickListener = onAnimalClickListener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.nameTextView.setText("Tên: " + animal.getName());
        holder.breedTextView.setText("Giới thiệu: " + animal.getBreed());
        holder.imageView.setImageResource(animal.getImageResourceId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnimalClickListener.onAnimalClick(animal); // Gọi callback khi click vào item
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public interface OnAnimalClickListener {
        void onAnimalClick(Animal animal);
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView breedTextView;
        public ImageView imageView;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            breedTextView = itemView.findViewById(R.id.breedTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
