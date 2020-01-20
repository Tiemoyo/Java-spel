import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private String name;
    private String type;
    private boolean isEquipped;
    private int damage;
    private int armor;
    private int hp;
    private int shield;

    public Item(String iname, String idescription, String itype)
    {   
        //initializing the item name, description
        this.name = iname;
        this.description = idescription;
        this.type = itype;
        
        isEquipped = false;

        damage = 0;
        armor = 0;
        hp = 0;
        shield = 0;
    }
    
    public void equipIt()
    {
        isEquipped = true;
    }
    
    public boolean getEquip()
    {
        return isEquipped;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public int getDam()
    {
        return damage;
    }

    public int getArmor()
    {
        return armor;
    }
    
    public int getShield()
    {
        return shield;
    }

    public int getHP()
    {
        return hp;
    }

    public void setDam(int dam)
    {
        damage = dam;
    }

    public void setArmor(int arm)
    {
        armor = arm;
    }
    
    public void setShield(int shld)
    {
        shield = shld;
    }

    public void setHP(int points)
    {
        hp = points;
    }
}