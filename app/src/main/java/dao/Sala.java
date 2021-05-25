package dao;

import com.github.javafaker.Faker;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int id;
    private String palabra;
    private String palabraJuego;
    private List<String>jugadores;
    private String letrasDescartadas;
    private List<String>chat;
    private int turno;

    public void setup(){
}

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Sala(int id) {
        this.id = id;
        jugadores = new ArrayList<String>();
        chat = new ArrayList<String>();
        this.chat.add("Sala creada con éxito!");
        Faker faker = new Faker();
        this.palabra = faker.book().title();
        this.palabra = palabra.replaceAll("[^a-zA-Z ]", "");
        this.palabraJuego = palabra.replaceAll("[^ ]", "_");
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabraJuego() {
        return palabraJuego;
    }

    public void setPalabraJuego(String palabraJuego) {
        this.palabraJuego = palabraJuego;
    }

    public String getLetrasDescartadas() {
        return letrasDescartadas;
    }

    public List<String> getChat() {
        return chat;
    }

    public void addChatMessage(String chatMessage) {
        this.chat.add(chatMessage);
    }

    public void setChat(List<String> chat) {
        this.chat = chat;
    }

    public void setLetrasDescartadas(String letrasDescartadas) {
        this.letrasDescartadas = letrasDescartadas;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public int getId() {
        return id;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }
}
