package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SearchScreen extends AppCompatActivity {

    private Button clearButton;
    private String url;
    public TextView defBox;
    public EditText enterWord;
    public Menu favoritesMenu;
    public boolean isFavorite;

    public String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    public DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference favRef = dbRef.child("Users").child(currentUser).child("Favorites");
    public ArrayList<String> currentFavorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lookup");

        defBox = (TextView) findViewById(R.id.definitionBox);
        enterWord = (EditText) findViewById(R.id.search_bar);
        clearButton = findViewById(R.id.search1ClearButton);

        favRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                currentFavorites.add(value);
                Collections.sort(currentFavorites);
                Log.d("Favorites", currentFavorites.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                currentFavorites.remove(value);
                Collections.sort(currentFavorites);
                Log.d("Favorites", currentFavorites.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        enterWord.requestFocus();
        enterWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    requestApi(textView);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    enterWord.clearFocus();

                    MenuItem favoritesMenuItem = favoritesMenu.findItem(R.id.setFavorite);
                    favoritesMenuItem.setIcon(R.drawable.ic_baseline_star_outline_24);



                    favRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String value = dataSnapshot.getValue(String.class);
                            currentFavorites.add(value);
                            Collections.sort(currentFavorites);
                            Log.d("Favorites", currentFavorites.toString());
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            currentFavorites.remove(value);
                            Collections.sort(currentFavorites);
                            Log.d("Favorites", currentFavorites.toString());
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    for (int j = 0; j < currentFavorites.size(); j++) {
                        String word = enterWord.getText().toString();
                        if (word != null) {
                            if ((word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()).equals(currentFavorites.get(j))) {
                                favoritesMenuItem = favoritesMenu.findItem(R.id.setFavorite);
                                //Drawable drawable = favoritesMenuItem.getIcon().mutate();
                                favoritesMenuItem.setIcon(R.drawable.ic_baseline_star_24);
                                isFavorite = true;
                                currentFavorites.clear();
                            }
                        }
                    }

                    return true;
                }

                return false;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                enterWord.setText("");
                enterWord.requestFocus();
                defBox.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(enterWord, InputMethodManager.SHOW_IMPLICIT);

                MenuItem favoritesMenuItem = favoritesMenu.findItem(R.id.setFavorite);
                //Drawable drawable = favoritesMenuItem.getIcon().mutate();

                favoritesMenuItem.setIcon(R.drawable.ic_baseline_star_outline_24);


            }
        });
    }

    public void requestApi(View v) {
        DictionaryRequest request = new DictionaryRequest(this, defBox);
        DictionaryApi dictionaryApi = new DictionaryApi();
        url = dictionaryApi.dictionaryEntries(enterWord.getText().toString());
        request.execute(url);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SearchScreen.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        favoritesMenu = menu;
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(SearchScreen.this, MainActivity.class));
            finish();
            return true;
        }

        if (item.getItemId() == R.id.favorites) {
            startActivity(new Intent(SearchScreen.this, Favorites.class));
            finish();
            return true;
        }

        if (item.getItemId() == R.id.setFavorite) {

            //Check that user cannot save a favorite if there is no text in search field
            if (!enterWord.getText().toString().equals("")) {

                if (!isFavorite) {
                    isFavorite = true;
                    item.setIcon(R.drawable.ic_baseline_star_24);
                    addFavorite(enterWord.getText().toString());
                    Toast.makeText(getApplicationContext(), "Saved to favorites", Toast.LENGTH_SHORT).show();

                } else {
                    isFavorite = false;
                    item.setIcon(R.drawable.ic_baseline_star_outline_24);
                    deleteFavorite(enterWord.getText().toString());
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a word to save", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFavorite(String word) {
        favRef.push().setValue(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
    }

    public void deleteFavorite(String word) {
        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getValue(String.class);
                    if ((word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase()).equals(value)) {
                        ds.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void logout() {
        //Toast values for user logout
        final Context context = getApplicationContext();
        final CharSequence text = "Logging out";
        final int duration = Toast.LENGTH_SHORT;

        //Sign user out of Firebase
        FirebaseAuth.getInstance().signOut();

        //Display message to user that they have logged out
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //Switch to login screen
        Intent intent = new Intent(SearchScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
