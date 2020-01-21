import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class Fight here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fight
{
    // instance variables - replace the example below with your own
    private Random randomGenerator;
    private int enemyHP;
    private int xpGain;
    private int enemyDAM;
    private int turnNmbr;
    private Player player;

    /**
     * Constructor for objects of class Fight
     */
    public Fight()
    {
        enemyHP = 0;
        enemyDAM = 0;
        turnNmbr = 0;
        xpGain = 0;
        randomGenerator = new Random();
    }

    public void lowRandomize()
    {
        // nothing yet
    }

    public void setEnemyHP(int hp)
    {
        enemyHP = hp;
    }
    
    public void setXPGain(int xp)
    {
        xpGain = xp;
    }

    public void dealDamage(int hp)
    {
        enemyHP -= hp;
    }

    public void setEnemyDam(int dam)
    {
        enemyDAM = dam;
    }

    public void addTurn()
    {
        turnNmbr += 1;
    }

    public int getEnemyHP()
    {
        return enemyHP;
    }
    
    public int getXPGain()
    {
        xpGain += turnNmbr;
        return xpGain;
    }

    public int getEnemyDam()
    {
        return enemyDAM;
    }

    public int getTurn()
    {
        return turnNmbr;
    }
    
    public void resetTurn()
    {
        turnNmbr = 0;
    }

    public boolean enemyHitChance()
    {
        int hitchance = randomGenerator.nextInt(4);
        boolean hit;
        if(hitchance == 0){
            hit = false;
        }
        else{
            hit = true;
        }
        return hit;
    }
    /*
    public boolean playerHitChance()
    {
        boolean hit;
        int ran = 3;
        int dex = player.getDex();
        ran += dex;
        int hitchance = randomGenerator.nextInt(ran);
        if(hitchance == 0){
            hit = false;
        }
        else{
            hit = true;
        }
        return hit;
    }
    */
    public void startFight()
    {

    }

    public String getAction(String action)
    {
        String youhit, youmiss, youwin, youlose, trainbegin, enemyhit, enemymiss;

        youhit = "\nYou successfully hit the enemy!";
        youmiss = "\nYou miss the enemy!";
        youwin = "\nYou stand victorious!";
        youlose = "\nYou are defeated..";
        trainbegin = "\nThe old soldier prepares for battle!";
        enemyhit = "\nThe enemy hits you!";
        enemymiss = "\nThe enemy misses!.";

        if(action == "youhit"){
            return youhit;
        }
        else if(action == "youmiss"){
            return youmiss;
        }
        else if(action == "youwin"){
            return youwin;
        }
        else if(action == "trainbegin"){
            return trainbegin;
        }
        else if(action == "enemyhit"){
            return enemyhit;
        }
        else if(action == "enemymiss"){
            return enemymiss;
        }
        else{
            return youlose;
        }
    }
}
