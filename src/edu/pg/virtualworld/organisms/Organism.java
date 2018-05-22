package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;

import java.awt.*;
import java.util.Vector;

public abstract class Organism implements Comparable<Organism> {
    protected Location location = new Location(-1, -1);//zeby mi getFreeAndRandomLocation dzialalo za pierwszym razem
    protected int age = 0;
    public abstract Action action(Vector<Organism>organisms);
    public Location getLocation() { return location; }
    public void setLocation(Location location1) { location = location1; }
    private int getAge() { return age; }
    public void growOlder() { age++; }
    public abstract int getInitiative();
    public abstract char getSymbol();
    public abstract int getStrength();
    public abstract Color getColor();
    public abstract void setStrength(int s);
    public abstract String getInfoForSave();
    public abstract void getStatsFromFile(String[] line);
    public abstract Action collision(Organism collider, Location where);

    public Boolean isRunningAway() { return false; }
    public Boolean isDeflectingAttack(Organism attacker) { return false; }
    public Boolean isIncreasingStrength() { return false; }
    public int getIncrease() { return 0; }
    public abstract Location chooseNewLocation(Location fromWhere);
    public Boolean isImmuneToKillingBy(Organism killer) { return false; }
    public String getName() {
        throw new RuntimeException("I don't have a name.");
    }
    public int compareTo(Organism l) {
        if (l.getInitiative() != this.getInitiative()) return (l.getInitiative() - this.getInitiative());
        else return (l.getAge() - this.getAge());
    }
}