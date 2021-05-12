package com.luismi.ahorcado;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import dao.Sala;

public class ConnectActivity extends AppCompatActivity {
    private DatabaseReference remoteSalas;
    private EditText inputCodigoSala;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        remoteSalas = FirebaseDatabase.getInstance("https://ahorcado-59fbe-default-rtdb.europe-west1.firebasedatabase.app/").getReference("salas");
        inputCodigoSala = (EditText) findViewById(R.id.inputCodigoSala);
        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void createSala(View view) {
        Sala newSala = new Sala(getValidSalaNumber());
        newSala.setPalabra("");
        newSala.setPalabraJuego("");
        remoteSalas.child("" + newSala.getId()).setValue(newSala);
    }

    private int getValidSalaNumber() {
        Random r = new Random();
        final int[] number = {r.nextInt(1000)};
        remoteSalas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(inputCodigoSala.getText().toString())) {
                    number[0] = getValidSalaNumber();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
        return number[0];
    }

        public void connectSala (View view){
            if (inputCodigoSala.getText().toString().equals("")) {
                Toast.makeText(this.getApplicationContext(), "Error: debes introducir el código de la sala en el cuadro de texto de abajo.", Toast.LENGTH_SHORT).show();
            } else {
                remoteSalas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(inputCodigoSala.getText().toString())) {
                            Toast.makeText(getApplicationContext(),(snapshot.child(inputCodigoSala.getText().toString()).getValue().toString()), Toast.LENGTH_SHORT).show();
                            //openSala((Sala)snapshot.child(inputCodigoSala.getText().toString()).getValue());
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: la sala a la que deseas conectarte no existe.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        }

        public void openSala (Sala sala)
        {
            Toast.makeText(getApplicationContext(), sala.getPalabra(), Toast.LENGTH_SHORT).show();
        }
    }
