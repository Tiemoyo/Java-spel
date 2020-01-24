import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author Quinten de Haan
 * @author Tiemo
 * @version 2020.01.23
 */

public class CommandWords
{
    // A mapping between a command word and the CommandWord
    // associated with it.

    private HashMap<String, CommandWord> validCommandsEN;
    private HashMap<String, CommandWord> validCommandsNL;
    private Parser parser;
    private Language language;


    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommandsEN = new HashMap<>();
        validCommandsNL = new HashMap<>();
        validCommandsEN.put("1", CommandWord.LANGEN);
        validCommandsEN.put("2", CommandWord.LANGNL);
        validCommandsEN.put("start", CommandWord.START);

        validCommandsEN.put("go", CommandWord.GO);
        validCommandsNL.put("ga", CommandWord.GO);

        validCommandsEN.put("help", CommandWord.HELP);

        validCommandsEN.put("quit", CommandWord.QUIT);
        validCommandsNL.put("afsluiten", CommandWord.QUIT);

        validCommandsEN.put("attack", CommandWord.ATT);
        validCommandsNL.put("sla", CommandWord.ATT);

        validCommandsEN.put("talk", CommandWord.TALK);
        validCommandsEN.put("say", CommandWord.TALK);
        validCommandsNL.put("praat", CommandWord.TALK);
        validCommandsNL.put("zeg", CommandWord.TALK);

        validCommandsEN.put("inventory", CommandWord.INV);
        validCommandsEN.put("bag", CommandWord.INV);
        validCommandsNL.put("inventaris", CommandWord.INV);
        validCommandsNL.put("tas", CommandWord.INV);

        validCommandsEN.put("equip", CommandWord.EQP);
        validCommandsNL.put("bewapen", CommandWord.EQP);

        validCommandsEN.put("take", CommandWord.TAKE);
        validCommandsNL.put("neem", CommandWord.TAKE);
        validCommandsNL.put("pak", CommandWord.TAKE);

        validCommandsEN.put("look", CommandWord.LOOK);
        validCommandsNL.put("kijk", CommandWord.LOOK);

        validCommandsEN.put("back", CommandWord.BACK);
        validCommandsNL.put("terug", CommandWord.BACK);

        validCommandsEN.put("stats", CommandWord.STAT);
        validCommandsNL.put("attributen", CommandWord.STAT);

        validCommandsEN.put("item", CommandWord.ITEM);

        validCommandsEN.put("use", CommandWord.USE);
        validCommandsNL.put("gebruik", CommandWord.USE);

        validCommandsEN.put("drop", CommandWord.DROP);
        validCommandsNL.put("neerzetten", CommandWord.DROP);
        validCommandsEN.put("about", CommandWord.ABOUT);
    }

    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command;
        
        switch (language){
            case ENGLISH:
            command = validCommandsEN.get(commandWord);
            break;
            case NEDERLANDS:
            command = validCommandsNL.get(commandWord);
            break;
            default:
            command = validCommandsEN.get(commandWord);
        }

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
        switch(language){
            case ENGLISH:
            return validCommandsEN.containsKey(aString);
            case NEDERLANDS:
            return validCommandsNL.containsKey(aString);
            default:
            return validCommandsEN.containsKey(aString);
        }
    }
    
    public void setLang(Language lang) 
    {
        language = lang;
    }

    /**
     * Print all valid commands to System.out.
     */
    /*
    public void showAll() 
    {

    for(String command : validCommands.keySet()) {
    System.out.print(command + "  ");
    }
    System.out.println();
    }
     */
}
