
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
    private Room previousRoom;
    private Stack<Room> roomStack;
    private boolean inConvo;
    private ArrayList<Item> inventory;
    private Stack<Room> backStack;
   
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        inConvo = false;
        inventory = new ArrayList<Item>();
        backStack = new Stack<Room>();
        createRooms();
    }
   
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room language, outside,buiten, bibliotheek, libary, hall, hal, castle, kasteel,
        second_floor, tweede_verdieping, path, pad, dining_room, eetkamer,slaapkamer, bedroom, stairs, piano_room,
        basement,kelder, chamber, kerker,jail, gevangenis, kitchen, keuken, training_ground, oefenterrein,fight, vechten, einde, end
        ,trappen, piano_kamer;
      
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
        fight = new Room("Want to fight the dragon?"); //Moet eerst het zwaard gehaald hebben uit de bedroom.
        end = new Room("You defeated the dragon and obtained the treasure");
        
        
        buiten = new Room("U staat voor een duister kasteel. In het noorden is de kasteelpoort, naar het westen is het oefenterrein.");

        oefenterrein = new Room("U bent op het oefenterrein van het kasteel. Er is een oude soldaat die zijn uitrusting verzorgt. Terug naar het oosten is de kasteelpoort.");

        kasteel = new Room("Je bevindt je in een donker kasteel en er zijn drie paden om uit te kiezen ...");

        bibliotheek = new Room("Dit is de oude kasteelbibliotheek, de meeste boeken vallen bijna uit elkaar");

        hal = new Room("Dit is de hal van het kasteel");

        keuken = new Room("Je bent in de keuken. De geur hier is walgelijk");

        tweede_verdieping = new Room("Er zijn hier meer kamers");

        pad = new Room("Je bevindt je op een klein donker pad");

        eetkamer = new Room("U bevindt zich in de eetkamer");

        slaapkamer = new Room("Je bent in de slaapkamer van de oude koning en koningin, hier is misschien iets nuttigs");

        trappen = new Room("Het wordt hier donkerder en donkerder");

        piano_kamer = new Room("Er is niks in deze kamer... alleen een oude piano");

        kelder = new Room ("Dit is de kelder, het is een zeer donkere plaats ... zachte geluiden zijn te horen");

        kerker = new Room("Dit is een ondergrondse kamer. Er is een schatkist in het midden, maar een monster beschermt het. Wil je ertegen vechten?");

        gevangenis = new Room("Dit is de gevangenis. Gewoon wat schedels die in de kamer liggen");

        vechten = new Room("Wil je de draak bestrijden?"); //Moet eerst het zwaard gehaald hebben uit de bedroom.

        einde = new Room("Je versloeg de draak en kreeg de schat");

        // initialise room exits
         
       language.setExit("English", outside);
       language.setExit("Nederlands", buiten);
        
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
       
        trappen.setExit("omlaag", kelder);
        
        kelder.setExit("links", chamber);
        kelder.setExit("rechts", gevangenis);       

        kerker.setExit("vechten", vechten);
        
        tweede_verdieping.setExit("omlaag", kasteel);
        tweede_verdieping.setExit("links", slaapkamer);

        

        //09-01-2020 laatst veranderd.

        //Beginnen met documentatie bijhouden

        

        
        

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
        
        stairs.setExit("down", basement);
        
        basement.setExit("left", chamber);
        basement.setExit("right", jail); 
        
        chamber.setExit("fight", fight );
                
        second_floor.setExit("down", castle);
        second_floor.setExit("left", bedroom);
        
        //09-01-2020 laatst veranderd.
        //Beginnen met documentatie bijhouden

        // start game outside
        currentRoom = language; 
        
        //items
        
        
       
    }

  public void storeConvos()
    {
        
    }
  private void english()
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
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
     private void enterRoom(Room nextRoom) {
      previousRoom = currentRoom;
      currentRoom = nextRoom;
      System.out.println(currentRoom.getLongDescription());
    }
    private void goBack(Command command)
    {
        if(command.hasSecondWord()){
         System.out.println("Back what?");
                return;
            }
        if (previousRoom == null){
                System.out.println("Sorry, cannot go back");
                
            }
            else{
                enterRoom(previousRoom);
           
            }
        }
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
       take();
        break;
            
       case BACK:
       goBack(command);
       break;
       
       case LOOK:
       look();
       break;
       
       case GO:
       goRoom(command);
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
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Can't go there.");
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
      System.out.println(currentRoom.getLongDescription());
    }
    }
    
      /**
     * "printInventory prints out the inventory"
     */
    private void printInventory() {
        if (inventory.size() == 0) {
            System.out.println("you are not carrying anything");
        } else {
            System.out.print("You have the following:");
            for (int n = 0; n < inventory.size(); n++) {
                Item item = inventory.get(n);
                System.out.print("\n" + " " + item.getIname() + "\n");
            }
            
        }
    }
      /**
     * "printInventory prints out the inventory"
     */
    private void printTas() {
        if (inventory.size() == 0) {
            System.out.println("U hebt momenteel niks bij je");
        } else {
            System.out.print("U hebt de volgende spullen bij je:");
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
    private void take()
    {   
    Room bedroom;
    bedroom = new Room("You are in the bedroom of the old king and queen, there might be something useful here");
    if(currentRoom.equals(bedroom)){
    bedroom.addItem("sword","sharp",30); 
   
         System.out.println("You have obtained a sword!" + "\n"); 
        
        inventory.add(new Item("sword", "sharp", 30));
       
   
 } else{
     System.out.println("There's no item here");
}
}
private void fight()
{
    Room chamber;
   //Volgens mij hebben we de player klasse nodig voordat je een item kan gebruiken
    //if(printInventory().contains("sword")){
        System.out.print("You are ready to take on the dragon");
   // } else{
    System.out.println("You might need an item first to fight");
    
}
}

