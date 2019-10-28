package com.mygdx.ttrispo.Gestores;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public  class GestorRecursos {
    private static AssetManager manager = new AssetManager();
    private static int contador = 0;
    public static void cargarImagenes() {
        manager.load("T.jpg", Texture.class);
        manager.load("S.jpg", Texture.class);
        manager.load("Z.jpg", Texture.class);
        manager.load("J.jpg", Texture.class);
        manager.load("L.jpg", Texture.class);
        manager.load("I.jpg", Texture.class);
        manager.load("O.jpg", Texture.class);
        manager.load("TCompleta.png", Texture.class);
        manager.load("SCompleta.png", Texture.class);
        manager.load("ZCompleta.png", Texture.class);
        manager.load("JCompleta.png", Texture.class);
        manager.load("LCompleta.png", Texture.class);
        manager.load("ICompleta.png", Texture.class);
        manager.load("OCompleta.png", Texture.class);
        manager.load("background.jpeg", Texture.class);
        manager.load("GameOver.jpeg", Texture.class);
        while (!manager.update()) {
            //System.out.println("Cargando...");
            contador++;
        }
    }

    public static Texture get(String s) {
        return manager.get(s);
    }

}
