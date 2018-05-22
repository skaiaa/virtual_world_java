package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Moving;

import java.awt.*;
import java.util.Vector;

public class Fox extends Animal {
    public Fox() {
        super(3);
    }

    public String getName() {
        return "Fox";
    }

    public int getInitiative() {
        return 7;
    }

    public char getSymbol() {
        return 'F';
    }

    public Action action(Vector<Organism> organisms) {
        Location newLocation = chooseNewLocation(getLocation());
        for (Organism o : organisms) {
            if ((o.getLocation() == newLocation) && (o.getStrength() > getStrength())) return new DoNothing();
        }
        return new Moving(newLocation, new Vector<>());
    }

    @Override
    public Color getColor() {
        return new Color(255, 102, 0);
    }
}