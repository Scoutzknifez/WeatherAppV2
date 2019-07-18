package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scoutzknifez.weatherappv2.Utility.Utils;

public class MainActivity extends AppCompatActivity {
    public static MainActivity selfRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfRef = this;
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