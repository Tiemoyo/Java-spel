import java.util.ArrayList;
import java.util.HashMap;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class lang
{
    private Parser parser;
    private Room currentRoom;
    private boolean inConvo;
    private ArrayList<Item> inventory;
    private HashMap<CommandLang, CommandLang> command = new HashMap<CommandLang, CommandLang>();
   
    /**
     * Create the game and initialise its internal map.
     */
    public lang() 
    {
        parser = new Parser();
        inConvo = false;
        inventory = new ArrayList<Item>();
        
        createRooms();
    }
   
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {   
        //language switch
        
       
        
       
        
        
        
        
        Room language, outside, theater, libary, hall, cellar, castle,
        second_floor, path, dining_room, bedroom, stairs, piano_room,
        basement, chamber,jail, kitchen, training_ground,fight, end;
      
        // create the rooms
        language = new Room("Choose your language: ");
        outside = new Room("U staat voor een duister kasteel. In het noorden is de kasteelpoort, naar het westen is het oefenterrein.");
        training_ground = new Room("U bent op het oefenterrein van het kasteel. Er is een oude soldaat die zijn uitrusting verzorgt. Terug naar het oosten is de kasteelpoort.");
        castle = new Room("Je bevindt je in een donker kasteel en er zijn drie paden om uit te kiezen ...");
        libary = new Room("Dit is de oude kasteelbibliotheek, de meeste boeken vallen bijna uit elkaar");
        hall = new Room("Dit is de hal van het kasteel");
        kitchen = new Room("Je bent in de keuken. De geur hier is walgelijk");
        second_floor = new Room("Er zijn hier meer kamers");
        path = new Room("Je bevindt je op een klein donker pad");
        dining_room = new Room("U bevindt zich in de eetkamer");
        bedroom = new Room("Je bent in de slaapkamer van de oude koning en koningin, hier is misschien iets nuttigs");
        stairs = new Room("Het wordt hier donkerder en donkerder");
        piano_room = new Room("Er is niks in deze kamer... alleen een oude piano");
        basement = new Room ("Dit is de kelder, het is een zeer donkere plaats ... zachte geluiden zijn te horen");
        chamber = new Room("Dit is een ondergrondse kamer. Er is een schatkist in het midden, maar een monster beschermt het. Wil je ertegen vechten?");
        jail = new Room("Dit is de gevangenis. Gewoon wat schedels die in de kamer liggen");
        fight = new Room("Wil je de draak bestrijden?"); //Moet eerst het zwaard gehaald hebben uit de bedroom.
        end = new Room("Je versloeg de draak en kreeg de schat");
        // initialise room exits
        outside.setExit("noord", castle);
        outside.setExit("west", outside);
        
        training_ground.setExit("west", outside);
        training_ground.setConvo("trainer");
        
        castle.setExit("links", hall);
        castle.setExit("rechts", libary);
        castle.setExit("omhoog", second_floor);
        castle.setExit("omlaag", path);

        libary.setExit("noord", dining_room);
        libary.setExit("west", castle);
        
        dining_room.setExit("west", hall);
        dining_room.setExit("zuid", kitchen);
        
        path.setExit("links", stairs);
        path.setExit("rechts", piano_room);
        
        stairs.setExit("omlaag", basement);
        
        basement.setExit("links", chamber);
        basement.setExit("rechts", jail); 
        
        chamber.setExit("vechten", fight );
        
        
        second_floor.setExit("omlaag", castle);
        second_floor.setExit("links", bedroom);
        
        //09-01-2020 laatst veranderd.
        //Beginnen met documentatie bijhouden

        currentRoom = outside;  // start game outside
        
        inventory.add(new Item("eten", "",1));
        inventory.add(new Item("water", "",1));
        
        
        
    }
    
   
    public void storeConvos()
    {
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Bedankt voor het spelen. Tot ziens!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welkom in de wereld van Zuul!");
        System.out.println("World of Zuul is a nieuw avonturenspel.");
        System.out.println("Type 'help' als je hulp nodig hebt");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        if(command.isUnknown()) {
            System.out.println("Ik begrijp niet wat u bedoeld.");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("kies")){
            goRoom(command);
        }
        else if (commandWord.equals("ga")) {
            goRoom(command);
        }
        else if (commandWord.equals("kijk")){
            look();
        }
        else if (commandWord.equals("stop")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("praat")) {
            talk();
        }
        else if (commandWord.equals("tas")){
           printInventory();
        }
        else if (command.equals("pak")){          
            //take(); werkt nog niet
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Je bent verdwaald. Je bent alleen. Jij dwaalt rond");
        System.out.println("rond in een kasteel om middernacht.");
        System.out.println();
        System.out.println("Uw commands zijn: ");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ga waarheen?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("U kan hier niet heen");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }
      /**
     * "printInventory prints out the inventory"
     */
    private void printInventory() {
        if (inventory.size() == 0) {
            System.out.println("U draagt niks met zich mee");
        } else {
            System.out.print("U heeft de volgende spullen:");
            for (int n = 0; n < inventory.size(); n++) {
                Item item = inventory.get(n);
                System.out.print("\n" + " " + item.getIname() + "\n");
            }
            
        }
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Stop ermee?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }                                                                                      
    }
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    private void talk()
    {       
        if(currentRoom.getConvo() == false){
            System.out.println("U praat tegen tegen uzelf.");
        }
        else{
            inConvo = true;
        }
    }
    private void take(Room bedroom)
    {   
      
      
      if(currentRoom == bedroom){
        System.out.println("U heeft een zwaard bemachtigd" + "\n");
          System.out.println("Het zwaard is toegevoegd aan uw tas" + "\n");
          inventory.add(new Item("zwaard", "scherp", 30));
       
        }
    }
}