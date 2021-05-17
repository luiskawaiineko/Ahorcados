package dao;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int id;
    private String palabra;
    private String palabraJuego;
    private List<Jugador>jugadores;
    private String letrasDescartadas;
    private List<String>chat;
    private int timer;
    private int turno;

    public Sala(){
    jugadores = new ArrayList<>();
    chat = new ArrayList<>();
    chat.add("Usuario1: Hola mundo");
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Sala(int id) {
        this.id = id;
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

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getId() {
        return id;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
