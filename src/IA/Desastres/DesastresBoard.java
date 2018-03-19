package IA.Desastres;

public class DesastresBoard {

    // Estructuda de datos que para cada helicoptero me guarde:
    // - Salida
    // - Grupo/s recogido/s
    // - Tiempo

    // Definir la solucion inicial en la creacion del Board con la estructura de datos definida arriba


    private Grupo[] grupos;
    private boolean[] grupos_visitados;
    private Centro[] centros;
    private DesastresHelicoptero[][] helicopteros;

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
    }

    public Grupo getGrupo(int i) {
        return new Grupo(grupos[i].getCoordX(), grupos[i].getCoordY(), grupos[i].getNPersonas(), grupos[i].getPrioridad());
    }

    public Centro getCentro(int i) {
        return new Centro(centros[i].getCoordX(), centros[i].getCoordY(), centros[i].getNHelicopteros());
    }

    public double distGG(Grupo a, Grupo b) {
        return Math.sqrt(Math.pow(b.getCoordX() - a.getCoordX(), 2) + Math.pow(b.getCoordY() - a.getCoordY(), 2));
    }

    public double distCG(Centro a, Grupo b) {
        return Math.sqrt(Math.pow(b.getCoordX() - a.getCoordX(), 2) + Math.pow(b.getCoordY() - a.getCoordY(), 2));
    }
}

