package ness.edu.jsonandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;

/**
 * Created by Android2017 on 5/16/2017.
 */

public class MyThread extends Thread {
    @Override
    public void run() {
        //Uses-permission INTERNET in manifest
        try {
            String json = StreamIO.readWebSite("http://api.androidhive.info/json/movies.json");
            //Decide if jsonObject Or JSON Array;
            JSONArray moviesArray = new JSONArray(json);
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieObject = moviesArray.getJSONObject(i);
                String title = movieObject.getString("title");

                String image = movieObject.getString("image");

                int releaseYear = movieObject.getInt("releaseYear");

                double rating = movieObject.getDouble("rating");

                JSONArray genresArray = movieObject.getJSONArray("genre");

                for (int j = 0; j < genresArray.length(); j++) {
                    String genre = genresArray.getString(j);
                }
            }
        } catch (IOException e) {
            //TODO: Handle with dialog
            e.printStackTrace();

        } catch (JSONException ignored) {}
    }
}
