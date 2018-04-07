package src;

import Desastres.*;
import IA.Desastres.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Grupos grupos = new Grupos(100, 1234);
        Centros centros = new Centros(5, 1, 1234);

        DesastresBoard board = new DesastresBoard(grupos, centros, true);
        /*
        System.out.println("Grupos:");
        for (int i = 0; i < grupos.size(); ++i) {
            System.out.println(board.getGrupo(i).getCoordX() + " " + board.getGrupo(i).getCoordY() + " " + board.getGrupo(i).getPrioridad() + " " +  board.getGrupo(i).getNPersonas());
        }
        System.out.println();
        System.out.println("Centros:");
        for (int i = 0; i < centros.size(); ++i) {
            System.out.println(board.getCentro(i).getCoordX() + " " +  board.getCentro(i).getCoordY() + " " + board.getCentro(i).getNHelicopteros());
        }*/

        //DesastresHC(board);
        DesastresSA(board);

    }

    private static void DesastresHC(DesastresBoard board) {
        try {
            System.out.println("\nHill Climbing");
            long timeInit = System.currentTimeMillis();
            Problem problem = new Problem(board, new DesastresSuccessorFunction(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            long timeFin = System.currentTimeMillis();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            System.out.println("Execution time: " + (timeFin - timeInit) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DesastresSA(DesastresBoard board) {
        try {
            System.out.println("\nSimulated Annealing");
            long timeInit = System.currentTimeMillis();
            Problem problem = new Problem(board, new DesastresSuccessorFunctionSA(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new SimulatedAnnealingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            long timeFin = System.currentTimeMillis();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            System.out.println("Execution time: " + (timeFin - timeInit) + "ms");
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
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i).toString();
            System.out.println(action);
        }
    }
}