import IA.Desastres.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {


    static long tExec;
    static double tRescates = 0;

    public static void main(String[] args) throws Exception {

        int replicas = 20;
        final int seed[] = {844, 1955, 251, 1948, 1658, 1392, 1499, 932, 258, 1001, 1637, 1744, 12, 1138, 681, 1803, 156, 1896, 558, 1234};
        int grupos = 100;
        int centros = 5;
        int helicopteros = 1;

        File out = new File("4OP1.txt");
        PrintWriter writer = new PrintWriter(out);


        for (int i = 0; i < replicas; ++i) {
            System.out.println("Pueba nº: " + (i+1) + " con semilla: " + seed[i]);
            Grupos gs = new Grupos(grupos, seed[i]);
            Centros cs = new Centros(centros, helicopteros, seed[i]);
            DesastresBoard board = new DesastresBoard(grupos, centros, false, 0, 4);
            DesastresHC(board);
            writer.println(tExec + " " + tRescates);
        }
        writer.close();
    }

    private static void DesastresHC(DesastresBoard board) {
        try {
            //System.out.println("\nHill Climbing");
            long timeInit = System.currentTimeMillis();
            Problem problem = new Problem(board, new DesastresSuccessorFunction(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            long timeFin = System.currentTimeMillis();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            DesastresBoard eFinal = (DesastresBoard)search.getGoalState();
            tExec = timeFin - timeInit;
            tRescates = eFinal.time;
            //System.out.println(eFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DesastresSA(DesastresBoard board) {
        try {
            //System.out.println("\nSimulated Annealing");
            long timeInit = System.currentTimeMillis();
            Problem problem = new Problem(board, new DesastresSuccessorFunctionSA(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new SimulatedAnnealingSearch(100000,20,40,0.0001);
            SearchAgent agent = new SearchAgent(problem, search);
            long timeFin = System.currentTimeMillis();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            tExec = timeFin - timeInit;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            //System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); ++i) {
            String action = actions.get(i).toString();
            //System.out.println(action);
        }
    }
}
