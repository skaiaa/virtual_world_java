package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class TryingToCatchIt extends Action {
    protected Location newLocation;
    protected Vector<Organism>toKill;//edu.pg.virtualworld.organisms.Organism*
    public TryingToCatchIt(Location newLocation1, Vector<Organism>toKill1){
        newLocation=newLocation1;
        toKill=toKill1;
    }
    public Boolean isTryingToCatchIt() { return true; }
    public Location getCatch() { return newLocation; }
    public Vector<Organism> kills() { return new Vector<Organism>(); }//edu.pg.virtualworld.organisms.Organism
}


