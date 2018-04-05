package IA.Desastres;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class DesastresSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        ArrayList res = new ArrayList();
        DesastresBoard board = (DesastresBoard)o;
        swapRescates(board, res);
        swapViajes(board, res);
        setViaje(board,res);
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int borrar1 = 0, borrar2 = 0;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 0; l < viajesHelicoptero2; l++) {
                        int rescate1[] = b.getGruposRescatados(i,j);
                        for(int r = 0; r < 3;r++){
                            int rescate2[] = b.getGruposRescatados(k,l);
                            for (int s = r; s < 3;s++){
                                if(rescate1[r] == -1 && rescate2[s] == -1)break;
                                aux =  new DesastresBoard(b);
                                if((rescate1[1] == -1 && rescate2[2] != -1) || (rescate1[1] == -1 && rescate2[1] != -1)) borrar1 = 1;
                                else if((rescate2[1] == -1 && rescate1[2] != -1) || (rescate2[1] == -1 && rescate1[1] != -1)) borrar2 = 1;
                                if(aux.swapR(i, j, k, l, rescate1[r], rescate2[s], borrar1, borrar2)) { ///MODIFICAR MATRIZ DE VIAJES
                                    String S = "Los helicopteros " + i + "y " + k + "cambian grupos " + r + "y " + s +
                                            "de los viajes" + j + "y " + l;
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
                        aux.swapV(i, j, k, l);
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
                        aux.setV(i, j, k, l);
                        String S = "Set viaje " + l + " a helicoptero " + i;
                        res.add(new Successor(S, aux));
                    }
                }
            }
        }
    }
}
