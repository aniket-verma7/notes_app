package com.project.notesapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.notesapp.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {

    private ArrayList<Uri> uriArrayList;
    private ItemChangeListener itemChangeListener;


    public ImageAdapter(ArrayList<Uri> uriArrayList, ItemChangeListener itemChangeListener) {
        this.uriArrayList = uriArrayList;
        this.itemChangeListener = itemChangeListener;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.imageView.setImageURI(uriArrayList.get(position));
        holder.imageView.setOnClickListener(onClick -> {
            int index = holder.getAdapterPosition();

            if (uriArrayList.size() > 0) {
                uriArrayList.remove(index);
                itemChangeListener.updateBitmaps(uriArrayList);
                notifyItemRemoved(index);
            }

        });
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public void update(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
        notifyItemChanged(uriArrayList.size());
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface ItemChangeListener {
        void updateBitmaps(ArrayList<Uri> bitmaps);
    }
}
