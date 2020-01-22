
import java.util.*;

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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean inConvo;
    
   
  ArrayList<Item> inventory = new ArrayList<Item>();
   
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        inConvo = false;
       
     
        
        createRoom();
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRoom()
    {
        Room language, outside,buiten, bibliotheek, libary, hall, hal, castle, kasteel,
        second_floor, tweede_verdieping, path, pad, dining_room, eetkamer,slaapkamer, bedroom, stairs, piano_room,
        basement,kelder, chamber, kerker,jail, gevangenis, kitchen, keuken, training_ground, oefenterrein,fight, vechten, einde, end
        ,trappen, piano_kamer, fightv2, fight2;
      
        // create the rooms
         language = new Room("Type 'choose' + the language you wish to play the game in. You can choose between: ");
        outside = new Room("You stand before the intimidating dark castle. To the north is the castle gate, to the west is a training ground.");       
        training_ground = new Room("You are in a training ground next to the castle. There is an old soldier tending to his equipment. Back to the east is the castle gate.");
        castle = new Room("You are in a dark castle, and there three paths to choose from...");
        libary = new Room("This is the old castle libary, most books are barely holding together");
        hall = new Room("This is the hall of the castle");
        kitchen = new Room("You are in the kitchen. The smell here is disgusting");
        second_floor = new Room("There are more rooms here");
        path = new Room("You are in a small dark path");
        dining_room = new Room("You are in the dining room");
        bedroom = new Room("You are in the bedroom of the old king and queen, there might be something useful here");
        stairs = new Room("it's getting darker and darker");
        piano_room = new Room("Nothing is in this room... just an old piano");
        basement = new Room ("This is the basement, it is a very dark place... soft sounds can be heard");
        chamber = new Room("This is an underground chamber. There is a treasure chest in the middle, but a monster is protecting it. Want to fight it?");
        jail = new Room("This is the jail. Just some skulls lying around the room");
        fight = new Room("Do you want to fight the dragon?"); //Moet eerst het zwaard gehaald hebben uit de bedroom.
        fightv2 = new Room("You need to obtain a sword first!");
        
        buiten = new Room("U staat voor een duister kasteel. In het noorden is de kasteelpoort, naar het westen is het oefenterrein.");

        oefenterrein = new Room("U bent op het oefenterrein van het kasteel. Er is een oude soldaat die zijn uitrusting verzorgt. Terug naar het oosten is de kasteelpoort.");

        kasteel = new Room("Je bevindt je in een donker kasteel en er zijn drie paden om uit te kiezen ...");

        bibliotheek = new Room("Dit is de oude kasteelbibliotheek, de meeste boeken vallen bijna uit elkaar");

        hal = new Room("Dit is de hal van het kasteel");

        keuken = new Room("Je bent in de keuken. De geur hier is walgelijk");

        tweede_verdieping = new Room("Er is hier nog 1 kamer");

        pad = new Room("Je bevindt je op een klein donker pad");

        eetkamer = new Room("U bevindt zich in de eetkamer");

        slaapkamer = new Room("Je bent in de slaapkamer van de oude koning en koningin, hier is misschien iets nuttigs");

        trappen = new Room("Het wordt hier donkerder en donkerder");

        piano_kamer = new Room("Er is niks in deze kamer... alleen een oude piano");

        kelder = new Room ("Dit is de kelder, het is een zeer donkere plaats ... zachte geluiden zijn te horen");

        kerker = new Room("Dit is een ondergrondse kamer. Er is een schatkist in het midden, maar een monster beschermt het. Wil je ertegen vechten?");

        gevangenis = new Room("Dit is de gevangenis. Gewoon wat schedels die in de kamer liggen");

        vechten = new Room("Wil je de draak bestrijden?"); //Moet eerst het zwaard gehaald hebben uit de bedroom.
        fight2 = new Room("U heeft eerst een zwaard nodig");
        einde = new Room("Je versloeg de draak en kreeg de schat");
    
        // initialise room exits
        language.setExit("English", outside);
        language.setExit("Nederlands",buiten);
   
        buiten.setExit("noord", kasteel);
        buiten.setExit("west", oefenterrein);
       
        oefenterrein.setExit("west", buiten);
        oefenterrein.setConvo("trainer");
        
        kasteel.setExit("links", hal);
        kasteel.setExit("rechts", bibliotheek);
        kasteel.setExit("omhoog", tweede_verdieping);
        kasteel.setExit("omlaag", pad);



        bibliotheek.setExit("noord", eetkamer);
        bibliotheek.setExit("west", kasteel);

        

        eetkamer.setExit("west", hal);
        eetkamer.setExit("zuid", keuken);
       
        pad.setExit("links", trappen);
        pad.setExit("rechts", piano_kamer);
        piano_kamer.setExit("links", pad);
        trappen.setExit("omlaag", kelder);
        
        kelder.setExit("links",kerker);
        kelder.setExit("rechts", gevangenis);       
        gevangenis.setExit("links", kelder);
        kerker.setExit("vechten", vechten);
        
        tweede_verdieping.setExit("omlaag", kasteel);
        tweede_verdieping.setExit("links", slaapkamer);

        slaapkamer.setExit("rechts", tweede_verdieping);
        fight2.setExit("links", kerker);
     

        

        
        

        // initialise room exits
      
        
        outside.setExit("north", castle);
        outside.setExit("west", training_ground);
        
        training_ground.setExit("east", outside);
        training_ground.setExit("south", castle);
        training_ground.setConvo("trainer");
        
        castle.setExit("left", hall);
        castle.setExit("right", libary);
        castle.setExit("up", second_floor);
        castle.setExit("down", path);

        libary.setExit("north", dining_room);
        libary.setExit("east", castle);
        
        dining_room.setExit("east", hall);
        dining_room.setExit("south", kitchen);
        
        path.setExit("left", stairs);
        path.setExit("right", piano_room);
        piano_room.setExit("left", path);
        
        stairs.setExit("down", basement);
        
        basement.setExit("left", chamber);
        basement.setExit("right", jail); 
        
        chamber.setExit("fight", fight );
                
        second_floor.setExit("down", castle);
        second_floor.setExit("left", bedroom);
        bedroom.setExit("right", second_floor);
        fight.setExit("yes" , fightv2);
        fight.setExit("no", chamber);
        fightv2.setExit("left", chamber);
        // start game in language_room
        currentRoom = language; 
        
       
        
        //Itemzzz
        inventory.add(new Item("food"));
        bedroom.setItem(new Item("sword"));
        slaapkamer.setItem(new Item("zwaard"));
        
    
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
      
        
        System.out.println("World of Zuul is a new adventure game.");
        System.out.println("Type 'help' if you need help.");

        System.out.println(currentRoom.getLongDescription());
    }
   
   /*

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        switch (commandWord){
       case UNKNOWN:
       System.out.println("I don't know what you mean...");
       break;
    
       case HELP: 
       printHelp();
       break;
        
        
       case QUIT:
       wantToQuit = quit(command);
       break;
        
       case TALK:
       talk();
       break;
        
        
       case INVENTORY:
       printInventory();
       break;
        
        case TAKE:
       take(command);
        break;
        
        case PAK:
        take(command);
        break;       
       
       case LOOK:
       look();
       break;
       
       case GO:
       wantToQuit = goRoom(command);
       break;
       
       case CHOOSE:
       goRoom(command);
       break;
       
       case GA:
       goRoom(command);
       break;
       
       case TAS:
       printTas();
       break;
    } 
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at a castle at midnight.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }
        
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) 
            System.out.println("Can't go there.");
       else {
            currentRoom = nextRoom;
      System.out.println(currentRoom.getLongDescription());
        
   if (currentRoom.equals(fight) && inventory.contains("sword")){ //gekke foutmelding nog... 1:55 's avonds
        System.out.println("You slayed the dragon with your sword and won the game!");
        return true;
    }
            
    }

    return false;
}

    private void take(Command command){
    if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("That item is not here");
        } else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up " + item);
        
    }
}
      /**
     * "printInventory prints out the inventory"
     */
    private void printInventory() {
       String output = "";
       for(int i =0; i < inventory.size(); i++){
           output += inventory.get(i).getDescription() + " ";
    }
   System.out.println("You are carrying: ");
   System.out.println(output);
}
      /**
     * "printInventory prints out the inventory"
     */
    private void printTas() {
       
       String output = "";
       for(int i =0; i < inventory.size(); i++){
           output += inventory.get(i).getDescription() + " ";
    }
   System.out.println("You are carrying: ");
   System.out.println(output);
}

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
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
            System.out.println("You talk to yourself.");
        }
        else{
            inConvo = true;
        }
    }
   
}
