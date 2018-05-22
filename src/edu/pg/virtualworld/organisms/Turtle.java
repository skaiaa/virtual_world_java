package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Moving;

import java.awt.*;
import java.util.Random;
import java.util.Vector;
public class Turtle extends Animal {
    private int probabilityToMove = 25;
    public Turtle() { super(2);}
    public String getName() { return "Turtle"; }
    public int getInitiative() { return 1; }
    public char getSymbol() { return 'T'; }
    public String getInfoForSave() {
        return age + " " + step +
        " " + strength + " " + getLocation().x +
        " " + getLocation().y+" "+probabilityToMove+"\n";
    }
    public void getStatsFromFile(String[] line) {
        super.getStatsFromFile(line);
        probabilityToMove=Integer.parseInt(line[5]);
    }
    public Action action(Vector<Organism>organisms){
        Random random=new Random();
        Location newLocation = chooseNewLocation(getLocation());
        if(random.nextInt(100)<probabilityToMove)return new Moving(newLocation, new Vector<>());
        else return new DoNothing();
    }
    public Boolean isDeflectingAttack(Organism attacker) {
        return(attacker.getStrength() < 5);
        }
    @Override
    public Color getColor() {
        return new Color(0, 102, 0);
    }
}

