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
    
    private Room small_room;

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
        Room outside, training_ground, gatehouse, outer_courtyard, inner_courtyard, warehouse, south_wall, se_wall, ne_wall, north_wall, armory;

        Item stick = new Item("Stick", "A sturdy wooden tree branch.\n+1 Damage.", "Weapon");
        Item tunic = new Item("Tunic", "A sturdy tunic that protects against damage.\n+1 Armor.", "Armor");
        stick.setDam(1);
        tunic.setArmor(1);

        // create the rooms
        outside = new Room("You stand before the intimidating dark castle. To the north is a drawbridge leading to the castle gate, to the west is a training ground.");
        training_ground = new Room("You are in a training ground next to the castle's ruined western wall. There is an old soldier tending to his equipment. To the east is the castle gate.");
        gatehouse = new Room("You are inside the gatehouse of the outer wall. To the north is a courtyard, to the east are stairs leading to the top of the wall. The western stairs are blocked.");
        outer_courtyard = new Room("You find yourself in the outer courtyard of the castle. To the north is a raised drawbridge, to the east is an important-looking stone building, to the west is a wooden building, to the south is the entrance gatehouse.");
        south_wall = new Room("You are standing on the castle's southern wall. From here you can see where you first arrived from. To the west is the stairs down the gatehouse, to the east is the south-east castle wall.");
        se_wall = new Room("You're standing on the south-east wall, you can see a river flowing below. To the north is the north-east part of the wall and to the south is the southern wall.");
        ne_wall = new Room("You are on the north-east wall, you can see a mountain in the distance. To the west the wall connects to the inner castle wall, to the south is the south-eastern wall.");
        north_wall = new Room("You are on the north part of the wall connecting to the inner wall. To the west is a door leading to the inner courtyard, to the east is the north-east wall.");
        small_room = new Room("You find yourself in a small room with some stuff lying around. Above you is the trapdoor you fell through, too high to climb through. To the south is a door.");
        armory = new Room("You are in the castle's armoury. Unfortunately, it seems there's not much of value left. To the north is a small room, to the west is a now-unlocked door.");
        inner_courtyard = new Room("WIP");
        warehouse = new Room("You are in a run-down warehouse, there isn't much here, aside from some rats. To the west is a ladder.");

        // initialise room exits
        outside.setExit("west", training_ground);
        outside.setExit("north", gatehouse);
        training_ground.setExit("east", outside);
        training_ground.setConvo("trainerfirst");        
        gatehouse.setExit("north", outer_courtyard);
        gatehouse.setExit("east", south_wall);
        outer_courtyard.setExit("south", gatehouse);
        outer_courtyard.setExit("east", armory);
        south_wall.setExit("west", gatehouse);
        south_wall.setExit("east", se_wall);
        se_wall.setExit("south", south_wall);
        se_wall.setExit("north", ne_wall);
        ne_wall.setExit("south", se_wall);
        ne_wall.setExit("west", north_wall);
        north_wall.setExit("east", ne_wall);
        north_wall.setExit("west", inner_courtyard);
        north_wall.setExit("trapdoor", small_room);
        small_room.setExit("south", armory);
        armory.setExit("north", small_room);
        armory.setExit("west", outer_courtyard);
        
        outside.addItem("Stick", stick);
        small_room.addItem("Tunic", tunic);
        
        inner_courtyard.isLocked();
        armory.isLocked();
        
        outer_courtyard.setBattle("randomguard");
        south_wall.setBattle("randomguard");
        se_wall.setBattle("randomguard");
        ne_wall.setBattle("randomguard");
        north_wall.setBattle("randomguard");

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
        trainrefuse = new Convo("'It's your loss.' The soldier returns his attention to his equipment, ending the conversation.");
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
        System.out.println("World of Zuul is a text adventure game.");
        System.out.println("Type 'start' if you'd like to start the game. Type 'help' for commands.");
        System.out.println();
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
            else if (commandWord == CommandWord.BACK) {
                back();
            }
        }

        return wantToQuit;
    }

    // implementations of user commands:
    private void start(){
        gameStart = true;
        System.out.println(currentRoom.getShortDescription());
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("To move to another area, type 'go [direction]'. To get the description of your current area type 'look', this also shows any items in the area.");
        System.out.println("To take an item from an area type 'take [item]'. (Case-sensitive) Not specifying an item will make you take every item in the area.");
        System.out.println("To check your inventory, simply type 'inventory' or 'bag'. To equip an item in your inventory type 'equip [item]'. (Case-sensitve)");
        System.out.println("If there is someone to talk to, you can type 'talk' to begin a conversation.");
        System.out.println("While in a conversation, you can type 'talk [response]' or 'say [response]' to progress the conversation.");
        System.out.println("To go back to the area you were previously in, type 'back'. You repeat this command to backtrack through multiple areas.");
        System.out.println("Finally, to end and quit the game, type 'quit'.");
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            System.out.println("Can't go there.");
        }
        else if(direction == "trapdoor"){
            System.out.println("Trapdoor?");
        }
        else if(nextRoom.getLock() == true){
            System.out.println("Locked, can't go there.");
            trapdoor();
        }
        else{
            roomhistory.add(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
            if(currentRoom.getBattle()){
                if(currentRoom.getBattleType() == "randomguard"){
                    if(fight.randomBattle() == true){
                        inFight = true;
                        fight.lowRandomize();
                        System.out.println(fight.getAction("randomguard"));
                    }
                }
            }
        }
    }
    
    private void trapdoor(){
        Room nextRoom = currentRoom.getExit("trapdoor");
        if(nextRoom != null){
            System.out.println("You notice the keyhole for the door seems rather intricate. As you inspect it, you suddenly feel the ground beneath you give way!");
            System.out.println("You fell through a trapdoor!");
            currentRoom.setDescription("You are on the north part of the wall connecting to the inner wall. To the west is a door leading to the inner courtyard, to the east is the north-east wall. There is also a trapdoor you fell through earlier.");            
            currentRoom.setExit("down", small_room);
            currentRoom = nextRoom;
            roomhistory.clear();
            nextRoom = currentRoom.getExit("south");
            nextRoom.isUnlocked();
            System.out.println(currentRoom.getShortDescription());
        }
        else{
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
        int gottenXP = fight.getXPGain();
        player.addXP(gottenXP);
        System.out.println("You gained " + gottenXP + " XP!");
        if(player.lvlCheck() == true){
            player.lvlUp();
            currentHP += 1;
        }
        inFight = false;
        fight.resetTurn();
        if(currentRoom.getConvoType() == "trainreturn"){
            Item shortsword = new Item("Shortsword", "A shortsword that's clearly seen a lot of use, though it seems it was well-maintained.\n+2 to damage.", "Weapon");
            shortsword.setDam(2);
            System.out.println( "'Congratulations, you're pretty good. As promised, one of my spare swords. Put it to good use.'");
            inventory.put("Shortsword", shortsword);
            System.out.println("You take the sword.");
            currentHP = player.getTotalHP();
            System.out.println("Your health was restored.");
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

    private void back()
    {
        if(roomhistory.size() < 1){
            System.out.println("Can't go back.");
        }
        else{
            int index = roomhistory.size();
            index -= 1;
            currentRoom = roomhistory.get(index);
            roomhistory.remove(index);
            System.out.println(currentRoom.getShortDescription());
        }
    }
}
