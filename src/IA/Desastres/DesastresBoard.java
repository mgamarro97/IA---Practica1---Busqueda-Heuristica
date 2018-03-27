package IA.Desastres;

import java.util.ArrayList;
import java.util.HashMap;

public class DesastresBoard {

    // Estructuda de datos que para cada helicoptero me guarde:
    // - Salida
    // - Grupo/s recogido/s
    // - Tiempo

    // Definir la solucion inicial en la creacion del Board con la estructura de datos definida arriba


    private Grupo[] grupos;
    private boolean[] grupos_visitados;
    private Centro[] centros;
    private DesastresHelicoptero[][] helicopteros;      // helicopteros[i][j] i: id coopter, j: id centro

    public DesastresBoard(Grupos gs, Centros cs) {
        int lg = gs.size();
        int lc = cs.size();
        grupos = new Grupo[lg];
        grupos_visitados = new boolean[lg];
        centros = new Centro[lc];

        int i = 0;
        for (Grupo g : gs) {
            grupos[i] = new Grupo(g.getCoordX(), g.getCoordY(), g.getNPersonas(), g.getPrioridad());
            grupos_visitados[i++] = false;
        }
        i = 0;
        for (Centro c : cs) {
            centros[i++] = new Centro(c.getCoordX(), c.getCoordY(), c.getNHelicopteros());
        }

        helicopteros = new DesastresHelicoptero[centros[0].getNHelicopteros()][lc];
        for (int a = 0; a < centros[0].getNHelicopteros(); ++a) {
            for (int b = 0; b < lc; ++b) {
                helicopteros[a][b] = new DesastresHelicoptero();
            }
        }

        int j;
        i = j = 0;
        for (int g = 0; g < lg; ++g) {
            ArrayList<Grupo> resc = new ArrayList<Grupo>();
            resc.add(grupos[i]); // Grupo a rescatar

        }
    }



    public Grupo getGrupo(int i) {
        return new Grupo(grupos[i].getCoordX(), grupos[i].getCoordY(), grupos[i].getNPersonas(), grupos[i].getPrioridad());
    }

    public Centro getCentro(int i) {
        return new Centro(centros[i].getCoordX(), centros[i].getCoordY(), centros[i].getNHelicopteros());
    }

    public double distGrupoGrupo(Grupo a, Grupo b) {
        return Math.sqrt(Math.pow(b.getCoordX() - a.getCoordX(), 2) + Math.pow(b.getCoordY() - a.getCoordY(), 2));
    }

    public double distCentroGrupo(Centro a, Grupo b) {
        return Math.sqrt(Math.pow(b.getCoordX() - a.getCoordX(), 2) + Math.pow(b.getCoordY() - a.getCoordY(), 2));
    }

    public double calculoTiempoViaje(Centro a, Grupo b) {
        return distCentroGrupo(a, b) / 100;
    }

    public double calculoTiempoViaje(Grupo a, Grupo b) {
        return distGrupoGrupo(a, b) / 100;
    }

    public double calculaTiempoViaje(HashMap<Integer, Grupo[]> viajes) {
        double tiempo = 10 * (viajes.size() - 1);
        for (int i = 0; i < viajes.size(); ++i) {
            Grupo[] trip = viajes.get(i);
            for (int j = 0; j < 3; ++j) {
                if (trip[j] != null) {
                    tiempo += trip[j].getNPersonas() * (3 - trip[j].getPrioridad());
                }
            }
        }
        return tiempo;
    }
}

