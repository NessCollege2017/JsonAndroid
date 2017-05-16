package ness.edu.jsonandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Movie> movies = new ArrayList<>();
    TextView tvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvMovies = (TextView) findViewById(R.id.tvMovies);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread movieThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //code that runs in the background
                        //Uses-permission INTERNET in manifest
                        try {
                            String json = StreamIO.readWebSite("http://api.androidhive.info/json/movies.json");
                            //Decide if jsonObject Or JSON Array;
                            JSONArray moviesArray = new JSONArray(json);
                            for (int i = 0; i < moviesArray.length(); i++) {
                                ArrayList<String> genres = new ArrayList<>();
                                JSONObject movieObject = moviesArray.getJSONObject(i);
                                String title = movieObject.getString("title");
                                String image = movieObject.getString("image");
                                int releaseYear = movieObject.getInt("releaseYear");
                                double rating = movieObject.getDouble("rating");
                                JSONArray genresArray = movieObject.getJSONArray("genre");
                                for (int j = 0; j < genresArray.length(); j++) {
                                    String genre = genresArray.getString(j);
                                    genres.add(genre);
                                }
                                Movie movie = new Movie(title, image, genres, releaseYear, rating);
                                movies.add(movie);
                            }
                            Log.d("Ness", movies.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //code that runs on the UI Thread:
                                    tvMovies.setText(movies.toString());
                                }
                            });
                        } catch (IOException e) {
                            //TODO: Handle with dialog
                            e.printStackTrace();

                        } catch (JSONException ignored) {}
                    }
                });
                movieThread.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
