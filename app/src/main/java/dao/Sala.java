package dao;

import java.util.List;

public class Sala {
    private int id;
    private String palabra;
    private String palabraJuego;
    private List<Jugador>jugadores;
    private List<String> letrasDescartadas;
    private List<>
    private int timer;
    private int turno;

    public Sala(){
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

    public List<String> getLetrasDescartadas() {
        return letrasDescartadas;
    }

    public void setLetrasDescartadas(List<String> letrasDescartadas) {
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
