package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;

import java.awt.*;
import java.util.EmptyStackException;
import java.util.Vector;

public abstract class Organism implements Comparable<Organism> {
    protected Location location = new Location(-1, -1);//zeby mi getFreeAndRandomLocation dzialalo za pierwszym razem
    //vector, albo jeden komentator(komentator obserwuje organizmy, a human obserwuje swiat
    //komentator dostaje stringa z opisem akcji w akcji organizmu
    int age = 0;
    public abstract Action action(Vector<Organism>organisms);//edu.pg.virtualworld.actions.Action* edu.pg.virtualworld.organisms.Organism*
    /*bool operator< (const edu.pg.virtualworld.organisms.Organism & other) const
    {
        if(this->getInitiative() != other.getInitiative()) return (this->getInitiative() < other.getInitiative());
        else return(this->getAge() < other.getAge());
    }*/
    public Location getLocation() { return location; };
    public void setLocation(Location location1) { location = location1; };
    public int getAge() { return age; }
    public void growOlder() { age++; }
    //virtual void registerObserver(Commentator commentator)=0;
    public abstract int getInitiative();
    public abstract char getSymbol();
    public abstract int getStrength();
    public abstract Color getColor();
    public abstract void setStrength(int s);
    public abstract String getInfoForSave();
    public abstract void getStatsFromFile(String[] line);//byl stringstream&
    //bede zwracac nowe akcje, potem je kasowac(delete) w worldzie po wykonaniu akcji
    public abstract Action collision(Organism collider, Location where);//byl edu.pg.virtualworld.organisms.Organism* edu.pg.virtualworld.actions.Action*

    public Boolean isRunningAway() { return false; }
    public Boolean isDeflectingAttack(Organism attacker) { return false; }
    public Boolean isIncreasingStrength() { return false; }
    public int getIncrease() { return 0; }
    public abstract Location chooseNewLocation(Location fromWhere);
    public Boolean isImmuneToKillingBy(Organism killer) { return false; };//byl edu.pg.virtualworld.organisms.Organism*
    public String getName() {
        throw new EmptyStackException();//było throw I dont have a name
    }

    public int compareTo(Organism l) {
        if (l.getInitiative() != this.getInitiative()) return (l.getInitiative() - this.getInitiative());
        else return (l.getAge() - this.getAge());
    }

};