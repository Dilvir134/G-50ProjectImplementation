package com.example.g_50projectimplementation.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.R;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.ClientJobsActivity;

import java.util.List;

public class ClientGroupedListChildAdapter extends RecyclerView.Adapter<ClientGroupedListChildAdapter.ChildViewHolder>{


    private List<ClientListCard> cards;

    public ClientGroupedListChildAdapter(List<ClientListCard> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_card, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        ClientListCard card = cards.get(position);
        holder.cardTitle.setText(card.getTitle());
        holder.cardDescription.setText(card.getLocation());

        // Set up click listener for each (Hard Rock for now) card
        holder.itemView.setOnClickListener(v -> {
            if (card.getTitle().equals("Hard Rock")) { // Check the card title
                Intent intent = new Intent(holder.itemView.getContext(), ClientJobsActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle, cardDescription;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.clientName);
            cardDescription = itemView.findViewById(R.id.clientLocation);
        }
    }


}

