package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Spreading extends Action {
    protected Vector<Location> toLocations;
    protected Vector<Organism>toKill;//edu.pg.virtualworld.organisms.Organism*
    public Spreading(Vector<Location> toLocations1, Vector<Organism>toKill1) {
        toLocations=toLocations1;
        toKill=toKill1;
    };
    public Boolean isSpreading() { return true; }
    public Vector<Location> getSpread() { return toLocations; }
    public Vector<Organism> kills() { return toKill; }//edu.pg.virtualworld.organisms.Organism*
};