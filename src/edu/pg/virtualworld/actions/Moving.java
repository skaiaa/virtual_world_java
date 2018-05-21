package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Moving extends Action {
    protected Location newLocation;
    protected Vector<Organism> toKill;//edu.pg.virtualworld.organisms.Organism*
    public Moving(Location newLocation1, Vector<Organism>toKill1){
        newLocation=newLocation1;
        toKill=toKill1;
    }
    public Boolean isMoving() { return true; }
    public Location getMove() { return newLocation; }
    public Vector<Organism> kills() { return toKill; }//edu.pg.virtualworld.organisms.Organism*
        //virtual void moveTo(Location location){}
};
