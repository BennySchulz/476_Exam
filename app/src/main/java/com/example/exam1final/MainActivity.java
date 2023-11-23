package com.example.exam1final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    TextView latlong;
    ImageView img1;
    ImageView img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latlong = findViewById(R.id.textViewLatLong);

        img1 = findViewById(R.id.imageView);
        img1.setVisibility(View.INVISIBLE);

        //img1.setVisibility(View.VISIBLE);
        //img2.setVisibility(View.VISIBLE);

        String url = "https://cdn.pixabay.com/photo/2017/11/06/18/39/apple-2924531_960_720.jpg";


        Intent intent = getIntent();
        String receivedLoc = intent.getStringExtra("MESSAGE_KEY");
        if(receivedLoc != null)
        {
            latlong.setText(receivedLoc);
            img1.setVisibility(View.INVISIBLE);
        }

        Context c = this;
        String receivedURL = intent.getStringExtra("URL_KEY");
        if(receivedURL != null)
        {
            img1.setVisibility(View.VISIBLE);
            Picasso.with(c).load(receivedURL).error(R.drawable.redx).into(img1);
            latlong.setVisibility(View.INVISIBLE);
        }




        Button act2Text = findViewById(R.id.buttonActivity2);
        act2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                switchToSecondActivity();
            }
        });

        ImageButton act2Img = findViewById(R.id.imageButtonActivity2);
        act2Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                switchToSecondActivity();
            }
        });

        Button act3Text = findViewById(R.id.buttonActivity3);
        act3Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                switchToThirdActivity();
            }
        });

        ImageButton act3Img = findViewById(R.id.imageButtonActivity3);
        act3Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                switchToThirdActivity();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Handle configuration changes if needed
    }

    private void switchToSecondActivity()
    {
        // Create an Intent to switch to the SecondActivity
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        latlong.setText("");
        startActivity(intent);
    }

    private void switchToThirdActivity()
    {
        // Create an Intent to switch to the SecondActivity
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        latlong.setText("");
        startActivity(intent);
    }
}