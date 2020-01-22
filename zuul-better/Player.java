import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Player here.
 *
 * @author Quinten de Haan
 * @version 2020.01.22
 */
public class Player
{
    // baseHP is the hitpoints a players has before modifiers
    private int baseHP;
    // baseINV is how much items a player can have before modifiers
    private int baseINV;
    private int strength;
    private int dexterity;
    private int endurance;
    private int intelligence;

    private int lvl;
    private int xp;
    private int wpnDAM;
    private int armor;
    private int shield;

    private Random randomGenerator;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        lvl = 1;
        xp = 0;

        baseHP = 1;
        baseINV = 1;

        strength = 1;
        dexterity = 1;
        endurance = 1;

        armor = 0;
        wpnDAM = 0;
        shield = 0;

        randomGenerator = new Random();
    }

    public int getTotalHP()
    {
        int HP = baseHP;
        HP += lvl;
        int endhp = endurance * 2;
        HP += endhp;
        return HP;
    }

    public int getInvSpace()
    {
        int INV = baseINV;
        INV += strength;
        INV += endurance;
        return INV;
    }

    public int getDamage()
    {
        int damage = strength;
        damage += wpnDAM;
        return damage;
    }

    public int getWpnDamage()
    {
        return wpnDAM;
    }

    public int getShield()
    {
        return shield;
    }

    public void setWpnDamage(int dam)
    {
        wpnDAM = dam;
    }

    public int getXP()
    {
        return xp;
    }

    public void addXP(int gain)
    {
        xp += gain;
    }

    public int getlvl()
    {
        return lvl;
    }

    public int getArmor()
    {
        return armor;
    }

    public int getDex()
    {
        return dexterity;
    }
    
    public int getStr()
    {
        return strength;
    }
    
    public int getEnd()
    {
        return endurance;
    }

    public void setArmor(int value)
    {
        armor = value;
    }

    public void setShield(int value)
    {
        shield = value;
    }

    public boolean hitChance()
    {
        boolean hit;
        int ran = 3;
        ran += dexterity;
        int hitchance = randomGenerator.nextInt(ran);
        if(hitchance == 0){
            hit = false;
        }
        else{
            hit = true;
        }
        return hit;
    }
    
    public boolean blockChance()
    {
        boolean blocked = false;
        int blockchance;
        if(shield == 1){
            blockchance = randomGenerator.nextInt(5);
            if(blockchance == 0){
                blocked = true;
                return blocked;
            }
            else{
                return blocked;
            }
        }
        else if(shield == 2){
            blockchance = randomGenerator.nextInt(4);
            if(blockchance == 0){
                blocked = true;
                return blocked;
            }
            else{
                return blocked;
            }
        }
        else{
            return blocked;
        }
    }

    public boolean lvlCheck()
    {
        if(lvl == 1){
            if(xp > 5){
                xp -= 5;
                return true;
            }
            else{ 
                return false;
            }
        }
        else if(lvl == 2){
            if(xp > 10){
                xp -= 10;
                return true;
            }
            else{
                return false;
            }
        }
        else if(lvl == 3){
            if(xp > 20){
                xp -= 20;
                return true;
            }
            else{
                return false;
            }
        }
        else if(lvl == 4){
            if(xp > 40){
                xp -= 40;
                return true;
            }
            else{
                return false;
            }
        }
        else if(lvl == 5){
            if(xp > 80){
                xp -= 80;
                return true;
            }
            else{
                return false;
            }
        }
        else if(lvl > 5){
            if(xp > 100){
                xp -= 100;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void lvlUp()
    {
        lvl += 1;
        int stat = randomGenerator.nextInt(3);
        String statup = null;
        if(stat == 0){
            strength += 1;
            statup = "strength";
        }
        else if(stat == 1){
            dexterity += 1;
            statup = "dexterity";
        }
        else{
            endurance += 1;
            statup = "endurance";
        }
        System.out.println("You have leveled up! You are now level " + getlvl() + ".");
        System.out.println("Your " + statup + " was increased by 1!");
    }
}
