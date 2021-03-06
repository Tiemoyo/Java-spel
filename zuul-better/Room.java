import java.util.Set;
import java.util.HashMap;

import java.util.Iterator;
import java.util.ArrayList;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author Quinten de Haan
 * @author Tiemo
 * @version 2020.01.22
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;// stores exits of this room.
    public HashMap<String, Item> items;
    private boolean hasConvo;
    private boolean isLocked;
    private boolean hasBattle;
    private String convotype;

    private String battletype;


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new HashMap<String, Item>();
        hasConvo = false;

        isLocked = false;
    }


    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public void isLocked()
    {
        isLocked = true;
    }
    
    public void isUnlocked()
    {
        isLocked = false;
    }
    
    public boolean getLock()
    {
        return isLocked;
    }

    public void addItem(String string, Item item)
    {
        items.put(string, item);
    }

    public String getItems()
    {
        if (items.size() == 0) {
            return "There are no items here.";    
        }
        else{
            String string = "The following items are here:";
            for (String i : items.keySet()) {
                Item item = items.get(i);
                string += "\n" + item.getName();
            }
            return string;
        }
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    public void setDescription(String des)
    {
        description = des;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "\n" + "Exits:" + "\n";
        Set<String> keys = exits.keySet();
        
        
        return returnString;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    { 
        return description + getExitString();
    } 

    /** nh
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public boolean getConvo()
    {
        return hasConvo;
    }

    public String getConvoType()
    {
        return convotype;
    }

    public void setConvo(String type)
    {
        hasConvo = true;
        convotype = type;
    }

    
    public void unsetConvo()
    {
        hasConvo = false;
    }
    
    public boolean getBattle()
    {
        return hasBattle;
    }

    public String getBattleType()
    {
        return battletype;
    }

    public void setBattle(String type)
    {
        hasBattle = true;
        battletype = type;
    }

}
