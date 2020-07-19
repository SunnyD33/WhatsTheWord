package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

public class Favorites extends AppCompatActivity {

    public ArrayList<String> favoritesList = new ArrayList<>();
    public ArrayAdapter adapter;
    public ListView lv;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        lv = findViewById(R.id.favorites_list);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,favoritesList);
        lv.setAdapter(adapter);
        Collections.sort(favoritesList);
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

    public void addWord(String word)
    {
        favoritesList.add(word);
    }
}
