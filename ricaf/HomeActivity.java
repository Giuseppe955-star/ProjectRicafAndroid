package com.domicilio.ricaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void MenuAction(View view) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);


    }


    public void ProfiloAction(View view) {

        Intent intent = new Intent(this, ProfiloActivity.class);
        startActivity(intent);


    }
}
