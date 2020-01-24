import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class Fight here.
 *
 * @author Quinten de Haan
 * @version 2020.01.22
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
        int ranHP = randomGenerator.nextInt(7);
        ranHP += 3;
        enemyHP = ranHP;
        int ranDAM = randomGenerator.nextInt(3);
        ranDAM += 1;
        enemyDAM = ranDAM;
        int ranXP = randomGenerator.nextInt(5);
        ranXP += 1;
        xpGain = ranXP;
    }
    
    public boolean randomBattle()
    {
        boolean battle = false;
        int chance = randomGenerator.nextInt(5);
        if(chance == 0){
            battle = true;
            return battle;
        }
        else{
            return battle;
        }
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
        String youhit, youmiss, youwin, youlose, trainbegin, enemyhit, enemymiss, randomguard, randomrat;

        youhit = "\nYou successfully hit the enemy!";
        youmiss = "\nYou miss the enemy!";
        youwin = "\nYou stand victorious!";
        youlose = "\nYou are defeated..";
        trainbegin = "\nThe old soldier prepares for battle!";
        randomguard = "\nYou encounter a guard!";
        randomrat = "\nYou encounter a large rat!";
        enemyhit = "\nThe enemy hits you!";
        enemymiss = "\nThe enemy misses!";

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
        else if(action == "randomguard"){
            return randomguard;
        }
        else if(action == "randomrat"){
            return randomrat;
        }
        else{
            return youlose;
        }
    }
}
