package com.luismi.ahorcado;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void register(View view){
        if (((EditText)findViewById(R.id.inputMail)).getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Error: debes introducir una dirección de correo.", Toast.LENGTH_SHORT).show();
        }
        else if (((EditText)findViewById(R.id.inputPass)).getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Error: debes introducir una contraseña.", Toast.LENGTH_SHORT).show();
        }
        else if (((EditText)findViewById(R.id.inputUsername)).getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Error: debes introducir un nombre de usuario.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(((EditText) findViewById(R.id.inputMail)).getText().toString(), ((EditText) findViewById(R.id.inputPass)).getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(((EditText)findViewById(R.id.inputUsername)).getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates);
                                Intent intent = new Intent(getBaseContext(),ConnectActivity.class);
                                startActivity(intent);
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText(getApplicationContext(), "Error: la contraseña es demasiado débil, debería tener mínimo 6 carácteres.", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(getApplicationContext(), "Error: la dirección de correo que has introducido no es válida.", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(getApplicationContext(), "Error: ya existe un usuario con la misma dirección de correo.", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
        }
    }
}
