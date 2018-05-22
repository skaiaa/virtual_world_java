package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Spreading extends Action {
    private Vector<Location> toLocations;
    private Vector<Organism>toKill;
    public Spreading(Vector<Location> toLocations, Vector<Organism>toKill) {
        this.toLocations=toLocations;
        this.toKill=toKill;
    }
    public Boolean isSpreading() { return true; }
    public Vector<Location> getSpread() { return toLocations; }
    public Vector<Organism> kills() { return toKill; }
}