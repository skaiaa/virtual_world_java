package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class Fighting extends Action {
    protected Location fightLocation;
    protected Vector<Organism>toKill;//edu.pg.virtualworld.organisms.Organism*
    public Fighting(Location fightLocation1, Vector<Organism>toKill1){
        toKill=toKill1;
        fightLocation=fightLocation1;
    }
    public Boolean isFighting() { return true; }
    public Location getFight() { return fightLocation; }
    public Vector<Organism> kills() { return toKill; }
        }

