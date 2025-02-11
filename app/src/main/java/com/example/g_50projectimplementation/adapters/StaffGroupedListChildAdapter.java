package com.example.g_50projectimplementation.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.ClientDetailsActivity;
import com.example.g_50projectimplementation.R;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.adapters.model.StaffListCard;

import java.util.List;

public class StaffGroupedListChildAdapter extends RecyclerView.Adapter<StaffGroupedListChildAdapter.ChildViewHolder>{

    private List<StaffListCard> cards;

    public StaffGroupedListChildAdapter(List<StaffListCard> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public StaffGroupedListChildAdapter.ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_card, parent, false);
        return new StaffGroupedListChildAdapter.ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffGroupedListChildAdapter.ChildViewHolder holder, int position) {
        StaffListCard card = cards.get(position);
        holder.cardTitle.setText(card.getTitle());
        holder.cardDescription.setText(card.getPosition());
        holder.img.setImageURI(card.getImageUri());

        holder.itemView.setOnClickListener(v -> {
            //TODO: Navigate to StaffDetailsActivity
            Intent intent = new Intent(holder.itemView.getContext(), ClientDetailsActivity.class);
            intent.putExtra("STAFF_ID", card.getId());
            intent.putExtra("STAFF_NAME", card.getTitle());
            intent.putExtra("STAFF_POSITION", card.getPosition());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle, cardDescription;
        ImageView img;
        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.clientName);
            cardDescription = itemView.findViewById(R.id.clientLocation);
            img = itemView.findViewById(R.id.clientPicture);
        }
    }

}
