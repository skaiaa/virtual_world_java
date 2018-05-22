package edu.pg.virtualworld.organisms;

import java.awt.*;

public class Wolf extends Animal {
    public Wolf() {
        super(9);
    }
    public String getName() { return "Wolf"; }
    public int getInitiative() { return 5; }
    public char getSymbol() { return 'W'; }
    @Override
    public Color getColor() {
        return new Color(153, 153, 102);
    }
}