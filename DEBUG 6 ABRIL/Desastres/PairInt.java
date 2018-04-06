package Desastres;

public class PairInt {

    public int first;
    public int second;

    public PairInt() {
        first = 0;
        second = 0;
    }

    public PairInt(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public PairInt(PairInt p) {
        this.first = p.first;
        this.second = p.second;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}