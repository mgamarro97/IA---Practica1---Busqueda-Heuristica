
public class Main {

    static int replicas = 10;
    static long tExec = 0;
    static double tRescates = 0;
    static int seed = 0;

    public static void main(String[] args) throws Exception {
        File out = new File("PRUEBACACA.txt");
        PrintWriter writer = new PrintWriter(out);
        Random random = new Random();
        for(int i = 1; i <= replicas; i++) {
            seed = random.nextInt(1500);
            System.out.println("Prueba nº: " + i +" con semilla: " + seed);
            Grupos grupos = new Grupos(100, seed);
            Centros centros = new Centros(5, 1, seed);

            DesastresBoard board = new DesastresBoard(grupos, centros, false);
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

            DesastresHC(board);
            //DesastresSA(board);

           writer.println(tExec + " " + tRescates);

        }
       writer.close();
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

            tExec = timeFin-timeInit;
            tRescates = eFinal.time;

            System.out.println("Tiempo de ejecución:" + tExec + ". Tiempo en rescates: " + tRescates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DesastresSA(DesastresBoard board) {
        try {
            System.out.println("\nSimulated Annealing");
            long timeInit = System.currentTimeMillis();
            Problem problem = new Problem(board, new DesastresSuccessorFunctionSA(), new DesastresGoalTest(), new DesastresHeuristicFunction());
            Search search = new SimulatedAnnealingSearch(90000,100,25,0.01);
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
