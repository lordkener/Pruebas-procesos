package com.mygdx.ttrispo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.ttrispo.Gestores.GestorEstado;

public class Procesador extends InputAdapter {
    GestorEstado gestorEstado;
    public Procesador(GestorEstado g){
        gestorEstado=g;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int altoMax = Gdx.graphics.getHeight();
        int anchoMax = Gdx.graphics.getWidth();
        //Demomento lo vamos a resolver por sectores.
        /*7
        De 0 a max/3 iz de max/3 a 2max/3 arriba posicionY abajo posicionY el resto derecha
         */
        if((screenY>(0.78*altoMax))&& (screenY <altoMax)){
            if(screenX<(anchoMax/3)){
                gestorEstado.setEstado(gestorEstado.IZQUIERDA);
            }
            else if ((screenX>(anchoMax/3))&& (screenX<(anchoMax*2/3))){
                //no hacia bien el calculo dentro del if.
                double zona1=0.89*altoMax;
                double zona0= 0.78*altoMax;
                if ((screenY>zona0)&&(screenY<zona1)){
                    gestorEstado.setEstado(gestorEstado.GIRO);
                }else {
                    gestorEstado.setEstado(gestorEstado.CAER);
                }
                System.out.println("Zona centro");
            }else{
                gestorEstado.setEstado(gestorEstado.DERECHA);
            }

        }
        return true;
    }

}
