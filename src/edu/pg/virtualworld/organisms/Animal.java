package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.*;

import java.util.Random;
import java.util.Vector;

public abstract class Animal extends Organism {
    int step = 1, strength;

    Animal(int initialStrength) {
        strength = initialStrength;
    }

    void setStep(int newStep) {
        step = newStep;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int s) {
        strength = s;
    }

    public String getInfoForSave() {
        return age + " " + step + " " + strength + " " + getLocation().x + " " + getLocation().y + "\n";
    }

    public void getStatsFromFile(String[] line) {
        age = Integer.parseInt(line[0]);
        step = Integer.parseInt(line[1]);
        strength = Integer.parseInt(line[2]);
        location.x = Integer.parseInt(line[3]);
        location.y = Integer.parseInt(line[4]);
    }

    public Location chooseNewLocation(Location fromWhere) {
        Random random = new Random();
        Location changeInLocation = new Location(0, 0);
        while ((changeInLocation.x == 0) && (changeInLocation.y == 0)
                || (getLocation().equals(fromWhere.add(changeInLocation)))) {//nie wylosuje miejsca gdzie stoje, kiedy rozmnazam
            if (random.nextBoolean())
                changeInLocation.changeTo((random.nextInt(step) + 1) * (random.nextBoolean() ? 1 : -1), 0);
            else changeInLocation.changeTo(0, (random.nextInt(step) + 1) * (random.nextBoolean() ? 1 : -1));
        }
        return new Location(fromWhere.add(changeInLocation));
    }

    public Action action(Vector<Organism> organisms) {
        return new Moving(chooseNewLocation(getLocation()), new Vector<>());
    }

    public Action collision(Organism withOrganism, Location place) {
        Vector<Organism> toKill = new Vector<>();
        if (withOrganism.getSymbol() == getSymbol()) return new Reproducing(chooseNewLocation(place));
        else {
            if (withOrganism.getStrength() <= getStrength()) {
                toKill.add(withOrganism);
            } else {
                toKill.add(this);//przegral i sam sie zabija:(
            }
            if (withOrganism.isDeflectingAttack(this)) {
                return new DoNothing();
            }
            if (withOrganism.isRunningAway()) {
                return new TryingToCatchIt(withOrganism.chooseNewLocation(place), toKill);
            }
            return new Fighting(place, toKill);
        }
    }
}
