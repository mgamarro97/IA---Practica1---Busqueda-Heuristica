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
        setRescate(board, res, size);
        setViaje(board,res);
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        for(int i = 0; i<size;i++){
            for(int j = i+1; j<size;j++){
                aux =  new DesastresBoard(b);
                if(aux.swapR(i, j)) {
                    String S = "Swapear rescates " + i + " y " + j;
                    res.add(new Successor(S, aux));
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
                        String S = "Swapear viajes " + i + " y " + j;
                        res.add(new Successor(S, aux));
                    }
                }
            }
        }
    }

    private void setRescate(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = 0; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 0; l < viajesHelicoptero2; l++) {
                        aux =  new DesastresBoard(b);

                        if(i != k && j != l){
                            int grupo1[] = b.getGruposRescatados(i,j);
                            for(int r = 0; r < 3;r++){
                                if (grupo1[2] != -1)break;
                                int grupo2[] = b.getGruposRescatados(k,l);
                                if (grupo2[1] == -1)break;
                                for (int s = r+1; s < 3;s++){
                                    if(aux.setR(i, j, k, l, grupo1[r], grupo2[s])) { ///MODIFICAR MATRIZ DE VIAJES
                                        String S = "Set viaje " + l + " a helicoptero " + i;
                                        res.add(new Successor(S, aux));
                                    }
                                }
                            }
                        }
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
