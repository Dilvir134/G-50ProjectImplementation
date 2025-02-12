package com.example.g_50projectimplementation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.R;
import com.example.g_50projectimplementation.adapters.model.ClientListCardGroup;
import com.example.g_50projectimplementation.adapters.model.StaffListCard;
import com.example.g_50projectimplementation.adapters.model.StaffListCardGroup;

import java.util.List;

public class StaffGroupedListParentAdapter extends RecyclerView.Adapter<StaffGroupedListParentAdapter.ParentViewHolder> {

    private List<StaffListCardGroup> cardGroups;

    public StaffGroupedListParentAdapter(List<StaffListCardGroup> cardGroups) {
        this.cardGroups = cardGroups;
    }

    public interface OnCardClickListener {
        void onCardClick(StaffListCard card);
    }

    @NonNull
    @Override
    public StaffGroupedListParentAdapter.ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_seggregated_list, parent, false);
        return new StaffGroupedListParentAdapter.ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffGroupedListParentAdapter.ParentViewHolder holder, int position) {
        StaffListCardGroup group = cardGroups.get(position);
        holder.groupTitle.setText(group.getGroupTitle());

        // Set up the child RecyclerView
        StaffGroupedListChildAdapter childAdapter = new StaffGroupedListChildAdapter(group.getCards());
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.childRecyclerView.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return cardGroups.size();
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle;
        RecyclerView childRecyclerView;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTitle = itemView.findViewById(R.id.groupTitle);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }
}
