package IA.Desastres;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class DesastresSuccessorFunction implements SuccessorFunction {
//EIS HOLA QUE ESTO ESTÁ EN GENERAL ASÍ COMO MUY A MEDIAS SÉ QUE HAY 72 COSAS MAL PROBABLEMENTE
    public List getSuccessors(Object o) {
        ArrayList res = new ArrayList();
        DesastresBoard board = (DesastresBoard)o;
        int size = board.getNumRescates();
        swapRescates(board, res, size);
        swapViajes(board, res, size);
        setRescate(board, res, size);
        return null;
      }

      private void swapRescates(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        for(int i = 0; i<size;i++){
            for(int j = i+1; j<size;j++){
                aux =  new DesastresBoard(b);
                if(comprobar si hijo cumple las condiciones de tamaño y numero){
                    aux.swapR(i, j);
                    String S = "Intercambiar rescates " + i + " y " + j;
                    res.add(new Successor(S, aux));
                }
            }
        }
    }

    private void swapViajes(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        int nHelicopteros = b.getNumHelicopteros();
            for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
                int[] viajesHelicoptero1 = b.getViajesH(i);  //VIAJE DE H1 / GETVIAJES ESTOY SUPONIENDO LO QUE HACE
                for(int j = 0; j < viajesHelicoptero1.length; j++) {

                    for (int k = i + 1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                        int[] viajesHelicoptero2 = b.getViajesH(k);      //VIAJE DE H2 / GETVIAJES ESTOY SUPONIENDO LO QUE HACE
                        for (int l = 0; l < viajesHelicoptero2.length; l++) {
                            aux =  new DesastresBoard(b);
                            if(comprobar si hijo cumple las condiciones de tamaño y numero){
                                aux.swapV(i, j, k, l); //MODIFICAR MATRIZ DE VIAJES
                                String S = "Juntar rescate " + i + " y rescate " + j;
                                res.add(new Successor(S, aux));
                            }
                        }
                    }
                }
            }
    }

    private void setRescate(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        for(int i = 0; i<size;i++){
            for(int j = 0; j<size;j++) {
                aux =  new DesastresBoard(b);
                if(comprobar si hijo cumple las condiciones de tamaño y numero && i!=j){
                    aux.setR(i, j); //MODIFICAR MATRIZ DE VIAJES
                    String S = "Juntar rescate " + i + " y rescate " + j;
                    res.add(new Successor(S, aux));
                }
            }
        }
    }
}
