
/**
 *
 * Write a description of class Item here.
 *
 * @author Quinten de Haan
 * @author Tiemo
 * @version 2020.01.24
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
    private int weight;

    public Item(String iname, String idescription, String itype)
    {   
        //initializing the item name, description
        this.name = iname;
        this.description = idescription;
        this.type = itype;
        
        isEquipped = false;
        
        weight = 1;
        damage = 0;
        armor = 0;
        hp = 0;
        shield = 0;
    }
    
    public void equipIt()
    {
        isEquipped = true;
    }
    
    public void unEquipIt()
    {
        isEquipped = false;
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
