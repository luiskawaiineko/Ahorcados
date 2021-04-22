package com.luismi.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button signUpButton = findViewById(R.id.signUpButton);
        final Button signInButton = findViewById(R.id.signUpButton);
        //registro
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        //inicio de sesión
        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });
    }
}
