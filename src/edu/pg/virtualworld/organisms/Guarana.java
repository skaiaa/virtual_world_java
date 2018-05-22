package edu.pg.virtualworld.organisms;

import java.awt.*;

public class Guarana extends Plant {
    public Guarana() {super(30);}
    public String getName() { return "Guarana"; }
    public char getSymbol() { return 'u'; }
    public Boolean isIncreasingStrength() { return true; }
    public int getIncrease() { return 3; }
    @Override
    public Color getColor() {
        return new Color(255, 153, 51);
    }
}