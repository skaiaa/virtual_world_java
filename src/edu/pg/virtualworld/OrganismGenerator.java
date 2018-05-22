package edu.pg.virtualworld;

import edu.pg.virtualworld.organisms.*;

import java.util.*;

public class OrganismGenerator {
    public static Location getFreeAndRandomLocation(Vector<Organism> organisms, int width, int height) {
        if (organisms.size() >= width * height) throw new EmptyStackException();//throw exception("There's no place!");
        Location randomLocation = Location.getRandomLocation(width, height);
        for (Organism i :organisms) {
            if (i.getLocation().equals(randomLocation)) return getFreeAndRandomLocation(organisms, width, height);
        }
        return randomLocation;
    }

    public static Vector<Organism> getInitialOrganisms(int width, int height) {
        String allSymbols = "ggggduuuuusbAAATTTFFF";//bardzo wazny string
        Vector<Organism> initialOrganisms=new Vector<>();
        for (int i = 0; i < allSymbols.length(); i++) {//tutaj robie po jednym kazdego rodzaju
            initialOrganisms.add(getOrganism(allSymbols.charAt(i)));
        }
        int k;//tutaj sa losowe zwierzaki
//        for (int i = 0; i < 0.2*width*height-allSymbols.length(); i++) {
//            k= (int)Math.random() * allSymbols.length();
//            initialOrganisms.add(getOrganism(allSymbols.charAt(k)));
//        }
        initialOrganisms.add(getOrganism('H'));//dodanie czlowieka na koniec
        for (Organism i:initialOrganisms) {
            i.setLocation(getFreeAndRandomLocation(initialOrganisms, width, height));
        }
        Collections.sort(initialOrganisms);
        return initialOrganisms;
    }

    public static Organism getOrganism(char symbol) {
        switch (symbol) {
            case 'W':
                return new Wolf();
            case 'S':
                return new Sheep();
            case 'F':
                return new Fox();
            case 'T':
                return new Turtle();
            case 'A':
                return new Antelope();
            case 'H':
                return new Human();
            case 'g':
                return new Grass();
            case 'd':
                return new Dandelion();
            case 'b':
                return new Belladonna();
            case 's':
                return new SosnowskysBorscht();
            case 'u':
                return new Guarana();
            default:
                return new Grass();//jak sie zaden symbol nie zgadza to rośnie trawa
        }

    }
}