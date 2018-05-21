package edu.pg.virtualworld.organisms;

public class Guarana extends Plant {
    public Guarana() {super(30);}
    public String getName() { return "Guarana"; }
    public char getSymbol() { return 'u'; }
    public Boolean isIncreasingStrength() { return true; }
    public int getIncrease() { return 3; }
}