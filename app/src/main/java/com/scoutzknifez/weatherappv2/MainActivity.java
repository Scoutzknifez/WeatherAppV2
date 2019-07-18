package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scoutzknifez.weatherappv2.Utility.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.initializeApplication();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            System.out.println("Thread crashed");
        }
    }
}