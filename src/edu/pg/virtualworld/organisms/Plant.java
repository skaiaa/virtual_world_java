package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Spreading;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public abstract class Plant extends Organism {
    protected int spreadingProbability;

    Plant(int Probability) {
        spreadingProbability = Probability;
    }

    public int getInitiative() {
        return 0;
    }

    public int getStrength() {
        return 0;
    }

    public void setStrength(int s) {
    }

    public String getInfoForSave() {
        return age + " " + spreadingProbability + " " + getLocation().x + " " + getLocation().y + "\n";
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    public void getStatsFromFile(String[] line) {
        age = Integer.parseInt(line[0]);
        spreadingProbability = Integer.parseInt(line[1]);
        location.x = Integer.parseInt(line[2]);
        location.y = Integer.parseInt(line[3]);
    }

    public Location chooseNewLocation(Location fromWhere) {
        Random random = new Random();
        Location changeInLocation = new Location(0, 0);
        while ((changeInLocation.x == 0) && (changeInLocation.y == 0)
                && (getLocation().equals(fromWhere.add(changeInLocation)))) {//nie wylosuje miejsca gdzie stoje, kiedy rozmnazam
            changeInLocation.changeTo(random.nextInt(3) - 1, (random.nextInt(3) - 1));
        }
        return fromWhere.add(changeInLocation);
    }

    public Action action(Vector<Organism> organisms) {
        Random random = new Random();
        Vector<Location> spreadingLocations = new Vector<>();
        int chanceOfSpreading = (random.nextInt(100));
        spreadingLocations.add(chooseNewLocation(getLocation()));
        if (spreadingProbability >= chanceOfSpreading) {
            return new Spreading(spreadingLocations, new Vector<>());
        }
        return new DoNothing();
    }

    public Action collision(Organism collider, Location where) {
        return new DoNothing();
    }
}