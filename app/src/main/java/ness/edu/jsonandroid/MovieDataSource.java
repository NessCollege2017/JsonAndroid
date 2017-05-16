package ness.edu.jsonandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;


public class MovieDataSource{

    public interface OnDataArrivedListener{
        void onDataArrived(ArrayList<Movie> movies, Exception e);
    }

    public static void getMovies(final OnDataArrivedListener listener){
        Thread movieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = StreamIO.readWebSite("http://api.androidhive.info/json/movies.json");
                    ArrayList<Movie> movies = parse(json);
                    listener.onDataArrived(movies, null);
                } catch (IOException | JSONException e) {
                    listener.onDataArrived(null, e);
                }
            }
        });
        movieThread.start();
    }

    private static ArrayList<Movie> parse(String json) throws JSONException {
        //code that runs in the background
        //Uses-permission INTERNET in manifest
        ArrayList<Movie> movies = new ArrayList<>();
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

        return movies;
    }
}
