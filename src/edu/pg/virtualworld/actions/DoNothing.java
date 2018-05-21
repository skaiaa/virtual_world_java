package edu.pg.virtualworld.actions;

import edu.pg.virtualworld.organisms.Organism;

import java.util.Vector;

public class DoNothing extends Action {
        public Boolean isDoingNothing() { return true; }
        public Vector<Organism> kills() { return new Vector<Organism>(); }//edu.pg.virtualworld.organisms.Organism*
        };