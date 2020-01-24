import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Write a description of class Convo here.
 *
 * @author Quinten de Haan
 * @version 2020.01.22
 */
public class Convo
{
    // instance variables - replace the example below with your own
    //private Convo convoType;
    private String content;
    private HashMap<String, Convo> links; // stores dialogue links
    /**
     * Constructor for objects of class Convo
     */
    public Convo(String content)
    {
        this.content = content;
        
        links = new HashMap<String, Convo>();
    }
    public Convo getLink(String link) 
    {
        return links.get(link);
    }
    public void setLink(String link, Convo topic) 
    {
        links.put(link, topic);
    }
    public String getContent()
    {
        return content; //+ getResponseString();
    }
    private String getResponseString()
    {
        String returnString = "\nResponses:";
        Set<String> keys = links.keySet();
        for(String link : keys) {
            returnString += " " + link;
        }
        return returnString;
    }
}
