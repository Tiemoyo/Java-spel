import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    // A mapping between a command word and the CommandWord
    // associated with it.
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("start", CommandWord.START);
        
        validCommands.put("go", CommandWord.GO);
        validCommands.put("ga", CommandWord.GO);
        
        validCommands.put("help", CommandWord.HELP);
        
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("afsluiten", CommandWord.QUIT);
        
        validCommands.put("attack", CommandWord.ATT);
        validCommands.put("sla", CommandWord.ATT);
        
        validCommands.put("talk", CommandWord.TALK);
        validCommands.put("say", CommandWord.TALK);
        validCommands.put("praat", CommandWord.TALK);
        validCommands.put("zeg", CommandWord.TALK);
        
        validCommands.put("inventory", CommandWord.INV);
        validCommands.put("bag", CommandWord.INV);
        validCommands.put("inventaris", CommandWord.INV);
        validCommands.put("tas", CommandWord.INV);
        
        validCommands.put("equip", CommandWord.EQP);
        validCommands.put("bewapen", CommandWord.EQP);
        
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("pick up", CommandWord.TAKE);
        validCommands.put("neem", CommandWord.TAKE);
        validCommands.put("pak", CommandWord.TAKE);
        
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("kijk", CommandWord.LOOK);
    }

    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}