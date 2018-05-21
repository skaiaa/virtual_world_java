package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class ActivatingSpecialAbility extends Action {
    protected String ability;
    protected Vector<Organism>toKill;
    public ActivatingSpecialAbility(String ability1, Vector<Organism>toKill1){
        ability=ability1;
        toKill=toKill1;
    }
    public Boolean isActivatingSpecialAbility() { return true; }
    public String getAbility() { return ability; }
    public Vector<Organism> kills() { return toKill; }
}


