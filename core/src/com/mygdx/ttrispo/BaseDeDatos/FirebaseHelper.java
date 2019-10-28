package com.mygdx.ttrispo.BaseDeDatos;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

import de.tomgrill.gdxfirebase.core.FirebaseConfiguration;
import de.tomgrill.gdxfirebase.core.FirebaseFeatures;
import de.tomgrill.gdxfirebase.core.FirebaseLoader;
import de.tomgrill.gdxfirebase.core.GDXFirebase;
import de.tomgrill.gdxfirebase.core.database.DataSnapshot;
import de.tomgrill.gdxfirebase.core.database.DatabaseError;
import de.tomgrill.gdxfirebase.core.database.DatabaseReference;
import de.tomgrill.gdxfirebase.core.database.ValueEventListener;

public class FirebaseHelper {

    private FirebaseConfiguration config;
    private DatabaseReference databaseReference;
    private long maxId = 0;
    private ArrayList<Jugador> listaRanking = new ArrayList<>();

    public FirebaseHelper(){
        listaRanking.add(null);//POSICION 0 DEL ARRAY (no me interesa)
        //configurar base de datos
        config = new FirebaseConfiguration();
        config.databaseUrl = "https://ttrispo-40d29.firebaseio.com";
        config.serviceAccount = Gdx.files.absolute("ttrispo-40d29-firebase-adminsdk-duoa1-62d89da538.json");

        //cargar base de datos en tiempo real
        FirebaseLoader.load(config,
                FirebaseFeatures.REALTIME_DATABASE,
                FirebaseFeatures.AUTHENTICATION
        );
        rellenarArrayDeRanking(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Jugador> lista) {
                insertarPuntuacionEnRanking("katarina", 27830452);
            }
        });
    }

    public void insertarPuntuacionEnRanking(final String nombre, final long puntos) {
        int contador = 1;
        boolean esMayorQueAlguno = false;
        System.out.println("TAMANIO DEL ARRAY 2: " + listaRanking.size());
        while(!esMayorQueAlguno && contador<=listaRanking.size()){
            if(listaRanking.get(contador).getPuntuacion()<puntos){
                esMayorQueAlguno = true;
            }
            contador++;
        }
        if (esMayorQueAlguno){

            databaseReference = GDXFirebase.FirebaseDatabase().getReference("bbdd");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long indice;
                    indice = (long)dataSnapshot.child(String.valueOf(0)).getValue();
                    databaseReference.child(String.valueOf(indice+1)).child("Nombre").setValue(nombre);
                    databaseReference.child(String.valueOf(indice+1)).child("Puntuacion").setValue(puntos);
                    databaseReference.child(String.valueOf(0)).setValue(indice+1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    /** rellena el array del ranking, maximo 10
     **/
    private void rellenarArrayDeRanking(final FirebaseCallback firebaseCallback){
        DatabaseReference dr = GDXFirebase.FirebaseDatabase().getReference("bbdd");

        //final Query query = dr.child(FirebaseConstants.TARGET);
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 1;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String clave = snapshot.getKey();
                        if (Integer.parseInt(clave) != 0) { // El 0 esta reservado para que se utilize como contador
                            //System.out.println(snapshot.getKey());
                            //System.out.println("Ha entrado con i: " + i);
                            Jugador jugador = new Jugador(Integer.parseInt(clave), snapshot.child("Nombre").getValue().toString(), (long) snapshot.child("Puntuacion").getValue());
                            listaRanking.add(jugador);
                            if (i == 10) {
                                break;
                            }
                            i++;
                        }
                        //System.out.println(snapshot.getKey());
                    }
                    System.out.println("TAMANIO DEL ARRAY 1: " + listaRanking.size());
                    firebaseCallback.onCallback(listaRanking);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /** LO QUE HAY QUE HACER ES EL METODO DE BURBUJA para reordenar el array de mayor a menor(esto es temporal)
     */
    private void reordenarArray(final long puntos){
        int i, j;
        int auxID;
        String auxNombre;
        long auxPuntuacion;

        for (i = 0; i <= 10 - 1; i++) {
            for (j = 1; j <= 10 - i - 1; j++) {
                if (listaRanking.get(j+1).getPuntuacion() > listaRanking.get(j).getPuntuacion()) {
                    auxID = listaRanking.get(j+1).getId();
                    auxNombre = listaRanking.get(j+1).getNombre();
                    auxPuntuacion = listaRanking.get(j+1).getPuntuacion();

                    listaRanking.get(j+1).setId(listaRanking.get(j).getId());
                    listaRanking.get(j+1).setNombre(listaRanking.get(j).getNombre());
                    listaRanking.get(j+1).setPuntuacion(listaRanking.get(j).getPuntuacion());

                    listaRanking.get(j).setId(auxID);
                    listaRanking.get(j).setNombre(auxNombre);
                    listaRanking.get(j).setPuntuacion(auxPuntuacion);
                }
            }
        }
    }

    public void mostrarRanking(){
        for(int i = 1; i<listaRanking.size(); i++) {
            System.out.println("OBJETO "+ i + " puntuacion:" + listaRanking.get(i).getPuntuacion());
        }
    }

    private interface FirebaseCallback{
         void onCallback(ArrayList<Jugador> lista);
    }
}