package com.mygdx.ttrispo.Pantalla;
import com.badlogic.gdx.Gdx;
import com.mygdx.ttrispo.MyGdxGame;

public class PantallaAjustes extends PantallaBase{

    public PantallaAjustes (final MyGdxGame game) {
        super(game);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f); //azul

    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
