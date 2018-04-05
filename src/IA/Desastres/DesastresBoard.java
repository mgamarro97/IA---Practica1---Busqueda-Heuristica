package IA.Desastres;
public class DesastresBoard {

    // Definir la solucion inicial en la creacion del Board con la estructura de datos definida arriba

    private static Centro[] centros;
    private static Grupo[] grupos;
    // rescates[i] es el rescate del grupo grupos[i]
    // first: id helicoptero h (#centros * #helicopteros/centro) + nHelicoptero; second: viaje en el que ha sido rescatado
    private PairInt[] rescates;
    private static boolean HEURISTICO;             // true: heuristico chulo; false: heuristico ez

    public double heuristicValue;

    public DesastresBoard(Grupos gs, Centros cs, boolean heuristico) {
        int lc = cs.size();
        int lg = gs.size();
        centros = new Centro[lc];
        grupos = new Grupo[lg];
        rescates = new PairInt[lc];
        HEURISTICO = heuristico;

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

    public Centro getCentro(int i) {
        return new Centro(centros[i].getCoordX(), centros[i].getCoordY(), centros[i].getNHelicopteros());
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
        return distAB(ax, ay, bx, by) / 100;
    }

    public double calculoTiempoRescate(Grupo g) {
        return (g.getNPersonas()) * (3 - g.getPrioridad());
    }

    public double calculoTiempoRescate(Grupo g, int v) {
        return (g.getNPersonas()) * (3 - g.getPrioridad()) ^ v;
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
            rescates[viaje2[i]].setFirst(h1);
            rescates[viaje2[i]].setSecond(v1);
        }
        for (int i = 0; i < 3; i++){
            rescates[viaje1[i]].setFirst(h2);
            rescates[viaje1[i]].setSecond(v2);
        }
    }

    public boolean swapR(int h1, int v1, int h2, int v2,int grupo1, int grupo2) {
        if(grupo1 != -1) {
            rescates[grupo1].setFirst(h2);
            rescates[grupo1].setSecond(v2);
        }
        else if(grupo2 != -1) {
            rescates[grupo2].setFirst(h1);
            rescates[grupo2].setSecond(v1);
        }
        if(sucesorValido(rescates[grupo1], rescates[grupo2]))return true;
        return false;
    }

    public boolean setV(int h1, int v1, int h2, int v2){
        if(v2 == 1)return false;
        int viaje2[] = getGruposRescatados(h2,v2);
        for (int i = 0; i < 3; i++){
            rescates[viaje2[i]].setFirst(h1);
            rescates[viaje2[i]].setSecond(v1+1);
        }
        decrementaViaje(h2,v2);
        return true;
    }

    private boolean sucesorValido(PairInt grupos1, PairInt grupos2){
        int viaje1[] = getGruposRescatados(grupos1.first, grupos1.second);
        int personas = 0;
        for (int i = 0; i < 3; i++){
            personas += grupos[viaje1[i]].getNPersonas();
            if (personas>15) return false;
        }
        personas = 0;
        int viaje2[] = getGruposRescatados(grupos2.first, grupos2.second);
        for (int i = 0; i < 3; i++){
            personas += grupos[viaje2[i]].getNPersonas();
            if (personas>15) return false;
        }
        return true;
    }

    private void decrementaViaje(int h, int vBorrado){
        for(int i = 0; i < rescates.length; i++){
            if(rescates[i].first == h && rescates[i].second < vBorrado) rescates[i].second--;
        }
    }

    public void calculaHeuristic() {
        /*
        heuristicValue = 0;
        int helisCenter = centros[0].getNHelicopteros();
        boolean[] yaRescatados = new boolean[rescates.length];
        for (int i = 0; i < yaRescatados.length; ++i) { yaRescatados[i] = false; }

        for (int i = 0; i < rescates.length; ++i) {
            if (!yaRescatados[i]) {
                int[] rAct = getGruposRescatados(rescates[i].first, rescates[i].second);
                for (int j = 0; j < rAct.length; ++j) {
                    yaRescatados[rAct[j]] = true;
                }
                Centro act = centros[centros.length / helisCenter];
                int k = 0;
                heuristicValue += calculoTiempoMovimiento(act.getCoordX(), act.getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
                heuristicValue += calculoTiempoRescate(grupos[rAct[k]]);
                for (k = 1; k < rAct.length; ++k) {
                    heuristicValue += calculoTiempoMovimiento(grupos[rAct[k-1]].getCoordX(), grupos[rAct[k-1]].getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
                    heuristicValue += calculoTiempoRescate(grupos[rAct[k]]);
                }
                heuristicValue += calculoTiempoMovimiento(act.getCoordX(), act.getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
            }
        }
        */

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
                    end = rescued[j] != -1; //mirar si hay un grupo en la posicion j
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
            }
        }
    }

    public void calculaHeuristic2() {
        /*
        heuristicValue = 0;
        int helisCenter = centros[0].getNHelicopteros();
        boolean[] yaRescatados = new boolean[rescates.length];
        for (int i = 0; i < yaRescatados.length; ++i) { yaRescatados[i] = false; }

        for (int i = 0; i < rescates.length; ++i) {
            if (!yaRescatados[i]) {
                int[] rAct = getGruposRescatados(rescates[i].first, rescates[i].second);
                for (int j = 0; j < rAct.length; ++j) {
                    yaRescatados[rAct[j]] = true;
                }
                Centro act = centros[centros.length / helisCenter];
                int k = 0;
                heuristicValue += calculoTiempoMovimiento(act.getCoordX(), act.getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
                heuristicValue += calculoTiempoRescate(grupos[rAct[k]], rescates[i].second);
                for (k = 1; k < rAct.length; ++k) {
                    heuristicValue += calculoTiempoMovimiento(grupos[rAct[k-1]].getCoordX(), grupos[rAct[k-1]].getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
                    heuristicValue += calculoTiempoRescate(grupos[rAct[k]], rescates[i].second);
                }
                heuristicValue += calculoTiempoMovimiento(act.getCoordX(), act.getCoordY(), grupos[rAct[k]].getCoordX(), grupos[rAct[k]].getCoordY());
            }
        }
        */

        heuristicValue = 0;
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

            for (int i = 2; !next; i++) {
                Grupo gact = getGrupo(rescued[0]);
                //distancia entre el centro y el primer grupo
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                //tiempo en rescatar a las personas del grupo actual
                heuristicValue += calculoTiempoRescate(gact,i);
                
                boolean end = false;
                for (int j = 1; j < 3 && !end; j++) {
                    end = rescued[j] != -1;
                    if (!end) {
                        Grupo aux = getGrupo(rescued[j]);
                        //tiempo en funcion de la prioridad y el número de personas a rescatar
                        heuristicValue += calculoTiempoRescate(aux,i);
                        //tiempo entre los grupos a rescatar en el viaje
                        heuristicValue += calculoTiempoMovimiento(gact.getCoordX(), gact.getCoordY(), aux.getCoordX(), aux.getCoordY());
                        gact = getGrupo(rescued[j]);
                    }
                }
                //tiempo entre el centro del helicoptero y el ultimo grupo a rescatar
                heuristicValue += calculoTiempoMovimiento(actual.getCoordX(), actual.getCoordY(), gact.getCoordX(), gact.getCoordY());
                rescued = getGruposRescatados(h, i);
                next = rescued[0] != -1;
            }
        }
    }
}
