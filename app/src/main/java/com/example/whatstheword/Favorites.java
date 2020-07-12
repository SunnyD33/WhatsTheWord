package com.example.whatstheword;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    public ListView lv;
    public ArrayList<String> favoritesList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        lv = (ListView) findViewById(R.id.favorites_list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,favoritesList);
        lv.setAdapter(adapter);
    }
}
