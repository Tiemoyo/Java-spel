
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
   
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
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
        Room outside, theater, libary, hall, cellar, castle,
        second_floor, path, dining_room, bedroom, stairs, piano_room,
        basement, chamber,jail, kitchen, training_ground,fight, end;
      
        // create the rooms
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

        currentRoom = outside;  // start game outside
        
        inventory.add(new Item("food", "",1));
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
        
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
    
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("talk")) {
            talk();
        }
        else if (commandWord.equals("inventory")){
           printInventory();
        }
        else if (command.equals("take")){          
            //take(); werkt nog niet
        }
        else if (commandWord.equals("back")){
           goBack(command);
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
    private void take(Room bedroom)
    {   
      
      
      if(currentRoom == bedroom){
        System.out.println("You have obtained a sword!" + "\n");
          System.out.println("The sword has been added to your inventory" + "\n");
          inventory.add(new Item("sword", "sharp", 30));
       
        }
    }
    
}