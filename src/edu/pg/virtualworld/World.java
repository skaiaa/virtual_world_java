package edu.pg.virtualworld;

import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.organisms.Organism;
import sun.rmi.runtime.Log;

import java.util.Collections;
import java.util.Vector;

public class World {
    private int width, height, fields;
    public Vector<Organism> organisms;//multiset<edu.pg.virtualworld.organisms.Organism*>
    private int numberOfLogs = 0, numberOfTurns=0;
    private Vector<String> allLogs;
    private Boolean newWorldLoading = false;
    private Logger logger;

    public World(int width, int height, Logger logger){
        this.width=width;
        this.height=height;
        fields=width*height;
        this.logger=logger;
        organisms = OrganismGenerator.getInitialOrganisms(width, height);
    }
    //to zrobic w dialogu!!!
//    private void writeLegend() {
//        for (int i = 0; i < 5; i++) {
//            gotoxy(START_X_LEGEND, START_Y_LEGEND + i);
//            if (i == 0)cout << "author: Anna Przybycien 172126";
//            if (i == 1)cout << "arrows - control human, p -superpower";
//            if (i == 2)cout << "s - save game to file";
//            if (i == 3)cout << "l - load game from file";
//            if (i == 4)cout << "esc - end game";
//        }
//    }
    private void writeLog(String log) {
       logger.log(log);
    }
    private void writeLog() {
       // cout << "Turn: "<<numberOfTurns;
//moze jakas updateTurns w dialogu???
    }
    private Organism whoIsThere(Location location) {
        handleWorldsEdges(location);
        for (Organism o : organisms) {
            if (o.getLocation() == location)
                return o;
        }
        return null;
    }
    private Location handleWorldsEdges(Location location) {
        if (location.x < 0)location.x = height - 1;
        if (location.x == height)location.x = 0;
        if (location.y < 0)location.y = width - 1;
        if (location.y == width)location.y = 0;
        //tutaj dostaje lokacje i patrze czy brzegi
        //uzyc mozna tego w whoIsThere, a nawet trzeba
        return location;
    }

    public Boolean playRound() {
        //string log;
        Vector<Organism> tmpOrganisms = new Vector<>(organisms);//jak w trakcie tury dodam cos do organizmow to nie moge iterowac po czyms do czego dodaje
        //writeLegend();
        while(!tmpOrganisms.isEmpty()){
            //writeLog();
            if (newWorldLoading) {
                newWorldLoading = false;
                break;
            }
            if ((tmpOrganisms.elementAt(0)).getSymbol() == 'H')writeLog("Your turn!");
            //bool killedOneself = false;
            //MainDialog.drawBoard(width,height,this,MainDialog.labels);
            if (executeActionsAndCheckEndOfGame(tmpOrganisms.elementAt(0),
                    (tmpOrganisms.elementAt(0)).action(organisms), tmpOrganisms))return false;
        }
        Collections.sort(organisms);
        //OrganismGenerator.sort(edu.pg.virtualworld.organisms);
        MainDialog.drawBoard(width,height,this,MainDialog.labels);
        return true;
    }
    private Boolean executeActionsAndCheckEndOfGame(Organism organism, Action action, Vector<Organism> tmpOrganisms) {
        Boolean killedOneself = false;
        Boolean canSpread=true;
        if (action.isMoving()) {
            Location location = action.getMove();
            Organism organismAlreadyThere = whoIsThere(location);
            if (organismAlreadyThere == null) {
                writeLog(organism.getName() + " moving to " + location.y + " " + location.x);
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
                    writeLog(organism.getName() + " is spreading to " + location.y + " " + location.x);
                    organisms.add(newOrganism);
                }
            }
        }
        if (action.isActivatingSpecialAbility()) {
            writeLog(organism.getName() + " just activated " + action.getAbility());
        }
        tmpOrganisms.remove(0);
        if (!(action.isDoingNothing()))
            numberOfTurns++;
        if (!killedOneself && !newWorldLoading) organism.growOlder();
        return false;
    }
    //naciskanie klawiszy tez gdzie indziej
//    private Boolean manageKeysPressed(int key) {
//        switch (key) {
//            case KB_ESCAPE:
//                return true;
//            break;
//            case 's':
//                saveToFile();
//                break;
//            case 'l':
//                loadFromFile();
//                break;
//            default:
//                break;
//        }
//        return false;
//    };
    private void saveToFile() {
//        ofstream out(readNameOfFile());
//        out << width <<" "<<height<<"\n";
//        for (vector<edu.pg.virtualworld.organisms.Organism*>::iterator i = edu.pg.virtualworld.organisms.begin(); i != edu.pg.virtualworld.organisms.end(); ++i) {
//            string info= (*i)->getInfoForSave();//to_string na char zrobi decimala
//            out << (*i)->getSymbol() << " ";
//            out << info;
//        }
//        out.close();

    }
    private String readNameOfFile() {
//        writeLog("Name of file: ");
//        string nameOfFile;
//        gotoxy(START_X_LOGS, START_Y_LOGS + numberOfLogs);
//        //writeLog("");//zeby zejsc jeden w dol
//        cin >> nameOfFile;
//        nameOfFile = "C:\\Users\\aniap\\source\\repos\\swiat_wg_pana_Tomka\\" + nameOfFile + ".txt";
//        return nameOfFile;
        return new String("ech");
    }
    private void loadFromFile() {
//        ifstream newWorld(readNameOfFile());
//        if (newWorld.is_open()) {
//            newWorldLoading = true;
//            killAllOrganisms();
//            newWorld >> width >> height;
//            string line;
//            std::getline(newWorld, line);
//            edu.pg.virtualworld.organisms.Organism* newOrganism;
//            while (std::getline(newWorld, line))
//            {
//                newOrganism = organismGenerator::getOrganism(line[0]);
//                line.erase(line.begin());
//                stringstream stats(line);
//                newOrganism->getStatsFromFile(stats);
//                //sczytaj staty ze strinu line dla organizmów
//                edu.pg.virtualworld.organisms.push_back(newOrganism);
//            }
//        }
    }
    private Boolean executeCollisionsAndCheckIfKilledOneself(Organism organism,
                                                             Action collision,
                                                             Organism organismAlreadyThere,
                                                             Vector<Organism>tmpOrganisms) {
        Boolean killedOneself = false;
        if (collision.isReproducing()) {
            Location location = collision.getReproduce();//musze obsluzyc teraz boki planszy!!!
            writeLog(organism.getName() + " trying to reproduce on " + location.y + " " + location.x);
            Organism organismOnPlace = whoIsThere(location);
            if (organismOnPlace == null && organisms.size()<fields) {
                writeLog("Successfully reproduced!");
                Organism child = OrganismGenerator.getOrganism(organism.getSymbol());
                child.setLocation(location);
                organisms.add(child);
                //MainDialog.drawBoard(width,height,this,MainDialog.labels);
            }
        }
        if (collision.isFighting()) {
            writeLog(organism.getName() + " is trying to eat " + organismAlreadyThere.getName());
            killedOneself = performKillingSpree(collision.kills(), organism, organismAlreadyThere, tmpOrganisms);
            if (!killedOneself) {//tutaj cos sie nie zabija:C
                organism.setLocation(collision.getFight());
            }
        }
        if (collision.isTryingToCatchIt()) {
            writeLog(organism.getName() + " is trying to catch " + organismAlreadyThere.getName());
            Location l = collision.getCatch();
            writeLog(organismAlreadyThere.getName() + " is trying to run away to " + l.y + " " + l.x);
            Organism organismOnPlace = whoIsThere(l);
            if (organismOnPlace == null) {//rozpatrzec sukces w ucieczce
                writeLog(organismAlreadyThere.getName() + " succesfully run away!");
                organism.setLocation(organismAlreadyThere.getLocation());
                organismAlreadyThere.setLocation(collision.getCatch());
                //organism zmienia lokacje na location z getmove(tam gdzie byl organismAlreadyThere, mozna najpierw)
                //organismAlreadyThere zmienia swoja lokacje na ta z getcatch
            }
            else {//rozpatrzec porazke z ucieczka
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


    //tutaj zabijamy i sprawdzamy czy nazs organism sie nie zabil od razu
    private Boolean performKillingSpree(Vector<Organism>killed, Organism killer,
                                        Organism organismAlreadyThere, Vector<Organism> tmpOrganisms) {
        Boolean killedOneself = false;
        for (Organism victim : killed) {
            String nameOfVictim = victim.getName();
            String nameOfKiller = killer.getName();
            if (!victim.isImmuneToKillingBy(killer)) {
                if (victim.getLocation() == killer.getLocation()) {
                    killedOneself = true;
                    nameOfKiller = organismAlreadyThere.getName();
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
}
