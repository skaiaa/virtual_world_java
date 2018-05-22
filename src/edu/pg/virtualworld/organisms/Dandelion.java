package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Spreading;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Dandelion extends Plant {
    public Dandelion() {
        super(70);
    }

    public String getName() {
        return "Dandelion";
    }

    public char getSymbol() {
        return 'd';
    }

    public Action action(Vector<Organism> organisms) {
        Random random = new Random();
        Vector<Location> spreadingLocations = new Vector<>();
        int chanceOfSpreading = random.nextInt(100);
        if (spreadingProbability >= chanceOfSpreading) {
            for (int i = 0; i < 2; i++) spreadingLocations.add(chooseNewLocation(getLocation()));
            return new Spreading(spreadingLocations, new Vector<>());
        }
        return new DoNothing();
    }

    @Override
    public Color getColor() {
        return new Color(255, 255, 102);
    }
}