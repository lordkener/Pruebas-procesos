package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProgresoPartida extends Actor {
    private BitmapFont font;
    private Partida partida;

    public ProgresoPartida(Partida partida){
        this.partida = partida;
        font = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.getData().setScale(8);
        font.draw(batch, ""+partida.getPuntuacion(),
                Gdx.graphics.getWidth()/5, 93*Gdx.graphics.getHeight()/100);
    }
}
