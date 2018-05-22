package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Fighting extends Action {
    private Location fightLocation;
    private Vector<Organism> toKill;

    public Fighting(Location fightLocation1, Vector<Organism> toKill) {
        this.toKill = toKill;
        fightLocation = fightLocation1;
    }

    public Boolean isFighting() {
        return true;
    }

    public Location getFight() {
        return fightLocation;
    }

    public Vector<Organism> kills() {
        return toKill;
    }
}

