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
    private int enemyDAM;
    private int turnNmbr;

    /**
     * Constructor for objects of class Fight
     */
    public Fight()
    {
        enemyHP = 0;
        enemyDAM = 0;
        turnNmbr = 0;
        randomGenerator = new Random();
    }
    public void lowRandomize()
    {
        
    }
    public void setEnemyHP(int hp)
    {
        enemyHP = hp;
    }
    public void setEnemyDAM(int dam)
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
    public int getEnemyDAM()
    {
        return enemyDAM;
    }
    public int getTurn()
    {
        return turnNmbr;
    }
    public boolean hit()
    {
        int hitchance = randomGenerator.nextInt(4);
        boolean hit = true;
        if(hitchance == 0){
            hit = false;
        }
        return hit;
    }
}
