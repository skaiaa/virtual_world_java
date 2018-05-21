package edu.pg.virtualworld.organisms;

import edu.pg.virtualworld.Location;
import edu.pg.virtualworld.actions.Action;
import edu.pg.virtualworld.actions.ActivatingSpecialAbility;
import edu.pg.virtualworld.actions.DoNothing;
import edu.pg.virtualworld.actions.Moving;

import java.awt.event.KeyEvent;
import java.util.Vector;

public class Human extends Animal{
    protected Location myMove=new Location(0,0);
    protected int magicPotion=0;
    protected int usingSpecialAbility=0;
    protected Boolean waitingForArrow=false;
    protected String specialAbilityName="magic potion";
    public Human() {super(5);}
    public String getName(){return"Tomek";}
    public int getInitiative() {return 4;}
    public char getSymbol(){return'H';}
    public String getInfoForSave(){
        return age+" "+step+
                " "+strength+" "+getLocation().x+" "+getLocation().y+
                " "+magicPotion+" "+usingSpecialAbility+" "+waitingForArrow+"\n";
    }
    public void getStatsFromFile(String[] line){
        super.getStatsFromFile(line);
        magicPotion=Integer.parseInt(line[5]);
        usingSpecialAbility=Integer.parseInt(line[6]);
        //jeszcze waiting for arrow
        //std::cout << "Found integer: " << n << "\n";
    }
    public int getStrength(){return strength+magicPotion;}
    public Action action(Vector<Organism> organisms){
        //keyTyped(new KeyEvent());
        //if (waitingForArrow)keyPressed(_getch());
        if (!myMove.equals(new Location(0, 0))) return new Moving(getLocation().add(myMove), new Vector<>());
        else if (magicPotion == 5) return new ActivatingSpecialAbility(specialAbilityName, new Vector<>());
        else return new DoNothing();
    }
    public void growOlder(){
        if (magicPotion > 0) magicPotion--;
        if ((usingSpecialAbility > 0) && (usingSpecialAbility<12))usingSpecialAbility++;
        if (usingSpecialAbility == 12)usingSpecialAbility = -1;
        age++;
    }
    public void keyTyped(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_P:
                if ((usingSpecialAbility == 0)){
                    magicPotion = 5;
                    usingSpecialAbility = 1;
                }
                break;
            case KeyEvent.VK_UP:
                myMove = new Location(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                myMove = new Location(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                myMove = new Location(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                myMove = new Location(0, 1);
                break;
            default:
                break;

        }
    }
}