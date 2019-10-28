package com.mygdx.ttrispo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.Pantalla.Partida;

public class Tablero extends Actor {
    public static int tablero[][];
    private Partida partida;
    private Texture imagenTablero;
    private Texture imagenPiezaSiguiente;

    public  int tamanyoPieza = 60; //Tamaño Pieza
    public  int tableroX = 20;
    public  int tableroY = 500;

    public Tablero(Partida partida) {
        this.partida = partida;
        this.tablero = new int[10][20];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //pintarPieza();
        int posicionX,posicionY,tipo;
        setScaleX(50);
        Texture imagenBloque;

        for (int i = 0; i < this.tablero.length; i++){
            for (int j = 0; j < this.tablero[i].length; j++){

                posicionX = (tamanyoPieza * i);
                posicionY = Gdx.graphics.getHeight() - (tamanyoPieza * j);

                tipo = this.tablero[i][j];
                if(tipo >= 1) {
                    imagenBloque = partida.getTexturaPieza(tipo);
                    //batch.setColor(1, 1, 1, 1f);
                    batch.draw(imagenBloque, posicionX - tableroX, posicionY - tableroY, 0, 0, tamanyoPieza, tamanyoPieza);
                }
            }
        }
        if (imagenPiezaSiguiente != null){
            batch.draw(imagenPiezaSiguiente, Gdx.graphics.getWidth() - (imagenPiezaSiguiente.getWidth() - 50), Gdx.graphics.getHeight()- imagenPiezaSiguiente.getHeight());
        }

    }

    public void insertarBloquesDePieza(int bloques[][], int tipo) {
        int columnas, filas;
        for (int i = 0; i < bloques.length; i++) {
            columnas = bloques[i][1];
            filas = bloques[i][0];

            // Comprobar si se sale de la pantalla
            if(columnas < 10 && columnas >= 0 && filas >= 0 && filas < 20){
                tablero[bloques[i][1]][bloques[i][0]] = tipo;
            }
        }
    }

    public boolean seProduceColision(int bloques[][]){
        int columnas, filas;
        try {
            for (int i = 0; i < bloques.length; i++) {
                columnas = bloques[i][1];
                filas = bloques[i][0];
                // Comprobar si se sale de la pantalla
                if (columnas >= 10 || filas >= 20) {
                    return true;
                }
                // Colisiona con otro bloque
                if (tablero[bloques[i][1]][bloques[i][0]] != 0) {
                    return true;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){ //Lanzo Excepcion
            return true;
        }
        return false;
    }
    /*
    A REFACTORIZAR.
    El numero de filas posicionY columnas. No consigo obtener correctamente el el numero
     */
    public boolean comprobarLineaCompleta() {
        int numeroColumnas = 10;
        int filas = 20;
        int valorFila = 0;

        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 10; j++){
                if(tablero[j][i]!= 0){
                    valorFila++;
                }
            }

            if(valorFila == numeroColumnas){
                partida.setPuntuacion(30);
                eliminarfila(i);
                bajarFilaAnterior(i);
            }
            valorFila = 0;
        }
        return false;
    }

    private void eliminarfila(int fila) {
        for(int j = 0; j < 10; j++){
            tablero[j][fila] = 0;
        }
    }

    public boolean comprobarGameOver(int bloques[][]){
        for (int i = 0; i < bloques.length; i++) {
            if (bloques[i][0] == 0){
                return true;
            }
        }
        return false;
    }

    /**
    Se recoge la fila eliminada posicionY desde ahi hasta arriba se copia la fila anterior en la actual.
    Habria que darle una vuelta en el siguiente sprint porque es un poco chapuza.

    Posible refatorizacion: Si son todpo 0 pues que no baje mas, pero eso ya mas adelante
     */
    private void bajarFilaAnterior(int fila) {
        for(;fila > 0; fila--){
            for(int c = 0; c < 10; c++){
                tablero[c][fila] = tablero[c][fila-1];
            }
        }
        for (int i = 0; i < 10; i++){ //Limpiamos la primera fila
            tablero[i][0] = 0;
        }
    }

    public void setImagenPiezaSiguiente(Texture imagenPiezaSiguiente) {
        this.imagenPiezaSiguiente = imagenPiezaSiguiente;
    }
}
