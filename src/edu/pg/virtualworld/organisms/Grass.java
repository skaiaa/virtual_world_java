package edu.pg.virtualworld.organisms;

import java.awt.*;

public class Grass extends Plant {
    public Grass() {
        super(80);
    }

    public String getName() {
        return "Grass";
    }

    public char getSymbol() {
        return 'g';
    }

    @Override
    public Color getColor() {
        return new Color(51, 204, 51);
    }

}