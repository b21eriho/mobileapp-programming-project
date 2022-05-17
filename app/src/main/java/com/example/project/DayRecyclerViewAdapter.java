package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayRecyclerViewAdapter extends RecyclerView.Adapter<DayRecyclerViewAdapter.DayViewHolder>{


    private List<Day> days;

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day thisDay = days.get(position);

        if(thisDay.getName() != null){
            holder.dayTitle.setText(thisDay.getName());
        }
        else{
            String tmp = "";

            switch (thisDay.getDate() % 5){
                case 0:
                    tmp += "Monday the ";
                    break;
                case 1:
                    tmp += "Tuesday the ";
                    break;
                case 2:
                    tmp += "Wednesday the ";
                    break;
                case 3:
                    tmp += "Friday  the ";
                    break;
                case 4:
                    tmp += "Sunday the ";
                    break;
            }

            tmp += thisDay.getDate() + "";

            switch (thisDay.getDate()){
                case 1:
                    tmp += "st ";
                    break;
                case 2:
                    tmp += "nd ";
                    break;
                case 3:
                    tmp += "rd ";
                    break;
                default:
                    tmp += "th ";
                    break;
            }

            holder.dayTitle.setText(tmp);
        }

        holder.sunTime.setText("Sun: " + thisDay.getSunTimesShort());

        String tmp = "";
        tmp += thisDay.getEvents().size() + " event";
        if(thisDay.getEvents().size() != 1){ tmp += "s";}
        holder.nrEvents.setText(tmp);

    }

    @Override
    public int getItemCount() { return days.size(); }

    public class DayViewHolder extends RecyclerView.ViewHolder{

        private TextView dayTitle;
        private TextView sunTime;
        private TextView nrEvents;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTitle = itemView.findViewById(R.id.listItemTitle);
            sunTime = itemView.findViewById(R.id.sunTimeDisplay);
            nrEvents = itemView.findViewById(R.id.eventAmountDisplay);
        }
    }

}
