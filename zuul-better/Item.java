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

 
 public Item(String iname, String idescription, int iweight)
 {   
     //initializing the item name, description and weight
     this.iname = iname;
     this.idescription = idescription;
     this.iweight = iweight;
     
     
 } 
 public String getIDescription()
 {
    return idescription;
 }
 public int getIWeight()
 {
  return iweight;
 }
 public String getIname()
 {
    return iname;
 }
}
