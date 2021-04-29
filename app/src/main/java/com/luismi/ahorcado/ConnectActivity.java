package com.luismi.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnectActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }
}
