package IA.Desastres;

import java.util.*;

public class DesastresHelicoptero {

    public static int MAX_GROUPS = 3;
    public static int MAX_CARRY = 15;

    HashMap<Integer, ArrayList<Grupo>> rescates;

    public DesastresHelicoptero() {
        rescates = new HashMap<Integer, ArrayList<Grupo>>();
    }

    public DesastresHelicoptero(HashMap<Integer, ArrayList<Grupo>> rescates) {
        this.rescates = copia(rescates);
    }

    public int getNumViajes() {
        return rescates.size();
    }

    // Para que funcione, 0 <= i <= nViajes
    public ArrayList<Grupo> getViaje(int i) {
        return new ArrayList<Grupo>(rescates.get(i));
    }

    public HashMap<Integer, ArrayList<Grupo>> getRescates() {
        return copia(this.rescates);
    }

    public boolean setTrip (ArrayList<Grupo> viaje) {
        if (viaje.size() >= MAX_GROUPS) return false;
        int sum = 0;
        for (Grupo g : viaje) {
            sum += g.getNPersonas();
            if (sum >= MAX_CARRY) return false;
        }

        rescates.put(rescates.size() + 1, new ArrayList<Grupo>(viaje));
        return true;
    }

    public void changeTrip(int trip, ArrayList<Grupo> newTrip) {
        rescates.remove(trip);
        rescates.put(trip, new ArrayList<Grupo>(newTrip));
    }

    private HashMap<Integer, ArrayList<Grupo>> copia(HashMap<Integer, ArrayList<Grupo>> cpy) {
        HashMap<Integer, ArrayList<Grupo>> letsCpy = new HashMap<Integer, ArrayList<Grupo>>();

        Set s = cpy.entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            letsCpy.put(new Integer((Integer)me.getKey()), new ArrayList<Grupo>((ArrayList<Grupo>)me.getValue()));
        }

        return letsCpy;
    }
}
