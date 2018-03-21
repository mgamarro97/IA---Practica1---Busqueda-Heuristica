package IA.Desastres;

import java.util.*;

public class DesastresHelicoptero {

    public static int MAX_GROUPS = 3;
    public static int MAX_CARRY = 15;

    HashMap<Integer, Grupo[]> rescates;

    public DesastresHelicoptero() {
        rescates = new HashMap<Integer, Grupo[]>();
    }
/*
    public DesastresHelicoptero(HashMap<Integer, ArrayList<Grupo>> rescates) {
        this.rescates = copia(rescates);
    }

    public boolean containsKey(int i) {
        return rescates.containsKey(i);
    }

    public int getNumViajes() {
        return rescates.size();
    }

    public int getNumPersonas(int i) {
        if (containsKey(i)) {
            int sum = 0;
            for (Grupo g : rescates.get(i)) {
                sum += g.getNPersonas();
            }
            return sum;
        }
        return -1;
    }

    public int getNumPersonas(ArrayList<Grupo> grupos) {
        int sum = 0;
        for (Grupo g : grupos) { sum += g.getNPersonas(); }
        return sum;
    }

    public int getNumGrupos(int i) {
        if (containsKey(i)) {
            return rescates.get(i).size();
        }
        return -1;
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
        if (getNumPersonas(viaje) >= MAX_CARRY) return false;

        rescates.put(rescates.size() + 1, new ArrayList<Grupo>(viaje));
        return true;
    }

    public boolean changeTrip(int trip, ArrayList<Grupo> newTrip) {
        if (newTrip.size() >= MAX_GROUPS) return false;
        if (getNumPersonas(newTrip) >= MAX_CARRY) return false;

        rescates.remove(trip);
        rescates.put(trip, new ArrayList<Grupo>(newTrip));
        return true;
    }

    public boolean addGroupToTrip(Grupo g) {
        ArrayList<Grupo> tmp = new ArrayList<Grupo>(rescates.get(rescates.size()));

        if (!rescates.isEmpty()) {
            rescates.get(rescates.size()).add(g);
        }
        else {
            ArrayList<Grupo> aux = new ArrayList<Grupo>();
            aux.add(g);
            rescates.put(1, aux);
        }
        return true;
    }

    public boolean addTrip(int i, ArrayList<Grupo> res) {
        if (res.size() >= MAX_GROUPS) return false;
        if (getNumPersonas(res) >= MAX_CARRY) return false;

        if (containsKey(i)) return false;
        rescates.put(i, new ArrayList<Grupo>(res));
        return true;
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
    */
}
