package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Spreading;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class SosnowskysBorscht extends Plant {
    public SosnowskysBorscht() {super(10);}
    public String getName() { return "Sosnowsky's Borscht"; }
    public char getSymbol() { return 's'; }
    public int getStrength() { return 10; }
    public Action action(Vector<Organism> organisms) {
        Random random=new Random();
        Vector<Location> spreadingLocations=new Vector<>();
        Vector<Organism> toKill=new Vector<>();
        Boolean SomeoneKilled = false;
        for (Organism l:organisms) {
                if (l instanceof Animal &&
                        (  (l.getLocation().equals(location.add(new Location(-1,0))))
                        || (l.getLocation().equals(location.add(new Location(1,0))))
                        || (l.getLocation().equals(location.add(new Location(0,-1))))
                        || (l.getLocation().equals(location.add(new Location(0,1))))
                )) {
                    toKill.add(l);
                    SomeoneKilled = true;
                    break;
                }

        }
        int chanceOfSpreading = random.nextInt(100);
        if (spreadingProbability >= chanceOfSpreading) {
            spreadingLocations.add(chooseNewLocation(getLocation()));
            return new Spreading(spreadingLocations, toKill);
        }
        if(SomeoneKilled)return new Spreading(new Vector<>(),toKill);
        else return new DoNothing();
    }
    @Override
    public Color getColor() {
        return new Color(153, 0, 0);
    }
}