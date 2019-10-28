package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.ttrispo.Gestores.GestorEstado;
import com.mygdx.ttrispo.Gestores.GestorPiezas;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;
import com.mygdx.ttrispo.Pieza.Pieza;
import com.mygdx.ttrispo.Tablero;
import com.mygdx.ttrispo.Procesador;

public class Partida extends PantallaBase {
    private Texture fondoPartida;
    private GestorRecursos gestorRecursos;
    private Tablero tablero;
    private ProgresoPartida progresoPartida;
    private GestorEstado gestorEstado;
    private GestorPiezas gestorPiezas;
    public static float posicionX, posicionY;
    private Procesador procesador;
    private static int puntuacion;

    public Partida(MyGdxGame game) {
        super(game);
        gestorEstado = new GestorEstado(this);
        gestorPiezas = new GestorPiezas(this);
        procesador = new Procesador(gestorEstado);
        fondoPartida = GestorRecursos.get("background.jpeg");

        tablero = new Tablero(this);
        tablero.setPosition(posicionX, posicionY);
        progresoPartida = new ProgresoPartida(this);

        stage.addActor(tablero);
        stage.addActor(progresoPartida);

        gestorRecursos.cargarImagenes();

        this.puntuacion = 0;
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(procesador);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fondoPartida, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        cicloDeVida(delta); // Ciclo de vida
        stage.draw();  // Pintar los actores
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void cicloDeVida(float delta) {
        switch (gestorEstado.getEstado(delta)) {

            case (GestorEstado.REPOSO): //Si el Gestor esta en reposo
                if (gestorPiezas.getPiezaActual() == null) { //Y no hay pieza siguiente
                    gestorEstado.setEstado(GestorEstado.SINPIEZA); //Modo Sin Pieza
                }
                break;

            case (GestorEstado.SINPIEZA):
                estadoGestorSinPieza(); //Selecciona una nueva Pieza y vuelve al modo de Reposo
                break;

            case (GestorEstado.IZQUIERDA):
                estadoGestorDesplazarIzq();
                break;

            case (GestorEstado.DERECHA):
                moverDerechaState();
                break;
            // La pieza intenta caer
            case (GestorEstado.CAER):
                caerState();
                break;
            case (GestorEstado.GIRO):
                giroState();
                break;
            case (GestorEstado.BLOQUEAR):
                bloquearPieza();
                break;
        }
    }

    private void bloquearPieza() {
        // La pieza no puede bajar
        Pieza currentPieza = gestorPiezas.getPiezaActual();
        tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), currentPieza.getTipo());
        if (tablero.comprobarGameOver(currentPieza.getPosicionPieza())) {
            dispose();
            game.setScreen(game.pantallaGameOver);
        }
        tablero.comprobarLineaCompleta();
        gestorPiezas.bloquearPieza();
        gestorEstado.setEstado(GestorEstado.SINPIEZA);
    }

    private void giroState() {
        Pieza currentPieza;
        currentPieza = gestorPiezas.getPiezaActual();
        tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), Pieza.VACIA);
        currentPieza.girarDer();
        int piezaGirada[][] = currentPieza.getPosicionPieza();
        if (tablero.seProduceColision(piezaGirada)) {
            // La pieza no puede girar
            currentPieza.girarIz();
            tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), currentPieza.getTipo());
        } else {
            tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), currentPieza.getTipo());
        }
        gestorEstado.setEstado(GestorEstado.REPOSO);
    }

    private void caerState() {
        Pieza currentPieza;
        currentPieza = gestorPiezas.getPiezaActual();

        int posicionPiezaAbajo[][] = currentPieza.getPosicionAbajo();
        tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), Pieza.VACIA);
        if (tablero.seProduceColision(posicionPiezaAbajo)) {
            gestorEstado.setEstado(GestorEstado.BLOQUEAR);
        } else {
            // La pieza puede baja
            tablero.insertarBloquesDePieza(posicionPiezaAbajo, currentPieza.getTipo());
            currentPieza.setFila(currentPieza.getFila() + 1);
            gestorEstado.setEstado(GestorEstado.REPOSO);
        }
    }

    private void moverDerechaState() {
        Pieza currentPieza;
        currentPieza = gestorPiezas.getPiezaActual();
        int posicionPiezaDerecha[][] = currentPieza.getPosicionDerecha();
        tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), Pieza.VACIA);
        if (tablero.seProduceColision(posicionPiezaDerecha)) {
            tablero.insertarBloquesDePieza(currentPieza.getPosicionPieza(), currentPieza.getTipo());
            //si no puede seguir moviendo a la derecha pues ahi se queda
        } else {
            tablero.insertarBloquesDePieza(posicionPiezaDerecha, currentPieza.getTipo());
            currentPieza.setColumna(currentPieza.getColumna() + 1);
        }
        //Cambia a reposo. pero esto hay que refactorizarlo por el amor de dios
        gestorEstado.setEstado(gestorEstado.REPOSO);
    }

    private void estadoGestorDesplazarIzq() {
        Pieza piezaActual = gestorPiezas.getPiezaActual();

        int posicionPiezaIzquierda[][] = piezaActual.getPosicionIzquierda();
        tablero.insertarBloquesDePieza(piezaActual.getPosicionPieza(), Pieza.VACIA);

        if (tablero.seProduceColision(posicionPiezaIzquierda)) {
            tablero.insertarBloquesDePieza(piezaActual.getPosicionPieza(), piezaActual.getTipo());
            //si no puede seguir moviendo a la izquierda pues ahi se queda
        } else {
            tablero.insertarBloquesDePieza(posicionPiezaIzquierda, piezaActual.getTipo());
            piezaActual.setColumna(piezaActual.getColumna() - 1);
        }
        //Cambia a reposo. pero esto hay que refactorizarlo por el amor de dios
        gestorEstado.setEstado(gestorEstado.REPOSO);
    }

    private void estadoGestorSinPieza() {
        Pieza pieza = gestorPiezas.getPiezaActual();
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
        tablero.setImagenPiezaSiguiente(gestorPiezas.getImagenPiezaSiguiente());
        gestorEstado.setEstado(GestorEstado.REPOSO);
    }

    public Texture getTexturaPieza(int tipo){
        return gestorPiezas.getTexturaBloque(tipo);
    }
    
    public int getPuntuacion(){
        return this.puntuacion;
    }
    
    public void setPuntuacion(int i) {
        puntuacion+=i;
    }
}
