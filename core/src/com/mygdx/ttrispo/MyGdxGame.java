package com.mygdx.ttrispo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.ttrispo.BaseDeDatos.FirebaseHelper;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.Pantalla.PantallaGameOver;
import com.mygdx.ttrispo.Pantalla.PantallaInicio;
import com.mygdx.ttrispo.Pantalla.Partida;

public class MyGdxGame extends Game {

    public PantallaInicio pantallaInicio;
    //public Partida partida;
    public PantallaGameOver pantallaGameOver;
    //public FirebaseHelper firebaseHelper;

    @Override
    public void create() {
        GestorRecursos.cargarImagenes();
        pantallaInicio = new PantallaInicio(this);
        //partida = new Partida(this);
        //firebaseHelper.prueba();
        pantallaGameOver = new PantallaGameOver(this);
        this.setScreen(pantallaInicio);
    }

    @Override
    public void dispose() {
//        batch.dispose();
//        img.dispose();
    }




}
