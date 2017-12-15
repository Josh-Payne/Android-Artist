package com.joshpayne1.androidartist;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.joshpayne1.androidartist.CanvasActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playActivity(View view){
        Intent intent = new Intent(this, CanvasActivity.class);
        startActivity(intent);
    }
    public void editActivity(View view){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }
}
