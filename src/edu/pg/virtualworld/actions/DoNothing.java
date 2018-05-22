package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class DoNothing extends Action {
        public Vector<Organism> kills() { return new Vector<>(); }
}