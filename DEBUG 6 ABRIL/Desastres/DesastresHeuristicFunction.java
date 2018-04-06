package Desastres;

import aima.search.framework.HeuristicFunction;

public class DesastresHeuristicFunction implements HeuristicFunction{

    public double getHeuristicValue(Object n) {
        return ((DesastresBoard) n).getHeuristicValue();
    }
}
