package Desastres;

public class Helicoptero {
    private int x;
    private int y;
    private int carry;

    public Helicoptero(int x, int y) {
        this.x = x;
        this.y = y;
        this.carry = 0;
    }

    public Helicoptero(int x, int y, int carry) {
        this.x = x;
        this.y = y;
        this.carry = carry;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCarry() {
        return carry;
    }

    public void setCarry(int carry) {
        this.carry = carry;
    }
}
