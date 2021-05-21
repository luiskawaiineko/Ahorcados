package com.luismi.ahorcado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import util.ChatMessageHolder;

public class GameActivity extends AppCompatActivity {

    private DatabaseReference remoteSalas;
    private String idSala;
    private ImageView man;
    private EditText inputChat;
    private TextView palabraJuego;
    private RecyclerView chatView;
    private FirebaseRecyclerAdapter<String, ChatMessageHolder> adapter;
    private FirebaseRecyclerOptions<String> options;

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

        //configuración del adapter
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setReverseLayout(true);
        layout.setStackFromEnd(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(chatView.getContext(), layout.getOrientation());
        chatView.setAdapter(adapter);
        chatView.setLayoutManager(layout);
        adapter.startListening();
        chatView.addItemDecoration(dividerItemDecoration);
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
