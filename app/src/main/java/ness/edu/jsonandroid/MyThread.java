package ness.edu.jsonandroid;

import android.util.Log;

/**
 * Created by Android2017 on 5/16/2017.
 */

public class MyThread extends Thread {
    @Override
    public void run() {
        StreamIO.readWebSite("...");
    }
}
