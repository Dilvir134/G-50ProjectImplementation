package com.example.g_50projectimplementation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.R;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.adapters.model.ClientListCardGroup;

import java.util.List;

public class ClientGroupedListParentAdapter extends RecyclerView.Adapter<ClientGroupedListParentAdapter.ParentViewHolder>{

    private List<ClientListCardGroup> cardGroups;

    public ClientGroupedListParentAdapter(List<ClientListCardGroup> cardGroups) {
        this.cardGroups = cardGroups;
    }

    public interface OnCardClickListener {
        void onCardClick(ClientListCard card);
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_seggregated_list, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        ClientListCardGroup group = cardGroups.get(position);
        holder.groupTitle.setText(group.getGroupTitle());

        // Set up the child RecyclerView
        ClientGroupedListChildAdapter childAdapter = new ClientGroupedListChildAdapter(group.getCards());
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.childRecyclerView.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return cardGroups.size();
    }

    static class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle;
        RecyclerView childRecyclerView;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTitle = itemView.findViewById(R.id.groupTitle);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }
}
