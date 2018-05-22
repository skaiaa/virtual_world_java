package edu.pg.virtualworld;

import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.organisms.Human;
import edu.pg.virtualworld.organisms.Organism;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class World {
    private int width, height, fields;
    public Vector<Organism> organisms;//multiset<edu.pg.virtualworld.organisms.Organism*>
    private int numberOfLogs = 0, numberOfTurns=0;
    private Vector<String> allLogs;
    private Logger logger;

    public World(int width, int height, Logger logger){
        this.width=width;
        this.height=height;
        fields=width*height;
        this.logger=logger;
        organisms = OrganismGenerator.getInitialOrganisms(width, height);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }

    private void writeLog(String log) {
       logger.log(log);
    }
    public Organism whoIsThere(Location location) {
        handleWorldsEdges(location);
        for (Organism o : organisms) {
            if (o.getLocation().equals(location))
                return o;
        }
        return null;
    }
    private Location handleWorldsEdges(Location location) {
        if (location.x < 0)location.x = height - 1;
        if (location.x == height)location.x = 0;
        if (location.y < 0)location.y = width - 1;
        if (location.y == width)location.y = 0;
        return location;
    }

    public Boolean playRound() {
        Vector<Organism> tmpOrganisms = new Vector<>(organisms);//jak w trakcie tury dodam cos do organizmow to nie moge iterowac po czyms do czego dodaje
        while(!tmpOrganisms.isEmpty()){
            //if ((tmpOrganisms.elementAt(0)).getSymbol() == 'H')writeLog("Your turn!");
            if (executeActionsAndCheckEndOfGame(tmpOrganisms.elementAt(0),
                    (tmpOrganisms.elementAt(0)).action(organisms), tmpOrganisms))return false;
        }
        Collections.sort(organisms);
        return true;
    }
    private Boolean executeActionsAndCheckEndOfGame(Organism organism, Action action, Vector<Organism> tmpOrganisms) {
        Boolean killedOneself = false;
        Boolean canSpread=true;
        if (action.isMoving()) {
            Location location = action.getMove();
            Organism organismAlreadyThere = whoIsThere(location);
            if (organismAlreadyThere == null) {
                //writeLog(organism.getName() + " moving to " + location.y + " " + location.x);
                organism.setLocation(location);
            }
            else {//kolizje
                killedOneself = executeCollisionsAndCheckIfKilledOneself(organism,
                        organism.collision(organismAlreadyThere, location), organismAlreadyThere, tmpOrganisms);
            }
        }
        if (action.isSpreading()) {
            Vector<Location> spread = action.getSpread();
            for (Location l : spread) {
                Location location = l;
                Organism organismAlreadyThere = whoIsThere(location);
                //writeLog(organism->getName() + " trying to spread to " + to_string(location.y) + " " + to_string(location.x));
                if (organismAlreadyThere == null && organisms.size()<fields) {
                    Organism newOrganism = OrganismGenerator.getOrganism(organism.getSymbol());
                    newOrganism.setLocation(location);
                    //writeLog(organism.getName() + " is spreading to " + location.y + " " + location.x);
                    organisms.add(newOrganism);
                }
            }
            if(!action.kills().isEmpty()) performKillingSpree(action.kills(),organism,null,tmpOrganisms);
            //performKillingSpree(collision.kills(), organism, organismAlreadyThere, tmpOrganisms);

        }
        if (action.isActivatingSpecialAbility()) {
            writeLog(organism.getName() + " just activated " + action.getAbility());
        }
        tmpOrganisms.remove(0);
        if (!(action.isDoingNothing()))
            numberOfTurns++;
        if (!killedOneself) organism.growOlder();
        return false;
    }

    public void saveToFile(FileOutputStream os) {
        PrintWriter p = new PrintWriter(os);
        p.println(width + " " + height);
        for (Organism o : organisms) {
            String info = o.getInfoForSave();//to_string na char zrobi decimala
            p.println(o.getSymbol() + " " + info);
        }
        p.close();

    }

    public void loadFromFile(FileInputStream is) {
        //newWorldLoading = true;
        killAllOrganisms();
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String[] dimensions= new String[0];
        try {
            dimensions = r.readLine().split(" ");
        } catch (IOException e) {
            logger.log("Cannot read dimensions!");
        }
        width=Integer.parseInt(dimensions[0]);
        height=Integer.parseInt(dimensions[1]);

        String line = null;
        try {
            line = r.readLine();
            while(line!=null){
                if(line.isEmpty()){
                    line=r.readLine();
                    continue;
                }
                String[] stats=line.split(" ");
                Organism o=OrganismGenerator.getOrganism(stats[0].charAt(0));
                o.getStatsFromFile(Arrays.copyOfRange(stats,1,stats.length));
                organisms.add(o);
                line=r.readLine();
            }
        } catch (IOException e) {
            logger.log("Cannot read organisms!");
        }
        catch (StringIndexOutOfBoundsException e){
            logger.log(":C");
        }
    }
    private Boolean executeCollisionsAndCheckIfKilledOneself(Organism organism,
                                                             Action collision,
                                                             Organism organismAlreadyThere,
                                                             Vector<Organism>tmpOrganisms) {
        Boolean killedOneself = false;
        if (collision.isReproducing()) {
            Location location = collision.getReproduce();//musze obsluzyc teraz boki planszy!!!
            //writeLog(organism.getName() + " trying to reproduce on " + location.y + " " + location.x);
            Organism organismOnPlace = whoIsThere(location);
            if (organismOnPlace == null && organisms.size()<fields) {
                writeLog("Successfully reproduced!");
                Organism child = OrganismGenerator.getOrganism(organism.getSymbol());
                child.setLocation(location);
                organisms.add(child);
            }
        }
        if (collision.isFighting()) {
            //writeLog(organism.getName() + " is trying to eat " + organismAlreadyThere.getName());
            killedOneself = performKillingSpree(collision.kills(), organism, organismAlreadyThere, tmpOrganisms);
            if (!killedOneself) {
                organism.setLocation(collision.getFight());
            }
        }
        if (collision.isTryingToCatchIt()) {
            writeLog(organism.getName() + " is trying to catch " + organismAlreadyThere.getName());
            Location l = collision.getCatch();
            //writeLog(organismAlreadyThere.getName() + " is trying to run away to " + l.y + " " + l.x);
            Organism organismOnPlace = whoIsThere(l);
            if (organismOnPlace == null) {//rozpatrzec sukces w ucieczce
                writeLog(organismAlreadyThere.getName() + " succesfully run away!");
                organism.setLocation(organismAlreadyThere.getLocation());
                organismAlreadyThere.setLocation(collision.getCatch());
                //organism zmienia lokacje na location z getmove(tam gdzie byl organismAlreadyThere, mozna najpierw)
                //organismAlreadyThere zmienia swoja lokacje na ta z getcatch
            }
            else {
                writeLog(organismAlreadyThere.getName() + " didn't manage to run away!");
                Location possibleLocation = organismAlreadyThere.getLocation();
                killedOneself = performKillingSpree(collision.kills(), organism, organismAlreadyThere, tmpOrganisms);
                if (!killedOneself) {
                    organism.setLocation(possibleLocation);
                }
                //jesli zabity jest organism atakowany to zmienic lokalizacje atakujacego na tego organismAlreadyThere
                //jesli zabity jest organism atakujacy to go zabic
            }
        }
        return killedOneself;
    }


    //tutaj zabijamy i sprawdzamy czy nasz organism sie nie zabil od razu
    private Boolean performKillingSpree(Vector<Organism>killed, Organism killer,
                                        Organism organismAlreadyThere, Vector<Organism> tmpOrganisms) {
        Boolean killedOneself = false;
        for (Organism victim : killed) {
            String nameOfVictim = victim.getName();
            String nameOfKiller = killer.getName();
            if (!victim.isImmuneToKillingBy(killer)) {
                if (victim.getLocation() == killer.getLocation()) {
                    killedOneself = true;
                    if(organismAlreadyThere!=null) nameOfKiller = organismAlreadyThere.getName();
                }
                if (victim.isIncreasingStrength()) {
                    killer.setStrength(killer.getStrength() + victim.getIncrease());
                    writeLog(nameOfVictim + " increased strength of " + nameOfKiller + " by " + victim.getIncrease());
                }
                int positionInRound = getPositionInVector(victim, tmpOrganisms);
                int positionInWorld = getPositionInVector(victim, organisms);
                if(positionInRound>-1)tmpOrganisms.remove(positionInRound);
                organisms.remove(positionInWorld);
            }
            writeLog(nameOfVictim  + " was eaten by " + nameOfKiller);
        }
        return killedOneself;
    }
    private int getPositionInVector(Organism victim, Vector<Organism> organisms) {
        int position = 0;
        for (Organism o : organisms) {
            if (o.getLocation() == victim.getLocation()) {
                return position;
            }
            position++;
        }
        return -1;//nie ma go w tym vectorze
    }
    private void killAllOrganisms() {
        organisms.clear();
    }
    public Human getHuman(){
        for(Organism o :organisms){
            if(o instanceof Human)return (Human)o;
        }
        return null;
    }
    public void createNewWorld(){
        killAllOrganisms();
        organisms = OrganismGenerator.getInitialOrganisms(width, height);
    }
}
