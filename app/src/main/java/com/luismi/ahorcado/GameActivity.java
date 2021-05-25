package com.luismi.ahorcado;

import android.os.Bundle;
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
    private EditText inputChat;
    private TextView palabraJuego;
    private RecyclerView chatView;
    private FirebaseRecyclerAdapter<String, ChatMessageHolder> adapter;
    private FirebaseRecyclerOptions<String> options;
    private boolean turno = false;

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
        chatView.setHasFixedSize(true);
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
        remoteSalas.child(idSala).child("turno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot turnoSnapshot) {
                remoteSalas.child(idSala).child("jugadores").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot jugadoresSnapshot) {
                        int position = 0;
                        int looper = 0;
                        for (DataSnapshot jugadores : jugadoresSnapshot.getChildren()) {
                            if (jugadores.getValue().equals(currentUser.getUid())) {
                                position = looper;
                            }
                            looper++;
                        }
                        turno = turnoSnapshot.getValue().toString().trim().equals(""+position);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
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
        if (!inputChat.getText().equals("")) {
            if (inputChat.getText().length() == 1) {
                //juega con la letra

                if (inputChat.getText().toString().matches("[a-zA-Z]+")) {

                } else {
                    Toast.makeText(getApplicationContext(), "Error: para jugar, debes introducir una letra en el chat.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //envía mensaje al chat
                remoteSalas.child(idSala).child("chat").child("" + chatView.getAdapter().getItemCount()).setValue(currentUser.getDisplayName() + ": " + inputChat.getText());
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
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }

}
