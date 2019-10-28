package com.mygdx.ttrispo.BaseDeDatos;

public class Jugador {
    private int id;
    private String nombre;
    private long puntuacion;

    public Jugador(int id, String nombre, long puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(long puntuacion) {
        this.puntuacion = puntuacion;
    }
}
