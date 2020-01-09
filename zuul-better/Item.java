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
 private String idescription;
 private int iweight;
 private String iname;
 private HashMap<String, Item> itemList;
 
 public Item(String iname, String idescription, int iweight)
 {   
     
     this.iname = iname;
     this.idescription = idescription;
     this.iweight = iweight;
     itemList = new HashMap<String, Item>();
     
} 
public String getIDescription()
{
    return idescription;
}
public int getIWeight()
{
 return iweight;
}
}
