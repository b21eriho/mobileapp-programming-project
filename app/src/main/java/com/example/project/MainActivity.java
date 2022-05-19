package com.example.project;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;

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

    private DataBaseHelper db;

    private SharedPreferences myPreferenceRef;
    private SharedPreferences.Editor myPreferenceEditor;

    private Gson gson;

    private Switch toggleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPreferenceRef = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        myPreferenceEditor = myPreferenceRef.edit();

        db = new DataBaseHelper(this);
        gson = new Gson();
        dayList = new ArrayList<Day>();
        dayRecyclerView = findViewById(R.id.recycler_view);
        adapter = new DayRecyclerViewAdapter(dayList);
        dayRecyclerView.setAdapter(adapter);
        dayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new JsonTask(MainActivity.this).execute(JSON_URL);

        toggleSwitch = findViewById(R.id.eventfullToggle);

        if(myPreferenceRef.getBoolean("onlyEventful", false)){
            toggleSwitch.setChecked(true);
        }
        else{
            toggleSwitch.setChecked(false);
            myPreferenceEditor.putBoolean("onlyEventful", false);
            myPreferenceEditor.apply();
        }

        toggleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPreferenceEditor.putBoolean("onlyEventful", toggleSwitch.isChecked());
                myPreferenceEditor.apply();
                updateRecyclerView(myPreferenceRef.getBoolean("onlyEventful", true));
            }
        });
    }

    public Cursor getDataBaseCursor(boolean onlyEventful){
        String sortQuerryString = "";
        if(onlyEventful){sortQuerryString += " WHERE " + DataBaseHelper.COLLUMN_EVENTS + " != '[]'";};

        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_LIGHTRISE_DAYS + sortQuerryString + " ORDER BY " + DataBaseHelper.COLLUMN_DATE, null, null);
        return cursor;
    }

    public void updateRecyclerView(boolean onlyEventful){
        Cursor cursor = getDataBaseCursor(onlyEventful);
        List<Day> tmpDays = new ArrayList<>();

        while (cursor.moveToNext()) {
            Type type = new TypeToken<List<String>>() {}.getType();

            Day day = new Day(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_SUNUP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_SUNDOWN)),
                    gson.<List<String>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_EVENTS)), type)
            );
            tmpDays.add(day);
        }
        cursor.close();
        dayList.clear();
        dayList.addAll(tmpDays);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Type type = new TypeToken<List<Day>>() {}.getType();
        List<Day> daysFromJson = gson.fromJson(json, type);

        db.getWritableDatabase().execSQL("DELETE FROM " + DataBaseHelper.TABLE_LIGHTRISE_DAYS);

        for(int i = 0; i < daysFromJson.size(); i++){
            Day thisDay = daysFromJson.get(i);
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLLUMN_DATE, thisDay.getDate());
            values.put(DataBaseHelper.COLLUMN_NAME, thisDay.getName());
            values.put(DataBaseHelper.COLLUMN_SUNUP, thisDay.getSunUp());
            values.put(DataBaseHelper.COLLUMN_SUNDOWN, thisDay.getSunDown());
            values.put(DataBaseHelper.COLLUMN_EVENTS, gson.toJson(thisDay.getEvents()));
            db.getWritableDatabase().insert(DataBaseHelper.TABLE_LIGHTRISE_DAYS, null, values);

        }
        updateRecyclerView(myPreferenceRef.getBoolean("onlyEventful", false));
    }
}
