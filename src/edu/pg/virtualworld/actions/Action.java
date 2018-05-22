package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public abstract class Action {
    public Boolean isMoving() {
        return false;
    }

    public Location getMove() {
        throw new RuntimeException("Unable to move!");
    }

    public Boolean isSpreading() {
        return false;
    }

    public Vector<Location> getSpread() {
        throw new RuntimeException("Soil is infertile here!");
    }

    public Boolean isReproducing() {
        return false;
    }

    public Location getReproduce() {
        throw new RuntimeException("I'm infertile.");
    }

    public Boolean isFighting() {
        return false;
    }

    public Location getFight() {
        throw new RuntimeException("No one is fighting here!");
    }

    public Boolean isTryingToCatchIt() {
        return false;
    }

    public Location getCatch() {
        throw new RuntimeException("No one is running away!");
    }

    public Boolean isActivatingSpecialAbility() {
        return false;
    }

    public String getAbility() {
        throw new RuntimeException("I cannot do anything special.");
    }

    public abstract Vector<Organism> kills();
}
