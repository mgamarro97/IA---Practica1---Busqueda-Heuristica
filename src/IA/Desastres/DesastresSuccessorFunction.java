package IA.Desastres;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class DesastresSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        ArrayList res = new ArrayList();
        DesastresBoard board = (DesastresBoard)o;
        int size = board.getNumRescates();

        swapRescates(board, res, size);
        swapViajes(board, res);
        setViaje(board,res);
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 0; l < viajesHelicoptero2; l++) {

                        int grupo1[] = b.getGruposRescatados(i,j);
                        for(int r = 0; r < 3;r++){
                            int grupo2[] = b.getGruposRescatados(k,l);
                            for (int s = r; s < 3;s++){
                                if(grupo1[r] == -1 && grupo2[s] == -1)break;
                                aux =  new DesastresBoard(b);
                                if(aux.swapR(i, j, k, l, grupo1[r], grupo2[s])) { ///MODIFICAR MATRIZ DE VIAJES
                                    String S = "Los helicopteros " + i + "y " + k + "cambian grupos " + r + "y " + s +
                                            "de los viajes" + j + "y " + l;
                                    res.add(new Successor(S, aux));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void swapViajes(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = i + 1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 0; l < viajesHelicoptero2; l++) {
                        aux =  new DesastresBoard(b);
                        aux.swapV(i, j, k, l); //MODIFICAR MATRIZ DE VIAJES
                        String S = "Swapear viajes " + j + " y " + l + "de los helicopteros " + i + "y " + k;
                        res.add(new Successor(S, aux));
                    }
                }
            }
        }
    }

    private void setViaje(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = i + 1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 0; l < viajesHelicoptero2; l++) {
                        aux =  new DesastresBoard(b);
                        aux.setV(i, j, k, l); //MODIFICAR MATRIZ DE VIAJES
                        String S = "Set viaje " + l + " a helicoptero " + i;
                        res.add(new Successor(S, aux));
                    }
                }
            }
        }
    }
}
