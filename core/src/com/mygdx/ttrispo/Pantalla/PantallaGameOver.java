package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;


import javax.swing.plaf.nimbus.State;
import javax.xml.soap.Text;

public class PantallaGameOver extends PantallaBase {
    private State state;
    private Skin skin;
    private TextButton retry;
    private TextButton home;
    private Texture fondoGameOver;

    public PantallaGameOver(final MyGdxGame game){
        super(game);
        fondoGameOver = GestorRecursos.get("GameOver.jpeg");
        //stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        retry = new TextButton("Retry", skin);
        home = new TextButton("Home", skin);

        retry.setSize(300,100);
        retry.setPosition(Gdx.graphics.getWidth()/2.65f, Gdx.graphics.getHeight()/2);

        home.setSize(300,100);
        home.setPosition(Gdx.graphics.getWidth() / 2.65f, Gdx.graphics.getHeight() / 3);

        stage.addActor(retry);
        stage.addActor(home);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Partida(game));
            }
        });
        home.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.pantallaInicio);
            }
        });
    }


    @Override
    public void show() {
        super.show();
        //Gdx.input.setInputProcessor(stage); //procesa todos los eventos de los actores: el botón AKA: sale rojo cuando pulsas!!
    }

    @Override
    public void hide() {
        //super.hide();
        //Gdx.input.setInputProcessor(null); //para dejar de usar este stage cuando cambiemos de pantalla
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fondoGameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        Gdx.gl.glClearColor(0.4f,0.2f,0.7f,0.7f); //morada
        stage.draw(); // Pintar los actores los botones por encima del background
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
