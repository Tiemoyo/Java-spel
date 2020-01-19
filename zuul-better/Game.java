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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean inConvo;
    private boolean gameStart;
    private boolean inFight;
    private Convo currentConvo;
    private ArrayList<Item> inventory;
    private int currentHP;
    private Player player;
    private Fight fight;

    //The following are flags to ensure the conversation system works properly.
    private Convo trainerfirst;
    private Convo trainaccept;
    private Convo trainrefuse;
    private Convo trainreturn;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        storeConvos();
        parser = new Parser();
        inConvo = false;
        gameStart = false;
        inFight = false;
        parser = new Parser();
        inventory = new ArrayList<Item>();
        player = new Player();
        currentHP = player.getTotalHP();
        fight = new Fight();

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
        /*
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
         */
        // initialise room exits
        //outside.setExit("north", castle);
        outside.setExit("west", training_ground);

        training_ground.setExit("east", outside);
        training_ground.setConvo("trainerfirst");
        /*
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
         */
        //09-01-2020 laatst veranderd.
        //Beginnen met documentatie bijhouden

        currentRoom = outside;  // start game outside

        //inventory.add(new Item("food", "",1));
        //inventory.add(new Item("water", "",1));
    }    

    private void storeConvos()

    {
        Convo traininterest, traindisinterest;

        //create the conversations
        trainerfirst = new Convo("You greet the soldier.\n'Hello there, lad. You seem like you could use a bit of training, especially if you're thinkin' of heading into the castle'\nA. Declare your interest.\nB. You're not interested.");
        traininterest = new Convo("'Good thinkin' lad. To tell ya truth I'm a bit rusty, so how about we spar? If ya win, I'll give ya one of my spare swords.'\nA. Accept.\nB. Refuse.");
        traindisinterest = new Convo("'Confident, are ya? Listen here, if yer able to beat me one-on-one, you can win one of my swords'\nA. Accept.\nB. Refuse.");
        trainaccept = new Convo("'Alright, let's see what you're made of.' This is in progress, so the conversation ends here.");
        trainrefuse = new Convo("'It's your loss.' The soldier returns his attention to his equipment ending the conversation.");
        trainreturn = new Convo("'Ah, you return! Want to give it another go?'\nYes.\nNo.");

        trainerfirst.setLink("a", traininterest);
        trainerfirst.setLink("b", traindisinterest);
        traininterest.setLink("a", trainaccept);
        traininterest.setLink("b", trainrefuse);
        traindisinterest.setLink("a", trainaccept);
        traindisinterest.setLink("b", trainrefuse);
        trainreturn.setLink("yes", trainaccept);
        trainreturn.setLink("no", trainrefuse);
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
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Choose your language: " + "\n");

        System.out.println("World of Zuul is a new adventure game.");
        System.out.println("Type 'start' if you'd like to start the game. Type 'help' for commands.");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());
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
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(gameStart == false){
            if(commandWord.equals("start")){
                start();
            }
            else if (commandWord.equals("go")) {
                System.out.println("Game has not started yet.");
            }
            else if (commandWord.equals("look")) {
                System.out.println("Game has not started yet.");
            }
            else if (commandWord.equals("talk")) {
                System.out.println("Game has not started yet.");
            }
            else if (commandWord.equals("inventory")){
                System.out.println("Game has not started yet.");
            }
            else if (commandWord.equals("attack")){
                System.out.println("Game has not started yet.");
            }
        }
        else if(inConvo == true){
            if (commandWord.equals("go")) {
                System.out.println("Can't move while in conversation.");
            }
            else if (commandWord.equals("talk")) {
                talk(command);
            }
            else if (commandWord.equals("look")) {
                System.out.println("Can't look while in conversation.");
            }
            else if (commandWord.equals("inventory")){
                System.out.println("Can't open inventory in conversation.");
            }
            else if (commandWord.equals("attack")){
                System.out.println("Can't attack in conversation.");
            }
        }
        else if(inFight == true){
            if(commandWord.equals("attack")){
                attack();
            }
            else if (commandWord.equals("go")) {
                System.out.println("Can't move while in fight.");
            }
            else if (commandWord.equals("look")) {
                System.out.println("Can't look while in fight.");
            }
        }
        else{          
            if (commandWord.equals("go")) {
                goRoom(command);
            }
            else if (commandWord.equals("look")) {
                look();
            }
            else if (commandWord.equals("talk")) {
                talk(command);
            }
            else if (commandWord.equals("start")) {
                System.out.println("Game has already started");  
            }
            else if (commandWord.equals("inventory")){
                printInventory();
            }
            else if (commandWord.equals("attack")) {
                System.out.println("Not in battle.");  
            }
        }       
        /*else if (command.equals("take")){          
        //take(); werkt nog niet
        }
        // else command not recognised. */
        return wantToQuit;
    }

    // implementations of user commands:
    private void start(){
        gameStart = true;
        System.out.println(currentRoom.getLongDescription());
    }

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

    private void talk(Command command)
    {               
        if(!command.hasSecondWord()){
            if(currentRoom.getConvo() == false){
                System.out.println("You talk to yourself.");
            }
            else if(inConvo == false){
                //If there's a conversation available in the current room, conversation mode is activated and the conversation is called.
                inConvo = true;
                if(currentRoom.getConvoType() == "trainerfirst"){
                    currentConvo = trainerfirst;
                    System.out.println(currentConvo.getContent());
                }
                else if(currentRoom.getConvoType() == "trainreturn"){
                    currentConvo = trainreturn;
                    System.out.println(currentConvo.getContent());
                }
                else{
                    System.out.println("Error, no Convo called.");
                    inConvo = false;
                }
            }
        }        
        if(command.hasSecondWord()){
            String link = command.getSecondWord();
            Convo nextConvo = currentConvo.getLink(link);
            if(nextConvo == null){
                System.out.println("Invalid response.");
            }
            else{
                //Here is how we move to different parts of the conversation and call those parts.
                currentConvo = nextConvo;
                System.out.println(currentConvo.getContent());
                //Next we check for flags that signal the end of a conversation, and use them to end the conversation mode. 
                //We'll also set a flag in the room to signal that we had the conversation in the first place.
                if(currentConvo == trainaccept){
                    inConvo = false;
                    currentRoom.setConvo("trainreturn");
                    inFight = true;
                    fight.setEnemyHP(5);
                    fight.setEnemyDam(1);
                    System.out.println(fight.getAction("trainbegin"));
                }
                else if(currentConvo == trainrefuse){
                    inConvo = false;
                    System.out.println(currentRoom.getShortDescription());
                    currentRoom.setConvo("trainreturn");
                }
            }
        }
    }

    private void attack()
    {
        boolean playerhits = player.hitChance();        
        if(playerhits == true){
            fight.dealDamage(player.getDamage());
            System.out.println(fight.getAction("youhit"));
            System.out.println(" You dealt " + player.getDamage() + " damage.");
            if(fight.getEnemyHP() < 1){
                inFight = false;
                System.out.println(fight.getAction("youwin"));
                System.out.println(currentRoom.getShortDescription());
            }
        }
        else{
            System.out.println(fight.getAction("youmiss"));
        }
        if(inFight = true){
            boolean enemyhits = fight.enemyHitChance();
            if(enemyhits == true){
                currentHP -= fight.getEnemyDam();
                System.out.println(fight.getAction("enemyhit"));
                System.out.println("Enemy dealt " + fight.getEnemyDam() + " damage.");
                if(currentHP < 1){
                    inFight = false;
                    System.out.println(fight.getAction("youlose"));
                    System.out.println(currentRoom.getShortDescription());
                }
            }
            else{
                System.out.println(fight.getAction("enemymiss"));
            }
        }
    }
    /*private void take(Room bedroom)
    {   

    if(currentRoom == bedroom){
    System.out.println("You have obtained a sword!" + "\n");
    System.out.println("The sword has been added to your inventory" + "\n");
    inventory.add(new Item("sword", "sharp", 30));

    }
    }*/

}