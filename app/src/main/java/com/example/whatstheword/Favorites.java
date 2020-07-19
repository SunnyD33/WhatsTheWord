package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;


public class Favorites extends AppCompatActivity {

    public ArrayList<String> favoritesList = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    public ListView lv;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ref;
    private String userID = mAuth.getCurrentUser().getUid();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Favorites");

        lv = (ListView) findViewById(R.id.favorites_list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,favoritesList);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        registerForContextMenu(lv);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                favoritesList.add(value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase());
                Collections.sort(favoritesList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                favoritesList.remove(value);
                Collections.sort(favoritesList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.favorites_list)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Options");
            String[] items = getResources().getStringArray(R.array.longClickMenu);
            for(int i = 0; i < items.length; i++)
            {
                menu.add(Menu.NONE, i, i, items[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemIndex = item.getItemId();
        String[] items = getResources().getStringArray(R.array.longClickMenu);
        String menuItemName = items[itemIndex];
        String itemName = "Delete?";
        return true;
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
        String userID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("Users").child(userID).child("Favorites");
        myRef.push().setValue(word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase());
    }
}
