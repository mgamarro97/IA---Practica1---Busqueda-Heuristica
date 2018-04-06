package Desastres;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DesastresSuccessorFunctionSA implements SuccessorFunction {

    public List getSuccessors(Object o) {
        DesastresBoard board = (DesastresBoard)o;
        ArrayList res = new ArrayList();
        DesastresBoard aux;

        ArrayList <ArrayList> op1 = new ArrayList();
        swapRescates(board, op1);
        ArrayList <ArrayList> op2 = new ArrayList();
        swapViajes(board, op2);
        ArrayList <ArrayList> op3 = new ArrayList();
        setViaje(board,op3);
        int n1,n2,n3;
        int randOperador;
        Random random = new Random();
        n1 = op1.size();
        n2 = op2.size();
        n3 = op3.size();
         while(res.isEmpty() && n1+n2+n3 >1){
            randOperador = random.nextInt(n1+n2+n3);
            if(randOperador < n1){
                aux =  new DesastresBoard(board);
                int i = (Integer) op1.get(randOperador).get(0);
                int j = (Integer) op1.get(randOperador).get(1);
                int k = (Integer) op1.get(randOperador).get(2);
                int l = (Integer) op1.get(randOperador).get(3);
                int grupo1 = (Integer) op1.get(randOperador).get(4);
                int grupo2 = (Integer) op1.get(randOperador).get(5);
                int borrar1 = (Integer) op1.get(randOperador).get(6);
                int borrar2 = (Integer) op1.get(randOperador).get(7);
                System.out.println(i + " " +j+" "+grupo1+" "+k+" "+l+" "+grupo2);
                if(aux.swapR(i, j, k, l, grupo1, grupo2, borrar1, borrar2)){
                    res.add(new Successor("", aux));
                }
                op1.remove(randOperador);
                n1 = op1.size();
            }
            else if(randOperador < n1 + n2){
                aux =  new DesastresBoard(board);
                int i = (Integer) op2.get(randOperador-n1).get(0);
                int j = (Integer) op2.get(randOperador-n1).get(1);
                int k = (Integer) op2.get(randOperador-n1).get(2);
                int l = (Integer) op2.get(randOperador-n1).get(3);
                aux.swapV(i,j,k,l);
                res.add(new Successor("", aux));
                System.out.println(i + " " +j+" "+k+" "+l);
                op2.remove(randOperador-n1);
                n2 = op2.size();
            }
            else{
                aux =  new DesastresBoard(board);
                int i = (Integer) op3.get(randOperador-n1-n2).get(0);
                int j = (Integer) op3.get(randOperador-n1-n2).get(1);
                int k = (Integer) op3.get(randOperador-n1-n2).get(2);
                int l = (Integer) op3.get(randOperador-n1-n2).get(3);
                if(aux.setV(i,j,k,l)){
                    res.add(new Successor("", aux));
                    System.out.println(i + " " +j+" "+k+" "+l);
                }
                op3.remove(randOperador-n2-n1);
                n3 = op3.size();
            }
        }
        System.out.println(res.size());
        if(res.isEmpty())res.add(new Successor("",board));
        return res;
    }

    private void swapRescates(DesastresBoard b, ArrayList res){
        int borrar1 = 0, borrar2 = 0;
        int nHelicopteros = b.getNumHelicopteros();
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
                                if(rescate1[r] == -1 && rescate2[s] == -1)break;
                                if((rescate1[1] == -1 && rescate2[2] != -1) || (rescate1[1] == -1 && rescate2[1] != -1)) borrar1 = 1;
                                else if((rescate2[1] == -1 && rescate1[2] != -1) || (rescate2[1] == -1 && rescate1[1] != -1)) borrar2 = 1;
                                ArrayList<Integer> values = new ArrayList<>();
                                values.add(i);
                                values.add(j);
                                values.add(k);
                                values.add(l);
                                values.add(rescate1[r]);
                                values.add(rescate2[s]);
                                values.add(borrar1);
                                values.add(borrar2);
                                res.add(values);
                            }
                        }
                    }
                }
            }
        }
    }

    private void swapViajes(DesastresBoard b, ArrayList res){
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = i+1; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
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

    private void setViaje(DesastresBoard b, ArrayList res){
        int nHelicopteros = b.getNumHelicopteros();
        for (int i = 0; i < nHelicopteros; i++){          //HELICOPTERO 1
            int viajesHelicoptero1 = b.getNumViajes(i);  //VIAJE DE H1
            for(int j = 1; j <= viajesHelicoptero1; j++) {

                for (int k = 0; k < nHelicopteros; k++) {    //HELICOPTERO 2
                    int viajesHelicoptero2 = b.getNumViajes(k);      //VIAJE DE H2
                    for (int l = 1; l <= viajesHelicoptero2; l++) {
                        if(i != k) {
                            ArrayList<Integer> values = new ArrayList<>();
                            values.add(i);
                            values.add(viajesHelicoptero1);
                            values.add(k);
                            values.add(l);
                            res.add(values);
                        }
                    }
                }
            }
        }
    }
}