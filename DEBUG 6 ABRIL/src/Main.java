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

    static int seed[] = {844,1955,251,1948,1658,1392,1499,932,258,1001,1631,1744,12,1138,681,1803,156,1896,558,1234};
    static int nGrupos = 100;
    static int nCentros = 5;
    static int nHelis = 1;
    static boolean heur = false;
    static int op = 0;
    static int algoritmo = 0;
    static int sol = 0;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce número de grupos, centros y helicópteros");
        nGrupos = scanner.nextInt();
        nCentros = scanner.nextInt();
        nHelis = scanner.nextInt();
        System.out.println("Introduce heurístico(0 para tiempo / 1 para prioridad 1)");
        boolean valido = false;
        do{
            int value = scanner.nextInt();
            if(value == 0){heur = false;valido = true;}
            else if(value == 1){heur = true;valido = true;}
            else System.out.println("Error, número no válido");
        }while(valido == false);
        valido = false;
        System.out.println("Introduce conjunto de operadores a usar(valor entre 1 y 4)");
        do{
            int value = scanner.nextInt();
            if(value == 1){op = 1;valido = true;}
            else if(value == 2){op = 2;valido = true;}
            else if(value == 3){op = 3;valido = true;}
            else if(value == 4){op = 4;valido = true;}
            else System.out.println("Error, número no válido");
        }while(valido == false);
        valido = false;
        System.out.println("Introduce algoritmo a usar(0 para Hill Climbing, 1 para Simulated Annealing)");
        do{
            int value = scanner.nextInt();
            if(value == 0){algoritmo = 0;valido = true;}
            else if(value == 1){algoritmo = 1;valido = true;}
            else System.out.println("Error, número no válido");
        }while(valido == false);
        valido = false;
        System.out.println("Introduce solución inicial para el problema(0 para solución básica, 1 para solución llena)");
        do{
            int value = scanner.nextInt();
            if(value == 0){sol = 0;valido = true;}
            else if(value == 1){sol = 1;valido = true;}
            else System.out.println("Error, número no válido");
        }while(valido == false);
        Random random = new Random();
        System.out.println("Introduce semilla(valor -1 usará una semilla aleatoria) ");
        int seed = scanner.nextInt();
        if(seed == -1) seed = random.nextInt(1500);
        Grupos grupos = new Grupos(nGrupos, seed);
        Centros centros = new Centros(nCentros, nHelis, seed);
        DesastresBoard board = new DesastresBoard(grupos, centros,heur,sol ,op);
        System.out.println("Ejecución con seed " + seed);
        if(algoritmo == 0)DesastresHC(board);
        if(algoritmo == 1)DesastresSA(board);
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
            DesastresBoard eFinal = (DesastresBoard)search.getGoalState();
            System.out.println("Rescates: " + eFinal.getTime() + " minutos");
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
            Search search = new SimulatedAnnealingSearch(75000,20,5,0.01);
            SearchAgent agent = new SearchAgent(problem, search);
            long timeFin = System.currentTimeMillis();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            DesastresBoard eFinal = (DesastresBoard)search.getGoalState();
            System.out.println("Rescates: " + eFinal.getTime() + " minutos");
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
