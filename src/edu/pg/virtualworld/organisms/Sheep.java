package edu.pg.virtualworld.organisms;

import java.awt.*;

public class Sheep extends Animal {
    public Sheep() {
        super(4);
    }
    public String getName() { return "Sheep"; }
    public int getInitiative() { return 4; }
    public char getSymbol() { return 'S'; }
    @Override
    public Color getColor() {
        return Color.WHITE;
    }
}