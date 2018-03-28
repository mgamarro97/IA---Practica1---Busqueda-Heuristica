package IA.Desastres;

import java.util.ArrayList;
import java.util.HashMap;

public class DesastresBoard {

    // Estructuda de datos que para cada helicoptero me guarde:
    // - Salida
    // - Grupo/s recogido/s
    // - Tiempo

    // Definir la solucion inicial en la creacion del Board con la estructura de datos definida arriba

    private static Centro[] centros;
    private static Grupo[] grupos;
    // rescates[i] es el rescate del grupo grupos[i]
    // first: id helicoptero h (0 <= h < grupos.size()); second: viaje en el que ha sido rescatado
    private PairInt[] rescates;

    public DesastresBoard(Grupos gs, Centros cs) {
        int lc = cs.size();
        int lg = gs.size();
        centros = new Centro[lc];
        grupos = new Grupo[lg];
        rescates = new PairInt[lc];

        int i = 0;
        for (Centro c : cs) {
            centros[i++] = new Centro(c.getCoordX(), c.getCoordY(), c.getNHelicopteros());
        }

        i = 0;
        for (Grupo g : gs) {
            grupos[i++] = new Grupo(g.getCoordX(), g.getCoordY(), g.getNPersonas(), g.getPrioridad());
        }

        int nHelicopteros = centros[0].getNHelicopteros();
        int viaje = 1;
        int h = 0;
        int c = 0;
        for (i = 0; i < lg; ++i) {
            if (h == nHelicopteros) {
                if (c == lc) {
                    ++viaje;
                    c = 0;
                }
                else {
                    ++c;
                }
                h = 0;
            }
            rescates[i] = new PairInt(c * nHelicopteros + h, viaje);
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

