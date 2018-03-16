import IA.Desastres.*;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Grupos grupos = new Grupos(10, 1234);
        Centros centros = new Centros(5, 10, 1234);

        DesastresBoard board = new DesastresBoard(grupos, centros);

        System.out.println("Grupos:");
        for (int i = 0; i < grupos.size(); ++i) {
            System.out.println(board.getGrupo(i).getCoordX() + " " + board.getGrupo(i).getCoordY() + " " + board.getGrupo(i).getPrioridad() + " " +  board.getGrupo(i).getNPersonas());
        }
        System.out.println();
        System.out.println("Centors:");
        for (int i = 0; i < centros.size(); ++i) {
            System.out.println(board.getCentro(i).getCoordX() + " " +  board.getCentro(i).getCoordY() + " " + board.getCentro(i).getNHelicopteros());
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
