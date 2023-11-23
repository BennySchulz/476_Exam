package com.example.exam1final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity3 extends AppCompatActivity {


    EditText url;
    String savedURL = "";
    String currURL = "";
    ImageView imageView;

    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        url = findViewById(R.id.editTextURL);
        imageView = findViewById(R.id.imageViewPlaceHold);
        imageView.setActivated(true);

        c = this;



        Button fetch = findViewById(R.id.buttonFetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String imageUrl = url.getText().toString();
                if (!imageUrl.isEmpty())
                {
                    Picasso.with(c).load(imageUrl).error(R.drawable.redx).into(imageView);
                    currURL = imageUrl;
                }
            }
        });

        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                savedURL = String.valueOf(url.getText());
            }
        });


        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switchToMainActivity();
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the location data to the bundle
        outState.putString("curr", currURL);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        String curr = savedInstanceState.getString("curr");
        Picasso.with(c).load(curr).error(R.drawable.redx).into(imageView);
    }

    private void switchToMainActivity()
    {
        // Create an Intent to switch to the SecondActivity
        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
        if(!savedURL.equals(""))
        {
            intent.putExtra("URL_KEY", savedURL);
        }
        startActivity(intent);
    }
}