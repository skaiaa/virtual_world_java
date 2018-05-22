package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Moving extends Action {
    private Location newLocation;
    private Vector<Organism> toKill;

    public Moving(Location newLocation, Vector<Organism> toKill) {
        this.newLocation = newLocation;
        this.toKill = toKill;
    }

    public Boolean isMoving() {
        return true;
    }

    public Location getMove() {
        return newLocation;
    }

    public Vector<Organism> kills() {
        return toKill;
    }
}
