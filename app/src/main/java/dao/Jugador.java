package dao;

public class Jugador {
    private String uid;
    private String nombre;
    private boolean jugando;

    public Jugador(String uid, String nombre) {
        this.uid = uid;
        this.nombre = nombre;
        this.jugando = false;
    }

    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }
}
