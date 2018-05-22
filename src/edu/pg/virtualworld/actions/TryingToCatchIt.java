package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class TryingToCatchIt extends Action {
    private Location newLocation;
    private Vector<Organism> toKill;

    public TryingToCatchIt(Location newLocation, Vector<Organism> toKill) {
        this.newLocation = newLocation;
        this.toKill = toKill;
    }

    public Boolean isTryingToCatchIt() {
        return true;
    }

    public Location getCatch() {
        return newLocation;
    }

    public Vector<Organism> kills() {
        return new Vector<>();
    }
}


