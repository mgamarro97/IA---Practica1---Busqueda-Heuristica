import IA.Desastres.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import javafx.util.Pair;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Grupos grupos = new Grupos(10, 1916);
        Centros centros = new Centros(5, 10, 1916);

        DesastresBoard board = new DesastresBoard(grupos, centros);

        System.out.println("Grupos:");
        for (int i = 0; i < grupos.size(); ++i) {
            System.out.println(board.getGrupo(i).getCoordX() + " " + board.getGrupo(i).getCoordY() + " " + board.getGrupo(i).getPrioridad() + " " +  board.getGrupo(i).getNPersonas());
        }
        System.out.println();
        System.out.println("Centros:");
        for (int i = 0; i < centros.size(); ++i) {
            System.out.println(board.getCentro(i).getCoordX() + " " +  board.getCentro(i).getCoordY() + " " + board.getCentro(i).getNHelicopteros());
        }

        //DesastresHC(board);
        //DesastresSA(board);

        /*
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "Holi");
        hm.put(2, "Que");
        hm.put(3, "Tal");
        hm.put(4, getHM(1, hm));
        hm.replace(4, "Estamos");
        printAll(hm);
        System.out.println("Size: " + hm.size());



        ArrayList<Integer> al1 = new ArrayList<Integer>();
        for (int i = 0; i < 5; ++i) { al1.add(i); }

        ArrayList<Integer> al2 = new ArrayList<Integer>(al1); // No hay shallow Copy
        al2.remove(0);

        System.out.println();
        for (Integer i : al1) { System.out.println(i); }
        System.out.println();

        HashMap<Integer, ArrayList<Integer>> a = new HashMap<Integer, ArrayList<Integer>>();
        a.put(1, al1);
        HashMap<Integer, ArrayList<Integer>> b = new HashMap<Integer, ArrayList<Integer>>(a); // Hay shallow Copy
        b.put(2, al2);
        b.get(1).remove(0);

        Set sa = a.entrySet();
        Iterator ia = sa.iterator();

        Set sb = b.entrySet();
        Iterator ib = sb.iterator();

        System.out.println("a: " + a.size());
        while (ia.hasNext()) {
            System.out.println(ia.next());
        }
        System.out.println();
        System.out.println("b: " + b.size());
        while (ib.hasNext()) {
            System.out.println(ib.next());
        }
        System.out.println();
        for (Integer i : al1) { System.out.println(i); }
        System.out.println();
        for (Integer i : al2) { System.out.println(i); }
        */
    }

    /*
    private static String getHM(int i, HashMap<Integer, String> hm) {
        return (hm.get(i));
    }

    private static void printAll(HashMap<Integer, String> hm) {
        Set set = hm.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            System.out.println(me.getKey() + " " + me.getValue());
        }
    }
    */

    private static void DesastresHC(DesastresBoard board) {
        try {
            System.out.println("\nHill Climbing");
            Problem problem = new Problem(board, new DesastresSuccessorFunction(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DesastresSA(DesastresBoard board) {
        try {
            System.out.println();
            Problem problem = new Problem(board, new DesastresSuccessorFunctionSA(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new SimulatedAnnealingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); ++i) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
