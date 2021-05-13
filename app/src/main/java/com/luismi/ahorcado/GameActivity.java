package com.luismi.ahorcado;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dao.Sala;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idSala = getIntent().getExtras().getString("sala");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView showSalaCode = (TextView) findViewById(R.id.codigoSala);
        showSalaCode.setText(idSala);
    }
    public GameActivity() {

    }
}
