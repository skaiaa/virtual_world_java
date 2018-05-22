package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Reproducing extends Action {
    private Location newLocation;
    public Reproducing(Location newLocation){
        this.newLocation=newLocation;
    }
    public Boolean isReproducing() { return true; }
    public Location getReproduce() { return newLocation; }
    public Vector<Organism> kills() { return new Vector<>(); }
}

