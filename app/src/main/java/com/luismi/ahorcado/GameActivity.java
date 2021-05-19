package com.luismi.ahorcado;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    private DatabaseReference remoteSalas;
    private String idSala;
    private ImageView man;
    private EditText inputChat;
    private TextView palabraJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //definición variables
        remoteSalas = FirebaseDatabase.getInstance("https://ahorcado-59fbe-default-rtdb.europe-west1.firebasedatabase.app/").getReference("salas");
        palabraJuego = (TextView) findViewById(R.id.gameWord);
        idSala = getIntent().getExtras().getString("sala");
        man = (ImageView) findViewById(R.id.manView);
        inputChat=(EditText)findViewById(R.id.inputChat);

        //exposición del código de la sala
        TextView showSalaCode = (TextView) findViewById(R.id.codigoSala);
        showSalaCode.setText(idSala);
        //listeners
        remoteSalas.child(idSala).child("palabraJuego").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                palabraJuego.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        remote
    }

    public GameActivity() {

    }
}
