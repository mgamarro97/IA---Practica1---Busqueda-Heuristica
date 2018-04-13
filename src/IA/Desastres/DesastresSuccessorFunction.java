package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class DesastresSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        ArrayList res = new ArrayList();
        DesastresBoard board = (DesastresBoard)o;
        setRescate(board, res);
        //System.out.println(res.size());
        swapRescates(board, res);
        //System.out.println(res.size());
        swapViajes(board, res);
        //System.out.println(res.size());
        setViaje(board,res);
        //System.out.println(res.size());
        return res;
    }

    private void setRescate(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nRescates = b.getNumRescates();
        for(int i = 0; i < nRescates; i++){
            for(int j = 0; j < nRescates; j++){
                if(i != j){
                    aux =  new DesastresBoard(b);
                    if(aux.setR(i, j)) {
                        String S = "Pasar grupo " + i + " a rescate del grupo " + j;
                        res.add(new Successor(S, aux));
                       // System.out.println(i + " " +j);
                    }
                }
            }
        }
    }

    private void swapRescates(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nRescates = b.getNumRescates();
        for(int i = 0; i < nRescates; i++){
            for(int j = i+1; j < nRescates; j++){
                    aux =  new DesastresBoard(b);
                    if(aux.swapR(i, j)) {
                        String S = "Cambiar valores de " + "grupo " + i + " por valores del grupo " + j;
                        res.add(new Successor(S, aux));
                        //System.out.println(i + " " +j);
                    }
            }
        }
    }

    private void swapViajes(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        aux =  new DesastresBoard(b);
                        aux.swapV(i, j, k, l);
                        String S = "Swapear viajes " + j + " y " + l + " de los helicopteros " + i + " y " + k;
                        res.add(new Successor(S, aux));
                        //System.out.println(i + " " +j+" "+k+" "+l);
                    }
                }
            }
        }
      //  System.out.println(cont + " " + nHelicopteros);
    }

    private void setViaje(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = 0; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        if(i != k) {    //añadir viajesHelicoptero != 1
                            aux =  new DesastresBoard(b);
                            if(aux.setV(i, viajesHelicoptero1, k, l)) {
                                String S = "Set viaje " + l + " a helicoptero " + i;
                                res.add(new Successor(S, aux));
                                //System.out.println(i + " " + j + " " + k + " " + l);
                            }
                        }
                    }
                }
            }
        }
       // System.out.println(cont + " " + nHelicopteros);
    }
}
