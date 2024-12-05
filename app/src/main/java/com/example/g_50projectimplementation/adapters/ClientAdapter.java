package com.example.g_50projectimplementation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.ClientDetailsActivity;
import com.example.g_50projectimplementation.ClientJobsActivity;
import com.example.g_50projectimplementation.R;
import com.example.g_50projectimplementation.database.entity.Client;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private final List<Client> clientList;
    private final Context context;

    public ClientAdapter(Context context, List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_list_card, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.clientName.setText(client.getName());
        holder.clientLocation.setText(client.getLocation());

        // Navigate to ClientDetailsActivity on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClientDetailsActivity.class);
            intent.putExtra("clientId", client.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView clientName, clientLocation;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.clientName);
            clientLocation = itemView.findViewById(R.id.clientLocation);
        }
    }
}
