package Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class DesastresSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        ArrayList res = new ArrayList();
        DesastresBoard board = (DesastresBoard)o;
        swapRescates(board, res);
        System.out.println(res.size());
        swapViajes(board, res);
        System.out.println(res.size());
        setViaje(board,res);
        System.out.println(res.size());
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int borrar1 = 0, borrar2 = 0;
        int nHelicopteros = b.getNumHelicopteros();
        int cont = 0;
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        int rescate1[] = b.getGruposRescatados(i,j);
                        for(int r = 0; r < 3;r++){
                            int rescate2[] = b.getGruposRescatados(k,l);
                            for (int s = 0; s < 3;s++){
                                if(rescate1[r] == -1 && rescate2[s] == -1) break;
                                aux =  new DesastresBoard(b);
                                //System.out.println(i + " " +j+" "+r+" "+k+" "+l+" "+s);
                                if(aux.swapR(i, j, k, l, rescate1[r], rescate2[s], borrar1, borrar2)) { ///MODIFICAR MATRIZ DE VIAJES
                                    cont++;
                                    String S = "Los helicopteros " + i + " y " + k + " cambian grupos número:" + r +
                                            " y:" + s + " de los viajes " + (j) + " y " + (l);
                                    res.add(new Successor(S, aux));
                                }
                                borrar1 = 0;
                                borrar2 = 0;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(cont + " " + nHelicopteros);
    }

    private void swapViajes(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        int cont = 0;
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        aux =  new DesastresBoard(b);
                        cont++;
                        aux.swapV(i, j, k, l);
                        String S = "Swapear viajes " + j + " y " + l + " de los helicopteros " + i + " y " + k;
                        res.add(new Successor(S, aux));
                        //System.out.println(i + " " +j+" "+k+" "+l);
                    }
                }
            }
        }
        System.out.println(cont + " " + nHelicopteros);
    }

    private void setViaje(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        int cont = 0;
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = 0; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        if(i != k) {
                            aux =  new DesastresBoard(b);
                            if(aux.setV(i, viajesHelicoptero1, k, l)) {
                                cont++;
                                String S = "Set viaje " + l + " a helicoptero " + i;
                                res.add(new Successor(S, aux));
                                //System.out.println(i + " " + j + " " + k + " " + l);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(cont + " " + nHelicopteros);
    }
}