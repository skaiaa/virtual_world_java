package edu.pg.virtualworld.organisms;

public class Wolf extends Animal {
    public Wolf() {
        super(9);
    }
    public String getName() { return "Wolf"; }
    public int getInitiative() { return 5; }
    public char getSymbol() { return 'W'; }
}