package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventRecyclerviewAdapter extends RecyclerView.Adapter<EventRecyclerviewAdapter.EventViewHolder>{

    private List<String> events;

    public EventRecyclerviewAdapter(List<String> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String thisEvent = events.get(position);
        holder.eventName.setText(thisEvent);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        private TextView eventName;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_title);;
        }
    }

}
