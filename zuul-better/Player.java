
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
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

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        lvl = 1;
        xp = 0;
        
        baseHP = 2;
        baseINV = 1;
        
        strength = 1;
        dexterity = 1;
        endurance = 1;

        armor = 0;
        wpnDAM = 0;
    }

    public int getHP()
    {
        int HP = baseHP;
        HP += endurance;
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
    
    public void setWpnDamage(int dam)
    {
        wpnDAM = dam;
    }

    public int getXP()
    {
        return xp;
    }

    public int getlvl()
    {
        return lvl;
    }

    public int getArmor()
    {
        return armor;
    }

    public void setArmor(int value)
    {
        armor = value;
    }

    public void lvlUp(String stat)
    {
        xp = 0;
        lvl += 1;
        if(stat == "str"){
            strength += 1;
        }
        else if(stat == "dex"){
            dexterity += 1;
        }
    }
}
