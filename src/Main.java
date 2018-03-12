import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.IterativeDeepeningAStarSearch;
import IA.Desastres.*;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner init = new Scanner(System.in);
        int[] prob = new int[5];
        int[] sol = new int[5];
        for (int i = 0; i < 5; ++i) {
            prob[i] = init.nextInt();
        }
        Scanner solu = new Scanner(System.in);
        for (int i = 0; i < 5; ++i) {
            sol[i] = solu.nextInt();
        }

        DesastresBoard board = new DesastresBoard();

        Problem p = new Problem(board,
                new DesastresSuccessorFunction(),
                new DesastresGoalTest(),
                new DesastresHeuristicFunction());

        Search alg = new AStarSearch(new GraphSearch());
        long t1 = System.nanoTime();
        SearchAgent agent = new SearchAgent(p, alg);
        long t2 = System.nanoTime();

        System.out.println("A*");
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        System.out.println("Running time: " + (t2 - t1) + "ns");
        System.out.println();


        alg = new IterativeDeepeningAStarSearch();
        long t3 = System.nanoTime();
        agent = new SearchAgent(p, alg);
        long t4 = System.nanoTime();

        System.out.println("IDA*");
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        System.out.println("Running time: " + (t4 - t3) + "ns");
        System.out.println();
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
