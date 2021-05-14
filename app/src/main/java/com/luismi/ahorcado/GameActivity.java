package com.luismi.ahorcado;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        ImageView man = (ImageView) findViewById(R.id.manView);
        showSalaCode.setText(idSala);
        EditText inputChat=(EditText)findViewById(R.id.inputChat);
        inputChat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    
                }
                return false;
            }
        });
    }

    public GameActivity() {

    }
}
