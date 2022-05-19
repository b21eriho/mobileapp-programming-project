package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class DayRecyclerViewAdapter extends RecyclerView.Adapter<DayRecyclerViewAdapter.DayViewHolder>{

    private SharedPreferences myPreferenceRef;
    private SharedPreferences.Editor myPreferenceEditor;

    private List<Day> days;

    private DataBaseHelper db;

    public DayRecyclerViewAdapter(List<Day> days) {
        this.days = days;

    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView recyclerView = (RecyclerView) view.getParent();
                db = new DataBaseHelper(recyclerView.getContext());
                Gson gson = new Gson();

                myPreferenceRef = recyclerView.getContext().getSharedPreferences("MyPrefs",MODE_PRIVATE);
                myPreferenceEditor = myPreferenceRef.edit();

                String sortQuerryString = "";
                if(myPreferenceRef.getBoolean("onlyEventful", false)){sortQuerryString += " WHERE " + DataBaseHelper.COLLUMN_EVENTS + " != '[]'";};

                Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_LIGHTRISE_DAYS + sortQuerryString + " ORDER BY " + DataBaseHelper.COLLUMN_DATE, null, null);
                cursor.moveToPosition(recyclerView.getChildAdapterPosition(view));

                Intent intent = new Intent(recyclerView.getContext(), DetailedActivity.class);

                Log.d("==>", cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_NAME)));
                if(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_NAME)).length() == 0){
                    intent.putExtra("DAY_NAME", DayNameHelper.getNameFromDate(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_DATE))));
                } else{
                    intent.putExtra("DAY_NAME", cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_NAME)));
                }

                intent.putExtra("DAY_DATE", "Date: " + DayNameHelper.getFullDateFromDate(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_DATE))));

                intent.putExtra("DAY_SUNUP", "Sunrise: " + cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_SUNUP)));

                intent.putExtra("DAY_SUNDOWN", "Sunset: " + cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_SUNDOWN)));

                intent.putExtra("DAY_EVENTS", cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_EVENTS)));

                recyclerView.getContext().startActivity(intent);
            }
        });
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day thisDay = days.get(position);

        if(thisDay.getName().length() != 0){
            holder.dayTitle.setText(thisDay.getName());
        }
        else{
            holder.dayTitle.setText(DayNameHelper.getNameFromDate(thisDay.getDate()));
        }

        holder.sunTime.setText("Sun: " + thisDay.getSunTimesShort());

        String tmp = "";
        tmp += thisDay.getEvents().size() + " event";
        if(thisDay.getEvents().size() != 1){ tmp += "s";}
        holder.nrEvents.setText(tmp);

    }

    @Override
    public int getItemCount() { return days.size(); }

    public class DayViewHolder extends RecyclerView.ViewHolder {

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
