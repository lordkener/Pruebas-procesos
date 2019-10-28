package com.mygdx.ttrispo.Gestores;

//import sun.security.util.DerEncoder;

import com.mygdx.ttrispo.Pantalla.Partida;

public class GestorEstado {
    public static final int SINPIEZA = 2; //Valor del Gestor de Estado cuando no hay pieza en el tablero
    public static final int REPOSO = 0; //Valor del Gestor de Estado en reposo
    public static final int CAER = 1; //Valor del Gestor de Estado cuando cae una pieza
    public static final int DERECHA = 3; //Valor del Gestor de Estado cuando realizamos un desplaz. a la der.
    public static final int IZQUIERDA = 4; //Valor del Gestor de Estado cuando realizamos un desplaz a la izq.
    public static final int GIRO = 5; //Valor del Gestor de Estado cuando realizamos un estadoGiro
    public static final int BLOQUEAR = 6; //Valor del Gestor de Estado cuando lo bloqueamos

    private final Partida partida;
    private float velocity = 0.3f;
    private float contador = 0;
    private int estado = SINPIEZA; //Estado inicial del Gestor de Estado

    public GestorEstado(Partida partida) {
        this.partida = partida;
    }

    public int getEstado(float delta) {
        if (contador < velocity) {
            contador += delta;
        } else {
            contador = 0;
            estado = CAER;
        }
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
