package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.EmptyStackException;
import java.util.Vector;

public abstract class Action {
    public Boolean isDoingNothing() { return false; }
    public Boolean isMoving() { return false; };
    public Location getMove() {
        //throw exception("Unable to move!");
        throw new EmptyStackException();
    };
    public Boolean isSpreading() { return false; };
    public Vector<Location> getSpread() {
        //throw exception("Soil is infertile here!");
        throw new EmptyStackException();
    };
    public Boolean isReproducing() { return false; }
    public Location getReproduce() {
        //throw exception("I'm infertile.");
        throw new EmptyStackException();
    }
    public Boolean isFighting() { return false; }
    public Location getFight() {
        //throw exception("No one is fighting here!");
        throw new EmptyStackException();
    }
    public Boolean isTryingToCatchIt() { return false; }
    public Location getCatch() {
        //throw exception("No one is running away!");
        throw new EmptyStackException();
    }
    public Boolean isActivatingSpecialAbility() { return false; }
    public String getAbility() {
        //throw exception("I cannot do anything special.");
        throw new EmptyStackException();
    }
    public abstract Vector<Organism> kills();//edu.pg.virtualworld.organisms.Organism*
}
