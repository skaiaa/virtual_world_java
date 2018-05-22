package edu.pg.virtualworld.organisms;

import java.awt.*;

public class Belladonna extends Plant {
    public Belladonna() {super(20);}
    public String getName() { return "Belladonna"; }
    public char getSymbol() { return 'b'; }
    public int getStrength() { return 99; }
    @Override
    public Color getColor() {
        return new Color(0, 0, 102);
    }
}