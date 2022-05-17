package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=b21eriho";

    private RecyclerView dayRecyclerView;
    private DayRecyclerViewAdapter adapter;
    private List<Day> dayList;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();
        dayList = new ArrayList<Day>();
        dayRecyclerView = findViewById(R.id.recycler_view);
        adapter = new DayRecyclerViewAdapter(dayList);
        dayRecyclerView.setAdapter(adapter);
        dayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new JsonTask(MainActivity.this).execute(JSON_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        new JsonTask(this).execute(JSON_URL);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh_list) {
            new JsonTask(this).execute(JSON_URL);
        }

        if (id == R.id.action_show_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostExecute(String json) {

        Log.d("==>", json);

        Type type = new TypeToken<List<Day>>() {}.getType();
        List<Day> daysFromJson = gson.fromJson(json, type);

        dayList.clear();
        dayList.addAll(daysFromJson);
        adapter.notifyDataSetChanged();
    }
}
