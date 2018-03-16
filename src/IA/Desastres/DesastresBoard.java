package IA.Desastres;

public class DesastresBoard {

    private Grupo[] grupos;
    private Centro[] centros;
    private Helicoptero[][] coopters;

    public DesastresBoard(Grupos gs, Centros cs) {
        int lg = gs.size();
        int lc = cs.size();
        grupos = new Grupo[lg];
        centros = new Centro[lc];

        int i = 0;
        for (Grupo g : gs) {
            grupos[i++] = new Grupo(g.getCoordX(), g.getCoordY(), g.getNPersonas(), g.getPrioridad());
        }
        i = 0;
        for (Centro c : cs) {
            centros[i++] = new Centro(c.getCoordX(), c.getCoordY(), c.getNHelicopteros());
        }

        coopters = new Helicoptero[centros[0].getNHelicopteros()][lc];
        for (i = 0; i < lc; ++i) {
            for (int j = 0; j < centros[i].getNHelicopteros(); ++j) {
                coopters[j][i] = new Helicoptero(centros[i].getCoordX(), centros[i].getCoordY());
            }
        }
    }

    public Grupo getGrupo(int i) {
        return new Grupo(grupos[i].getCoordX(), grupos[i].getCoordY(), grupos[i].getNPersonas(), grupos[i].getPrioridad());
    }

    public Centro getCentro(int i) {
        return new Centro(centros[i].getCoordX(), centros[i].getCoordY(), centros[i].getNHelicopteros());
    }
}

