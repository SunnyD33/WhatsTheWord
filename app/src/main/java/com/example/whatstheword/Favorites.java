package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Favorites extends AppCompatActivity {

    public ArrayList<String> favList = new ArrayList<>();
    public ListView listView;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Favorites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.favoritesList);
        listView.setClickable(true);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favRef = dbRef.child("Users").child(currentUser).child("Favorites");

        favRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                favList.add(value);
                Collections.sort(favList);
                Log.d("Favorites", favList.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),listView.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Favorites.this,SearchScreen.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            startActivity(new Intent(Favorites.this,SearchScreen.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
