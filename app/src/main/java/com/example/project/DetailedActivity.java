package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    private Gson gson;

    private RecyclerView eventRecyclerView;
    private EventRecyclerviewAdapter adapter;

    private List<String> events;

    private TextView dayName;
    private TextView dayDate;
    private TextView sunUp;
    private TextView sunDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        gson = new Gson();

        Type type = new TypeToken<List<String>>() {}.getType();
        events = gson.fromJson(getIntent().getStringExtra("DAY_EVENTS"), type);

        eventRecyclerView = findViewById(R.id.events_recycler_view);

        dayName = findViewById(R.id.day_name);
        dayDate = findViewById(R.id.day_date);
        sunUp = findViewById(R.id.sun_up);
        sunDown = findViewById(R.id.sun_down);

        adapter = new EventRecyclerviewAdapter(events);

        eventRecyclerView.setAdapter(adapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dayName.setText(getIntent().getStringExtra("DAY_NAME"));
        dayDate.setText(getIntent().getStringExtra("DAY_DATE"));
        sunUp.setText(getIntent().getStringExtra("DAY_SUNUP"));
        sunDown.setText(getIntent().getStringExtra("DAY_SUNDOWN"));

        adapter.notifyDataSetChanged();

    }
}