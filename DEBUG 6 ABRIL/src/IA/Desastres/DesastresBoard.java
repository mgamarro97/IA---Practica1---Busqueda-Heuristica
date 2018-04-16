package IA.Desastres;

public class DesastresBoard {

    private static Centro[] centros;
    private static Grupo[] grupos;
    // rescates[i] es el rescate del grupo grupos[i]
    // first: id helicoptero h (#centros * #helicopteros/centro) + nHelicoptero; second: viaje en el que ha sido rescatado
    private PairInt[] rescates;
    private int[][] viajesHeli;                    // viajesHeli[i][j] = n; n-> num viajes hecho por el helicoptero j del centro i
    private static boolean HEURISTICO;             // true: heuristico chulo; false: heuristico ez
    public double heuristicValue;
    private double time;
    private static int conjOp = 0;

    public DesastresBoard(Grupos gs, Centros cs, boolean heuristico, int initSol, int conjOp){
        this.conjOp = conjOp;
        int lc = cs.size();
        int lg = gs.size();
        centros = new Centro[lc];
        grupos = new Grupo[lg];
        rescates = new PairInt[lg];
        HEURISTICO = heuristico;
        time = 0;

        int i = 0;
        for (Centro c : cs) {
            centros[i++] = new Centro(c.getCoordX(), c.getCoordY(), c.getNHelicopteros());
        }

        i = 0;
        for (Grupo g : gs) {
            grupos[i++] = new Grupo(g.getCoordX(), g.getCoordY(), g.getNPersonas(), g.getPrioridad());
        }

        int h = centros[0].getNHelicopteros();
        viajesHeli = new int[lc][h];
        for (i = 0; i < lc; ++i) {
            for (int j = 0; j < h; ++j) {
                viajesHeli[i][j] = 0;
            }
        }

        switch (initSol) {
            case 0:
                solucionBasica();
                // Un rescate por viaje
                break;
            case 1:
                // Intentando llenar todos los viajes de todos los helicopteros
                solucionLlena();
                break;
            default:
                solucionBasica();
                break;
        }
    }

    public DesastresBoard(DesastresBoard board) {
        int n = board.rescates.length;
        rescates = new PairInt[n];
        for (int i = 0; i < n; ++i) {
            rescates[i] = new PairInt(board.rescates[i]);
        }
    }

    public int getNumRescates() {
        return rescates.length;
    }

    public int getNumHelicopteros(){
        return centros[0].getNHelicopteros()*centros.length;
    }

    public int getNumViajes(int h){
        int v = 0;
        for (int i = 0; i < rescates.length; ++i){
            if (rescates[i].first == h && rescates[i].second > v) {
                v = rescates[i].second;
            }
        }
        return v;
    }

    public Grupo getGrupo(int i) {
        return new Grupo(grupos[i].getCoordX(), grupos[i].getCoordY(), grupos[i].getNPersonas(), grupos[i].getPrioridad());
    }

    public double getTime(){
        return time;
    }
    public int getOperadores(){
        return conjOp;
    }

    public double getHeuristicValue() {
        if (HEURISTICO) {
            calculaHeuristic2();
        } else {
            calculaHeuristic();
        }
        return heuristicValue;
    }

    public double distAB(int ax, int ay, int bx, int by) {
        return Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - ay, 2));
    }

    public double calculoTiempoMovimiento(int ax, int ay, int bx, int by) {
        return (distAB(ax, ay, bx, by) / 100)*60;
    }

    public double calculoTiempoRescate(Grupo g) {
        return (g.getNPersonas()) * (3 - g.getPrioridad());
    }

    public double calculoTiempoRescate(Grupo g, int v) {
        return (g.getNPersonas()) * Math.pow((3 - g.getPrioridad()),v);
    }

    public int[] getGruposRescatados(int helicoptero, int viaje) {
        int[] sol = new int[3];
        sol[0] = -1; sol[1] = -1; sol[2] = -1;
        int k = 0;
        for (int i = 0; i < rescates.length; ++i) {
            if (rescates[i].first == helicoptero && rescates[i].second == viaje) {
                sol[k++] = i;
            }
        }
        return sol;
    }

    public void swapV(int h1, int v1, int h2, int v2) {
        int viaje1[] = getGruposRescatados(h1,v1);
        int viaje2[] = getGruposRescatados(h2,v2);
        for (int i = 0; i < 3; i++){
            if(viaje2[i] != -1) {
                rescates[viaje2[i]].setFirst(h1);
                rescates[viaje2[i]].setSecond(v1);
            }
        }
        for (int i = 0; i < 3; i++){
            if(viaje1[i] != -1) {
                rescates[viaje1[i]].setFirst(h2);
                rescates[viaje1[i]].setSecond(v2);
            }
        }
    }

    public boolean setR(int i, int j){
        PairInt grupoI = new PairInt(rescates[i]);
        PairInt grupoJ = new PairInt(rescates[j]);
        if(grupoI.first == grupoJ.first && grupoI.second == grupoJ.second)return false;
        int viaje1[] = getGruposRescatados(grupoI.first,grupoI.second);
        int viaje2[] = getGruposRescatados(grupoJ.first,grupoJ.second);
        if (viaje2[2] != -1)return false;
        if(grupoI.second == 1  && viaje1[1] == -1)return false;

        rescates[i].setFirst(grupoJ.first);
        rescates[i].setSecond(grupoJ.second);
        if(!sucesorValido(grupoJ))return false;
        if(viaje1[1] == -1)decrementaViaje(grupoI.first,grupoI.second);
        return true;
    }

    public boolean swapR(int i, int j){
        PairInt grupoI = new PairInt(rescates[i]);
        PairInt grupoJ = new PairInt(rescates[j]);
        if(grupoI.first == grupoJ.first && grupoI.second == grupoJ.second)return false;
        rescates[i].setFirst(grupoJ.first);
        rescates[i].setSecond(grupoJ.second);
        rescates[j].setFirst(grupoI.first);
        rescates[j].setSecond(grupoI.second);
        if(!sucesorValido(grupoJ))return false;
        if(!sucesorValido(grupoI))return false;
        return true;
    }

    public boolean setV(int h1, int v1, int h2, int v2){
        if(v2 == 1)return false;
        int viaje2[] = getGruposRescatados(h2,v2);
        for (int i = 0; i < 3; i++){
            if (viaje2[i] != -1) {
                rescates[viaje2[i]].setFirst(h1);
                rescates[viaje2[i]].setSecond(v1 + 1);
            }
        }
        decrementaViaje(h2,v2);
        return true;
    }

    private boolean sucesorValido(PairInt grupo){
        int viaje[] = getGruposRescatados(grupo.first, grupo.second);
        int personas = 0;
        for (int i = 0; i < 3; i++){
            if(viaje[i] != -1) {
                personas += grupos[viaje[i]].getNPersonas();
                if (personas > 15) return false;
            }
        }
        return true;
    }

    private void decrementaViaje(int h, int vBorrado){
        for(int i = 0; i < rescates.length; i++){
            if(rescates[i].first == h && rescates[i].second > vBorrado) rescates[i].second--;
        }
    }

    public void calculaHeuristic() {
        heuristicValue = 0;
        int heliscenter = centros[0].getNHelicopteros(); //helicopteros por centro
        int helis = centros.length*heliscenter; //helicopteros totales
        Centro actual = null;
        for (int h = 0; h < helis; ++h){
            if (h%heliscenter == 0) {
                actual = centros[h/heliscenter]; //centro al que pertenece el helicoptero h
            }
            int[] rescued;
            rescued = getGruposRescatados(h, 1);  //array de grupos rescatados por el helicoptero h en el viaje i
            boolean next = rescued[0] != -1;  //si rescued[0] es -1 entonces no existe el viaje
            for (int i = 2; next; i++) {
                Grupo gact = getGrupo(rescued[0]);
                //distancia entre el centro y el primer grupo
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(),actual.getCoordY(),gact.getCoordX(),gact.getCoordY());
                //tiempo que tarda en rescatar a las personas del grupo
                heuristicValue += calculoTiempoRescate(gact);

                boolean end = false;
                for (int j = 1; j < 3 && !end; j++) {
                    end = (rescued[j] == -1); //mirar si hay un grupo en la posicion j
                    if (!end) {
                        Grupo aux = getGrupo(rescued[j]);
                        //tiempo en funcion de la prioridad y el número de personas a rescatar
                        heuristicValue += calculoTiempoRescate(aux);
                        //tiempo entre los grupos a rescatar en el viaje
                        heuristicValue += calculoTiempoMovimiento(gact.getCoordX(), gact.getCoordY(), aux.getCoordX(), aux.getCoordY());
                        gact = getGrupo(rescued[j]);
                    }
                }

                //tiempo entre el centro del helicoptero y el ultimo grupo a rescatar
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(),actual.getCoordY(),gact.getCoordX(),gact.getCoordY());
                rescued = getGruposRescatados(h, i);
                next = rescued[0] != -1;
                if (next){ heuristicValue += 10; } //tiempo de espera para volver a hacer un viaje
            }
        }
        time = heuristicValue;
    }

    public void calculaHeuristic2() {

        heuristicValue = 0;
        time = 0;
        int heliscenter = centros[0].getNHelicopteros(); //helicopteros por centro
        int helis = centros.length*heliscenter; //helicopteros totales
        Centro actual = null;

        for (int h = 0; h < helis; ++h) {
            if (h % heliscenter == 0) {
                actual = centros[h / heliscenter]; //centro al que pertenece el helicoptero h
            }
            int[] rescued;
            rescued = getGruposRescatados(h, 1);  //array de grupos rescatados por el helicoptero h en el viaje i
            boolean next = rescued[0] != -1;  //si rescued[0] es -1 entonces no existe el viaje

            for (int i = 2; next; i++) {
                Grupo gact = getGrupo(rescued[0]);
                //distancia entre el centro y el primer grupo
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                time += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                //tiempo en rescatar a las personas del grupo actual
                heuristicValue += calculoTiempoRescate(gact,i);
                time += calculoTiempoRescate(gact);

                boolean end = false;
                for (int j = 1; j < 3 && !end; j++) {
                    end = (rescued[j] == -1);
                    if (!end) {
                        Grupo aux = getGrupo(rescued[j]);
                        //tiempo en funcion de la prioridad y el número de personas a rescatar
                        heuristicValue += calculoTiempoRescate(aux,i);
                        time += calculoTiempoRescate(aux);
                        //tiempo entre los grupos a rescatar en el viaje
                        heuristicValue += calculoTiempoMovimiento(gact.getCoordX(), gact.getCoordY(), aux.getCoordX(), aux.getCoordY());
                        time += calculoTiempoMovimiento(gact.getCoordX(), gact.getCoordY(), aux.getCoordX(), aux.getCoordY());
                        gact = getGrupo(rescued[j]);
                    }
                }
                //tiempo entre el centro del helicoptero y el ultimo grupo a rescatar
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                time += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                rescued = getGruposRescatados(h, i);
                next = rescued[0] != -1;
                if (next){
                    heuristicValue += 10;
                    time += 10;
                } //tiempo de espera para volver a hacer un viaje
            }
        }
    }
    @Override
    public String toString() {
        String s = "";
        int h = getNumHelicopteros();
        int[] viajesHeli = new int[h];

        for (int i = 0; i < rescates.length; ++i) {
            if (viajesHeli[rescates[i].first] < rescates[i].second) viajesHeli[rescates[i].first] = rescates[i].second;
        }

        for (int i = 0; i < h; ++i) {
            s += "h" + i + ":";
            for (int j = 1; j < viajesHeli[i]; ++j) {
                s += "\tv" + j + ":";
                int [] g = getGruposRescatados(i, j);
                for (int k = 0; k < 3; ++k) {
                    if (g[k] == -1) break;
                    s += "  grupo " + g[k];
                }
                s += "\n";
            }
            s += "\n";
        }
        return s;
    }

    private void solucionBasica() {
        int nHelicopteros = centros[0].getNHelicopteros();
        int viaje = 1;
        int h = 0;
        int c = 0;
        int lc = centros.length;
        int lg = grupos.length;
        for (int i = 0; i < lg; ++i) {
            if (h == nHelicopteros) {
                if (c == lc-1) {
                    ++viaje;
                    c = 0;
                }
                else {
                    ++c;
                }
                h = 0;
            }
            viajesHeli[c][h] = viaje;
            rescates[i] = new PairInt(c * nHelicopteros + h, viaje);
            h++;
        }
    }

    private void solucionLlena() {
        int lg = grupos.length;
        int g = 0;
        int np = 0;
        int h = 0;
        int viaje = 1;
        int c = 0;
        int nHelicopteros = centros[0].getNHelicopteros();
        int lc = centros.length;
        for (int i = 0; i < lg; ++i) {
            ++g;
            np += grupos[i].getNPersonas();
            if (g == 4) {
                g = 1;
                np = grupos[i].getNPersonas();
                ++h;
            } else if (np > 15) {
                g = 1;
                np = grupos[i].getNPersonas();
                ++h;
            }

            if (h == nHelicopteros) {
                if (c == lc - 1) {
                    ++viaje;
                    c = 0;
                } else {
                    ++c;
                }
                h = 0;
            }
            viajesHeli[c][h] = viaje;
            rescates[i] = new PairInt(c * nHelicopteros + h, viaje);
        }
    }
}
