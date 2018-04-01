package IA.Desastres;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DesastresSuccessorFunctionSA implements SuccessorFunction {

    public List getSuccessors(Object o) {
        DesastresBoard board = (DesastresBoard)o;
        ArrayList res = new ArrayList();
        int size = board.getNumRescates();
        DesastresBoard aux;

        ArrayList op1 = new ArrayList();
        swapRescates(board, op1, size);
        ArrayList op2 = new ArrayList();
        swapViajes(board, op2);
        ArrayList op3 = new ArrayList();
        setRescate(board, op3, size);
        ArrayList op4 = new ArrayList();
        setViaje(board,op4);
        int n1,n2,n3,n4;
        int randOperador;
        Random random = new Random();
        while(/*COSAS DE CONDICIONES WIP*/){
            n1 = op1.size();
            n2 = op2.size();
            n3 = op3.size();
            n4 = op4.size();
            randOperador = random.nextInt(n1+n2+n3+n4);
            if(randOperador < n1){
                //SACAR DE OP2, APLICAR OPERADOR Y BORRAR ELEMENTO DEL CONJUNTO DE SUCESORES
                aux =  new DesastresBoard(board);
                int i = op1.get(randOperador).get(0);
                int j = op1.get(randOperador).get(1);
                if(/*BOOLEAN PARA VALIDAR SUCESOR*/){
                    res.add(new Successor("", aux));
                }
                op1.remove(randOperador);
            }
            if(randOperador < n1 + n2){
                //SACAR DE OP2, APLICAR OPERADOR Y BORRAR ELEMENTO DEL CONJUNTO DE SUCESORES
                aux =  new DesastresBoard(board);
                int i = op2.get(randOperador-n1).get(0);
                int j = op2.get(randOperador-n1).get(1);
                int k = op2.get(randOperador-n1).get(2);
                int l = op2.get(randOperador-n1).get(3);
                if(/*BOOLEAN PARA VALIDAR SUCESOR*/){
                    res.add(new Successor("", aux));
                }
                op2.remove(randOperador);
            }
            if(randOperador < n1 + n2 + n3){
                //SACAR DE OP2, APLICAR OPERADOR Y BORRAR ELEMENTO DEL CONJUNTO DE SUCESORES
                aux =  new DesastresBoard(board);
                int i = op3.get(randOperador-n1-n2).get(0);
                int j = op3.get(randOperador-n1-n2).get(1);
                if(/*BOOLEAN PARA VALIDAR SUCESOR*/){
                    res.add(new Successor("", aux));
                }
                op3.remove(randOperador);
            }
            else{                   //OPERADOR DENTRO DDE N4
                //SACAR DE OP2, APLICAR OPERADOR Y BORRAR ELEMENTO DEL CONJUNTO DE SUCESORES
                aux =  new DesastresBoard(board);
                int i = op4.get(randOperador-n1-n2-n3).get(0);
                int j = op4.get(randOperador-n1-n2-n3).get(1);
                if(/*BOOLEAN PARA VALIDAR SUCESOR*/){
                    res.add(new Successor("", aux));
                }
                op4.remove(randOperador);
            }

        }
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        for(int i = 0; i<size;i++){
            for(int j = i+1; j<size;j++){
                ArrayList<Integer> values = new ArrayList<>();
                values.add(i);
                values.add(j);
                res.add(values);
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
                        ArrayList<Integer> values = new ArrayList<>();
                        values.add(i);
                        values.add(j);
                        values.add(k);
                        values.add(l);
                        res.add(values);
                    }
                }
            }
        }
    }

    private void setRescate(DesastresBoard b, ArrayList res, int size){
        DesastresBoard aux;
        for(int i = 0; i < b.getNumHelicopteros(); i++) {
            for (int j = i + 1; j < b.getNumHelicopteros(); j++) {
                ArrayList<Integer> values = new ArrayList<>();
                values.add(i);
                values.add(j);
                res.add(values);
            }
        }
    }

    private void setViaje(DesastresBoard b, ArrayList res){
        DesastresBoard aux;
        for(int i = 0; i < b.getNumHelicopteros(); i++) {
            for (int j = i + 1; j < b.getNumHelicopteros(); j++) {
                ArrayList<Integer> values = new ArrayList<>();
                values.add(i);
                values.add(j);
                res.add(values);
            }
        }
    }
}
