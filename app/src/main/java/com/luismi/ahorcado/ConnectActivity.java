package com.luismi.ahorcado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import dao.Sala;

public class ConnectActivity extends AppCompatActivity {
    private DatabaseReference remoteSalas;

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
    public void createSala(View view){
        Random r = new Random();
        Sala newSala = new Sala(r.nextInt(1000));
        remoteSalas = FirebaseDatabase.getInstance().getReference();
        String hola = remoteSalas.setValue(newSala).getException().toString();
        boolean stop = true;
    }

    public void connectSala(View view){

    }
}
