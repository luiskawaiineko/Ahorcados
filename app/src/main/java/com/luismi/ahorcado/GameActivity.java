package com.luismi.ahorcado;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.javafaker.Faker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import util.ChatMessageHolder;

public class GameActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference remoteSalas;
    private String idSala;
    private FirebaseUser currentUser;
    private ImageView man;
    private TextView failedLetters;
    private EditText inputChat;
    private TextView palabraJuego;
    private RecyclerView chatView;
    private FirebaseRecyclerAdapter<String, ChatMessageHolder> adapter;
    private FirebaseRecyclerOptions<String> options;
    private String palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //definición variables
        remoteSalas = FirebaseDatabase.getInstance("https://ahorcado-59fbe-default-rtdb.europe-west1.firebasedatabase.app/").getReference("salas");
        palabraJuego = findViewById(R.id.gameWord);
        idSala = getIntent().getExtras().getString("sala");
        man = findViewById(R.id.manView);
        inputChat = findViewById(R.id.inputChat);
        chatView = findViewById(R.id.chatView);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        failedLetters = findViewById(R.id.failedLetters);
        chatView.setHasFixedSize(true);
        palabra = "";
        options = new FirebaseRecyclerOptions.Builder<String>().setQuery(remoteSalas.child(idSala).child("chat"), String.class).build();
        adapter = new FirebaseRecyclerAdapter<String, ChatMessageHolder>(options) {
            @Override
            public ChatMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
                return new ChatMessageHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatMessageHolder holder, int position, @NonNull String model) {
                holder.setChatMessage(model);
            }
        };

        //exposición del código de la sala
        TextView showSalaCode = findViewById(R.id.codigoSala);
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

        remoteSalas.child(idSala).child("palabra").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                palabra = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        remoteSalas.child(idSala).child("letrasDescartadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                failedLetters.setText(snapshot.getValue().toString().replace("", " ").trim());
                //muñeco
                switch (snapshot.getValue().toString().length())
                {
                    case 0:
                        man.setImageResource(R.drawable.hangman0);
                        break;
                    case 1:
                        man.setImageResource(R.drawable.hangman1);
                        break;
                    case 2:
                        man.setImageResource(R.drawable.hangman2);
                        break;
                    case 3:
                        man.setImageResource(R.drawable.hangman3);
                        break;
                    case 4:
                        man.setImageResource(R.drawable.hangman4);
                        break;
                    case 5:
                        man.setImageResource(R.drawable.hangman5);
                        break;
                    case 6:
                        man.setImageResource(R.drawable.hangman6);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //configuración del adapter
        LinearLayoutManager layout = new LinearLayoutManager(this);
        //layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(chatView.getContext(), layout.getOrientation());
        chatView.setAdapter(adapter);
        chatView.setLayoutManager(layout);
        adapter.startListening();
        chatView.addItemDecoration(dividerItemDecoration);
        remoteSalas.child(idSala).child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatView.smoothScrollToPosition(chatView.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void sendChatMessage(View view) {
        final String stringChat = inputChat.getText().toString();
        if (!stringChat.equals("")) {
            if (stringChat.length() == 1) {
                //juega con la letra
                if (stringChat.toString().matches("[a-zA-Z]+")) {
                    remoteSalas.child(idSala).child("lastTurno").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue().toString().equals(currentUser.getUid()))
                            {
                                Toast.makeText(getApplicationContext(), "Error: El mismo jugador no puede jugar dos letras seguidas.", Toast.LENGTH_SHORT).show();
                            }else{
                                if (palabraJuego.getText().toString().trim().contains(stringChat.trim())||failedLetters.getText().toString().trim().contains(stringChat.trim()))
                                {
                                    Toast.makeText(getApplicationContext(), "Error: La letra que has introducido ya fue jugada anteriormente.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    remoteSalas.child(idSala).child("lastTurno").setValue(currentUser.getUid());
                                    if (palabra.contains(stringChat))
                                    {
                                        StringBuilder newPalabraJuego = new StringBuilder(palabraJuego.getText().toString());
                                        for(int i=0; i<palabra.length();i++)
                                        {
                                            if (palabra.charAt(i) == stringChat.charAt(0))
                                            {
                                                newPalabraJuego.setCharAt(i,palabra.charAt(i));
                                            }
                                        }
                                        remoteSalas.child(idSala).child("palabraJuego").setValue(newPalabraJuego.toString());
                                    }else
                                    {
                                        if (failedLetters.getText().toString().replace(" ","").length()>=5)
                                        {
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    remoteSalas.child(idSala).child("chat").child("" + chatView.getAdapter().getItemCount()).setValue(currentUser.getDisplayName() + " ha matado al muñeco");
                                                }
                                            }, 1000);
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    remoteSalas.child(idSala).child("letrasDescartadas").setValue("");
                                                    Faker faker = new Faker();
                                                    String newPalabra = faker.book().title().replaceAll("[^a-zA-Z ]", "");
                                                    remoteSalas.child(idSala).child("palabra").setValue(newPalabra);
                                                    remoteSalas.child(idSala).child("palabraJuego").setValue(newPalabra.replaceAll("[^ ]", "_"));
                                                }
                                            }, 5000);
                                        }
                                        remoteSalas.child(idSala).child("letrasDescartadas").setValue(failedLetters.getText().toString().replace(" ","")+stringChat);
                                    }
                                    remoteSalas.child(idSala).child("chat").child("" + chatView.getAdapter().getItemCount()).setValue(currentUser.getDisplayName() + " ha jugado con la letra " + stringChat);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Error: para jugar, debes introducir una letra en el chat.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //envía mensaje al chat
                remoteSalas.child(idSala).child("chat").child("" + chatView.getAdapter().getItemCount()).setValue(currentUser.getDisplayName() + ": " + stringChat);
            }
            inputChat.getText().clear();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onBackPressed() {
        if (adapter != null) {
            adapter.stopListening();
        }
        Intent intent = new Intent(getBaseContext(), ConnectActivity.class);
        startActivity(intent);
    }
}
