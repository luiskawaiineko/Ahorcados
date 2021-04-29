package com.luismi.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
    }
    public void login(View view) {
        if (((EditText)findViewById(R.id.inputMail)).getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Error: debes introducir una dirección de correo.", Toast.LENGTH_SHORT).show();
        }
        else if (((EditText)findViewById(R.id.inputPass)).getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Error: debes introducir una contraseña.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(((EditText) findViewById(R.id.inputMail)).getText().toString(), ((EditText) findViewById(R.id.inputPass)).getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getBaseContext(),ConnectActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: el usuario o la contraseña no es correcto.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
