package dao;

import java.util.List;

public class Sala {
    private int id;
    private String palabra;
    private String palabraJuego;
    private int estadoAhorcado;
    private List<Jugador>jugadores;

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

    public int getEstadoAhorcado() {
        return estadoAhorcado;
    }

    public void setEstadoAhorcado(int estadoAhorcado) {
        this.estadoAhorcado = estadoAhorcado;
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
