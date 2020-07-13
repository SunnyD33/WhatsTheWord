package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    public ListView lv;
    public ArrayList<String> favoritesList = new ArrayList<String>();
    public ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        lv = (ListView) findViewById(R.id.favorites_list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,favoritesList);
        lv.setAdapter(adapter);
        favoritesList.add("absent");
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorites_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_to_search_button:
                Intent intent = new Intent(Favorites.this, SearchScreen.class);
                startActivity(intent);
                break;

            case R.id.logoutButton:
                Intent intent1 = new Intent(Favorites.this,MainActivity.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
