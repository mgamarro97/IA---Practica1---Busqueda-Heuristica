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
        setRescate(board, res);
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
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1 / GETVIAJES ESTOY SUPONIENDO LO QUE HACE
            for(int j = 0; j < viajesHelicoptero1; j++) {

                for (int k = i + 1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2 / GETVIAJES ESTOY SUPONIENDO LO QUE HACE
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

    private void setRescate(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        for(int i = 0; i < b.getNumHelicopteros(); i++) {
            for (int j = i + 1; j < b.getNumHelicopteros(); j++) {
                aux =  new DesastresBoard(b);
                if(aux.setR(i, j)){
                    String S = "Set rescate " + i + " a rescate " + j;
                    res.add(new Successor(S, aux));
                }
            }
        }
    }

    private void setViaje(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        for(int i = 0; i < b.getNumHelicopteros(); i++) {
            for (int j = i + 1; j < b.getNumHelicopteros(); j++) {
                aux =  new DesastresBoard(b);
                if(aux.setV(i,j)) {//MODIFICAR MATRIZ DE VIAJES
                    String S = "Set viaje " + i + " a rescate " + j;
                    res.add(new Successor(S, aux));
                }
            }
        }
    }
}
