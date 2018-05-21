package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Reproducing extends Action {
    protected Location newLocation;
    public Reproducing(Location newLocation1){
        newLocation=newLocation1;
    }
    public Boolean isReproducing() { return true; }
    public Location getReproduce() { return newLocation; }
    public Vector<Organism> kills() { return new Vector<Organism>(); }//edu.pg.virtualworld.organisms.Organism*
};

