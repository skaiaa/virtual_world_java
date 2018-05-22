package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class ActivatingSpecialAbility extends Action {
    private String ability;
    private Vector<Organism>toKill;
    public ActivatingSpecialAbility(String ability, Vector<Organism>toKill){
        this.ability=ability;
        this.toKill=toKill;
    }
    public Boolean isActivatingSpecialAbility() { return true; }
    public String getAbility() { return ability; }
    public Vector<Organism> kills() { return toKill; }
}


