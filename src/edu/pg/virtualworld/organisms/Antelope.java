package edu.pg.virtualworld.organisms;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal {
    public Antelope(){
        super(4);
        setStep(2);
    }
    public int getInitiative() { return 4; }
    public char getSymbol() { return 'A'; }
    public String getName() { return "Antelope"; }
    public Boolean isRunningAway() {
        Random random=new Random();
        if (random.nextInt(100) < 50) return true;
        else return false;
    }
    @Override
    public Color getColor() {
        return new Color(153, 102, 51);
    }
}