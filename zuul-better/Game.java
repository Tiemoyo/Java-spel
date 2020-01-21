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
    private HashMap<String, Item> inventory;
    private HashMap<String, Item> equipment;
    private ArrayList<Room> roomhistory;

    private int currentHP;
    private Player player;
    private Fight fight;

    //The following are flags to ensure the conversation system works properly.
    private Convo trainerfirst;
    private Convo trainaccept;
    private Convo trainrefuse;
    private Convo trainreturn;
    private Convo trainwin;

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
        inventory = new HashMap<String, Item>();
        equipment = new HashMap<String, Item>();
        roomhistory = new ArrayList<Room>();
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
        Room outside, training_ground;

        Item stick = new Item("Stick", "A sturdy wooden tree branch", "Weapon");
        stick.setDam(1);

        // create the rooms
        outside = new Room("You stand before the intimidating dark castle. To the north is the castle gate, to the west is a training ground.");
        training_ground = new Room("You are in a training ground next to the castle. There is an old soldier tending to his equipment. Back to the east is the castle gate.");

        // initialise room exits
        outside.setExit("west", training_ground);
        outside.addItem("Stick", stick);

        training_ground.setExit("east", outside);
        training_ground.setConvo("trainerfirst");

        currentRoom = outside;  // start game outside     
    }    

    private void storeConvos()

    {
        Convo traininterest, traindisinterest;

        //create the conversations
        trainerfirst = new Convo("You greet the soldier.\n'Hello there, lad. You seem like you could use a bit of training, especially if you're thinkin' of heading into the castle'\nA. Declare your interest.\nB. You're not interested.");
        traininterest = new Convo("'Good thinkin' lad. To tell ya truth I'm a bit rusty, so how about we spar? If ya win, I'll give ya one of my spare swords.'\nA. Accept.\nB. Refuse.");
        traindisinterest = new Convo("'Confident, are ya? Listen here, if yer able to beat me one-on-one, you can win one of my swords'\nA. Accept.\nB. Refuse.");
        trainaccept = new Convo("'Alright, let's see what you're made of!'");
        trainrefuse = new Convo("'It's your loss.' The soldier returns his attention to his equipment ending the conversation.");
        trainreturn = new Convo("'Ah, you return! Want to give it another go?'\nYes.\nNo.");
        trainwin = new Convo("'Congratulations, you're pretty good. As promised, one of my spare swords. Put it to good use.'\nYou take the sword.");

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
        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }        
        else if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if(gameStart == false){
            if(commandWord == CommandWord.START){
                start();
            }
            else{
                System.out.println("Game has not started yet.");
            }
        }
        else if(inConvo == true){
            if (commandWord == CommandWord.TALK) {
                talk(command);
            }
            else{
                System.out.println("Can't do that while in conversation.");
            }
        }
        else if(inFight == true){
            if(commandWord == CommandWord.ATT){
                attack();
            }
            else if (commandWord == CommandWord.INV){
                printInventory();
            }
            else{
                System.out.println("Can't do that while fighting.");
            }
        }
        else{          
            if (commandWord == CommandWord.GO) {
                goRoom(command);
            }
            else if (commandWord == CommandWord.LOOK) {
                look();
            }
            else if (commandWord == CommandWord.TALK) {
                talk(command);
            }
            else if (commandWord == CommandWord.START) {
                System.out.println("Game has already started");  
            }
            else if (commandWord == CommandWord.INV){
                printInventory();
            }
            else if (commandWord == CommandWord.ATT) {
                System.out.println("Not in battle.");  
            }
            else if (commandWord == CommandWord.EQP) {
                equipItem(command);
            }
            else if (commandWord == CommandWord.TAKE) {
                take(command);
            }
        }

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
            System.out.println("You are not carrying anything.");
        }
        else {
            System.out.println("You have the following:");
            for (String i : inventory.keySet()) {
                Item item = inventory.get(i);
                System.out.print(item.getName());
                if(item.getEquip() == true){
                    System.out.print(" [Equipped]");
                }
                System.out.print("\n");
            }
        }
    }

    private void equipItem(Command command)
    {
        if(!command.hasSecondWord()) {          
            System.out.println("Equip what?");
            return;
        }

        String gItem = command.getSecondWord();
        if(inventory.get(gItem) == null){
            System.out.println("You don't have that item.");
            return;
        }      
        Item item = inventory.get(gItem);

        if(item.getType() == "Weapon"){
            if(equipment.get("Weapon") == null){
                equipment.put("Weapon", item);
                player.setWpnDamage(item.getDam());
                item.equipIt();
                System.out.println("You have equipped " + item.getName() + ".");
            }
            else{
                Item equip = equipment.get("Weapon");
                equip.unEquipIt();
                equipment.remove("Weapon");
                equipment.put("Weapon", item);
                player.setWpnDamage(item.getDam());
                item.equipIt();
                System.out.println("Unequipped " + equip.getName() + " and equipped " + item.getName() + ".");
            }
        }
        else if(item.getType() == "Armor"){
            if(equipment.get("Armor") == null){
                equipment.put("Armor", item);
                player.setArmor(item.getArmor());
                item.equipIt();
                System.out.println("You have equipped " + item.getName() + ".");
            }
            else{
                Item equip = equipment.get("Armor");
                equip.unEquipIt();
                equipment.remove("Armor");
                equipment.put("Armor", item);
                player.setWpnDamage(item.getDam());
                item.equipIt();
                System.out.println("Unequipped " + equip.getName() + " and equipped " + item.getName() + ".");
            }
        }
        else if(item.getType() == "Shield"){
            if(equipment.get("Shield") == null){
                equipment.put("Shield", item);
                player.setShield(item.getShield());
                item.equipIt();
                System.out.println("You have equipped " + item.getName() + ".");
            }
            else{
                Item equip = equipment.get("Shield");
                equip.unEquipIt();
                equipment.remove("Shield");
                equipment.put("Shield", item);
                player.setWpnDamage(item.getDam());
                item.equipIt();
                System.out.println("Unequipped " + equip.getName() + " and equipped " + item.getName() + ".");
            }
        }
        else{
            System.out.println("Can't equip this item.");
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
        System.out.println(currentRoom.getShortDescription());
        System.out.println(currentRoom.getItems());
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
                    fight.setXPGain(3);
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
            System.out.println("You dealt " + player.getDamage() + " damage.");
            fight.addTurn();
            if(fight.getEnemyHP() < 1){
                winFight();
                return;
            }
        }
        else{
            System.out.println(fight.getAction("youmiss"));
            fight.addTurn();
        }
        boolean enemyhits = fight.enemyHitChance();
        if(enemyhits == true){
            currentHP -= fight.getEnemyDam();
            System.out.println(fight.getAction("enemyhit"));
            System.out.println("Enemy dealt " + fight.getEnemyDam() + " damage.");
            if(currentHP < 1){
                loseFight();
                return;
            }
        }
        else{
            System.out.println(fight.getAction("enemymiss"));
        }

    }

    private void winFight()
    {
        System.out.println(fight.getAction("youwin"));
        player.addXP(fight.getXPGain());
        System.out.println("You gained " + fight.getXPGain() + " XP!");
        if(player.lvlCheck() == true){
            player.lvlUp();
        }
        inFight = false;
        fight.resetTurn();
        if(currentRoom.getConvoType() == "trainreturn"){
            Item oldsword = new Item("Shortsword", "A shortsword that's clearly seen a lot of use, though it seems it was well-maintained.\n+2 to damage.", "Weapon");
            oldsword.setDam(2);
            System.out.println(trainwin.getContent());
            inventory.put("Shortsword", oldsword);
            currentRoom.unsetConvo();
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private void loseFight()
    {
        System.out.println(fight.getAction("youlose"));
        inFight = false;
        fight.resetTurn();
        if(currentRoom.getConvoType() == "trainreturn"){
            System.out.println("'Seems like a win for me! Come back if you're feeling like giving it another go though.'");
            System.out.println("Your health was restored.");
            currentHP = player.getTotalHP();
            System.out.println(currentRoom.getShortDescription());
        }
        else{
            System.out.println("You have died.\nGAME OVER");
            Command quit = new Command(CommandWord.QUIT , null);
            processCommand(quit);
        }
    }

    private void take(Command command)
    {   
        if(currentRoom.items.size() < 1){
            System.out.println("No items here.");
        }
        else{
            if(!command.hasSecondWord()){
                for (String i : currentRoom.items.keySet()) {                    
                    Item item = currentRoom.items.get(i);
                    inventory.put(item.getName(), item);
                }
                currentRoom.items.clear();
                System.out.println("All items taken.");
            }
            else{
                String gItem = command.getSecondWord();
                if(currentRoom.items.get(gItem) != null){
                    Item item = currentRoom.items.get(gItem);
                    inventory.put(gItem, item);
                    currentRoom.items.remove(gItem);
                    System.out.println("You have taken the " + item.getName() + ".");
                }
                else{
                    System.out.println("No " + gItem + " here.");
                }
            }
        }
    }
}
