
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
 * @author  Quinten de Haan & Tiemo Laban
 * @version 2020.01.24
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room teleportRoom;
    private SL SL;
    private boolean inConvo;
    private boolean gameStart;
    private boolean inFight;
    private boolean gameOver;
    private boolean langSet;
    private boolean boss;
    private boolean trap;
    private Convo currentConvo;
    private HashMap<String, Item> inventory;
    private HashMap<String, Item> equipment;
    private ArrayList<Room> roomhistory;
    private Language language;

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
        language = Language.ENGLISH;        
        storeConvos();
        parser = new Parser();
        parser.setLang(1);
        SL = new SL();
        inConvo = false;
        gameStart = false;
        inFight = false;
        gameOver = false;
        langSet = false;
        trap = true;
        inventory = new HashMap<String, Item>();
        equipment = new HashMap<String, Item>();
        roomhistory = new ArrayList<Room>();
        player = new Player();
        currentHP = player.getTotalHP();
        fight = new Fight();
        createRooms();

        Item ration = new Item("Ration", "A ration of food. Fully heals.", "Health");
        inventory.put("Ration", ration);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, training_ground, gatehouse, outer_courtyard, inner_courtyard, warehouse, warehousebedroom, south_wall, se_wall, ne_wall, north_wall, armory, west_wall, nw_wall, tower,
        keep, throne;

        Item stick = new Item("Stick", "A sturdy wooden tree branch.\n+1 Damage.", "Weapon");
        Item tunic = new Item("Tunic", "A sturdy tunic that protects against damage.\n+1 Armor.", "Armor");
        Item bandages = new Item("Bandages", "Bandages used to stop bleeding. Fully heals.", "Health");
        Item buckler = new Item("Buckler", "A relatively small round shield.\n+1 Shield.", "Shield");
        Item bread = new Item("Bread", "A loaf of bread. Fully heals.", "Health");
        Item innerkey = new Item("InnerKey", "A key to access the inner walls of the castle.", "Key1");
        Item keychain = new Item("Keychain", "Several keys on  chain, includes keys for guard towers.", "Key2");
        Item beamer = new Item("Teleporter", "Stores a location allowing you to teleport to it.", "Teleport");
        Item longsword = new Item("Longsword", "A well-crafted longsword.\n+5 Damage.", "Weapon");
        Item suit = new Item("Armorsuit", "A full suit of armor that protects against damage.\n+5 Armor.", "Armor");
        Item kite = new Item("Kiteshield", "A kite shield.\n+2 Shield.", "Shield");
        Item water = new Item("Water", "water", "Health");
        longsword.setDam(5);
        suit.setArmor(5);
        kite.setShield(2);
        stick.setDam(1);
        tunic.setArmor(1);
        buckler.setShield(1);

        // create the rooms
        outside = new Room(getFromSL(SLe.R_OUTSIDE));
        training_ground = new Room(getFromSL(SLe.R_TRAIN));
        gatehouse = new Room(getFromSL(SLe.R_GATE));
        outer_courtyard = new Room(getFromSL(SLe.R_OUTYARD));
        south_wall = new Room(getFromSL(SLe.R_SWALL));
        se_wall = new Room(getFromSL(SLe.R_SEWALL));
        ne_wall = new Room(getFromSL(SLe.R_NEWALL));
        north_wall = new Room(getFromSL(SLe.R_NWALL));
        small_room = new Room(getFromSL(SLe.R_SMALL));
        armory = new Room(getFromSL(SLe.R_ARM));        
        warehouse = new Room(getFromSL(SLe.R_WHOUSE));
        warehousebedroom = new Room(getFromSL(SLe.R_WHBED));
        west_wall = new Room(getFromSL(SLe.R_WWALL));
        nw_wall = new Room(getFromSL(SLe.R_NWWALL));
        tower = new Room(getFromSL(SLe.R_TOWER));
        inner_courtyard = new Room(getFromSL(SLe.R_INYARD));
        keep = new Room("The keep, has some items you'll need. North is the throne room, south is back to the courtyard.");
        throne = new Room("");

        // initialise room exits
        inner_courtyard.setExit("east", north_wall);
        inner_courtyard.setExit("north", keep);
        keep.setExit("south", inner_courtyard);
        keep.setExit("north", throne);
        outside.setExit("west", training_ground);
        outside.setExit("north", gatehouse);
        training_ground.setExit("east", outside);
        training_ground.setConvo("trainerfirst");        
        gatehouse.setExit("north", outer_courtyard);
        gatehouse.setExit("east", south_wall);
        outer_courtyard.setExit("south", gatehouse);
        outer_courtyard.setExit("east", armory);
        outer_courtyard.setExit("west", warehouse);
        warehouse.setExit("west", warehousebedroom);
        warehousebedroom.setExit("east", warehouse);
        warehousebedroom.setExit("west", west_wall);
        west_wall.setExit("east", warehousebedroom);
        west_wall.setExit("north", nw_wall);
        nw_wall.setExit("south", west_wall);
        nw_wall.setExit("north", tower);
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
        small_room.addItem("Bandages", bandages);
        armory.addItem("Keychain", keychain);
        north_wall.addItem("Teleporter", beamer);
        warehousebedroom.addItem("Bread", bread);
        warehousebedroom.addItem("Buckler", buckler);
        tower.addItem("InnerKey", innerkey);
        keep.addItem("Longsword", longsword);
        keep.addItem("Armorsuit", suit);
        keep.addItem("Kiteshield", kite);
        keep.addItem("Water", water);

        inner_courtyard.isLocked();
        armory.isLocked();
        tower.isLocked();

        outer_courtyard.setBattle("randomguard");
        inner_courtyard.setBattle("randomguard");
        south_wall.setBattle("randomguard");
        se_wall.setBattle("randomguard");
        ne_wall.setBattle("randomguard");
        north_wall.setBattle("randomguard");
        west_wall.setBattle("randomguard");
        nw_wall.setBattle("randomguard");
        warehouse.setBattle("randomrat");
        warehousebedroom.setBattle("randomrat");
        armory.setBattle("randomguard");
        small_room.setBattle("randomrat");
        tower.setBattle("randomguard");
        keep.setBattle("randomguard");
        throne.setBattle("boss");

        currentRoom = outside;  // start game outside     
    }    

    /**
     * 
     * Creates conversation trees for the game.
     * 
     */
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
        chooseLanguage();

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
     * Here the player chooses their language.
     */
    private void chooseLanguage()
    {
        System.out.println();
        System.out.println("Select a language/Selecteer een taal.");
        System.out.println("1. English.");
        System.out.println("2. Nederlands.");
        System.out.println();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.print(getFromSL(SLe.WELCOME));
    }

    /**
     * Gets strings from a string library based on the language of the game.
     */
    private String getFromSL(SLe sle)
    {
        String string;
        switch(language){
            case ENGLISH:
            string = SL.enstrings.get(sle);
            break;

            case NEDERLANDS:
            string = SL.nlstrings.get(sle);
            break;

            default:
            string = SL.enstrings.get(sle);
        }
        return string;

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

        switch(commandWord){
            case UNKNOWN:
            System.out.print(getFromSL(SLe.WHAT));
            break;

            case HELP:
            printHelp();
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;
        }

        if(langSet == false){
            switch(commandWord){
                case LANGEN:
                parser.setLang(1);
                language = Language.ENGLISH;
                printWelcome();
                langSet = true;
                break;

                case LANGNL:
                parser.setLang(2);
                language = Language.NEDERLANDS;
                printWelcome();
                langSet = true;
                break;
            }
        }
        else if(gameStart == false){
            switch(commandWord){
                case START:
                start();
                break;

                case ABOUT:
                System.out.println("This is a project by Quinten en Tiemo.");
                break;

                default:
                System.out.println("Game has not started yet.");
            }
        }
        else if(inConvo == true){
            switch(commandWord){
                case TALK:
                talk(command);
                break;

                default:
                System.out.println("Can't do that while in conversation.");
            }
        }
        else if(inFight == true){
            switch(commandWord){
                case ATT:
                attack();
                break;

                case INV:
                printInventory();
                break;

                case STAT:
                printStats();
                break;

                default:
                System.out.println("Can't do that while fighting.");
            }
        }
        else if(gameOver == true){
            if (commandWord != CommandWord.QUIT) {
                switch(language){
                    case ENGLISH:
                    System.out.println("The game is over, you can only quit the game now.");
                    break;
                    case NEDERLANDS:
                    System.out.println("Het spel is over, je kan nu alleen het spel afsluiten.");
                    break;
                }
            }
        }
        else{
            switch(commandWord){
                case GO:
                goRoom(command);
                break;

                case TALK:
                talk(command);
                break;

                case START:
                switch(language){
                    case ENGLISH:
                    System.out.println("Game has already started");
                    break;
                    case NEDERLANDS:
                    System.out.println("Spel is al begonnen.");
                    break;
                }
                break;

                case INV:
                printInventory();
                break;

                case ATT:
                switch(language){
                    case ENGLISH:
                    System.out.println("Not in fight.");
                    break;
                    case NEDERLANDS:
                    System.out.println("Niet in gevecht.");
                    break;
                }
                break;

                case EQP:
                equipItem(command);
                break;

                case TAKE:
                take(command);
                break;

                case STAT:
                printStats();
                break;

                case BACK:
                back();
                break;

                case ITEM:
                printItem(command);
                break;

                case USE:
                useItem(command);
                break;

                case DROP:
                dropItem(command);
                break;
            }          
        }
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Signals the game has started, and gives the description of the starting room.
     */
    private void start(){
        gameStart = true;
        System.out.println(currentRoom.getShortDescription());
    }

    /**
     * Print out some help information.
     * It's not entirely complete.
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
     * Try to go to a direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * also some random battles are checked for here.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know where to go...

            switch(language){
                case ENGLISH:
                System.out.println("Go where?");
                break;
                case NEDERLANDS:
                System.out.println("Ga waarheen?");
                break;
            }
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            switch(language){
                case ENGLISH:
                System.out.println("Can't go there.");
                break;
                case NEDERLANDS:
                System.out.println("Kan daar niet heen gaan.");
                break;
            }
        }
        else if(direction == "trapdoor"){
            switch(language){
                case ENGLISH:
                System.out.println("Trapdoor?");
                break;
                case NEDERLANDS:
                System.out.println("Valluik?");
                break;
            }
        }
        else if(nextRoom.getLock() == true){
            switch(language){
                case ENGLISH:
                System.out.println("Locked, can't go there.");
                break;
                case NEDERLANDS:
                System.out.println("Op slot, kan daar niet heen gaan.");
                break;
            }
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
                else if(currentRoom.getBattleType() == "randomrat"){
                    if(fight.randomBattle() == true){
                        inFight = true;
                        fight.lowRandomize();
                        System.out.println(fight.getAction("randomrat"));
                    } 
                }
                else if(currentRoom.getBattleType() == "boss"){

                    inFight = true;
                    boss = true;
                    fight.setEnemyHP(50);
                    fight.setEnemyDam(7);
                    fight.setXPGain(100);
                    System.out.println("You prepare to face the king!");

                }
            }
        }
    }

    /**
     * checks whether the room has a trapdoor, if so, it gets activated.
     */
    private void trapdoor(){
        Room nextRoom = currentRoom.getExit("trapdoor");
        if(trap = true){
            if(nextRoom != null){
                System.out.println("You notice the keyhole for the door seems rather intricate. As you inspect it, you suddenly feel the ground beneath you give way!");
                System.out.println("You fell through a trapdoor!");
                currentRoom.setDescription("You are on the north part of the wall connecting to the inner wall. To the west is a door leading to the inner courtyard, to the east is the north-east wall. There is also a trapdoor you fell through earlier.");            
                //currentRoom.setExit("down", small_room);
                currentRoom = nextRoom;
                roomhistory.clear();
                nextRoom = currentRoom.getExit("south");
                nextRoom.isUnlocked();
                System.out.println(currentRoom.getShortDescription());
                trap = false;
            }
        }
    }

    /**
     * "printInventory prints out the inventory"
     */
    private void printInventory() 
    {
        if (inventory.size() == 0) {
            switch(language){
                case ENGLISH:
                System.out.println("You have nothing in your inventory.");
                break;
                case NEDERLANDS:
                System.out.println("Je hebt niets in je inventaris.");
                break;
            }            
        }
        else {
            switch(language){
                case ENGLISH:
                System.out.println("You have the following:");
                break;
                case NEDERLANDS:
                System.out.println("Jij hebt het volgende:");
                break;
            }            
            for (String i : inventory.keySet()) {
                Item item = inventory.get(i);
                System.out.print(item.getName());
                if(item.getEquip() == true){
                    switch(language){
                        case ENGLISH:
                        System.out.println(" [Equipped]");
                        break;
                        case NEDERLANDS:
                        System.out.println(" [Gewapend]");
                        break;
                    }
                }
                System.out.print("\n");
            }
        }
    }

    /**
     * 
     * Prints out the description of the item, or throws an "error" when specified items wasn't found.
     * 
     */
    private void printItem(Command command) 
    {
        if(!command.hasSecondWord()) {          
            switch(language){
                case ENGLISH:
                System.out.println("Look at what item?");
                break;
                case NEDERLANDS:
                System.out.println("Kijk naar welk item?");
                break;
            }
            return;
        }
        String gItem = command.getSecondWord();
        if(inventory.get(gItem) == null){
            switch(language){
                case ENGLISH:
                System.out.println("You don't have that item.");
                break;
                case NEDERLANDS:
                System.out.println("You hebt dat item niet.");
                break;
            }
            return;
        }
        Item item = inventory.get(gItem);   
        System.out.println(item.getDescription());
    }

    /**
     * 
     * Prints out the players current stats, like their remaining/total HP, level, etc.
     * 
     */
    private void printStats() 
    {
        int xp = player.getXP();
        int xpcap;
        if(player.getlvl() == 1){
            xpcap = 5;
        }
        else if(player.getlvl() == 2){
            xpcap = 10;
        }
        else if(player.getlvl() == 3){
            xpcap = 20;
        }
        else if(player.getlvl() == 4){
            xpcap = 40;
        }
        else if(player.getlvl() == 5){
            xpcap = 80;
        }
        else{
            xpcap = 100;
        }
        System.out.println();
        System.out.println("Level: " + player.getlvl());
        System.out.println();
        System.out.println("HP: " + currentHP + "/" + player.getTotalHP());
        System.out.println("XP: " + xp + "/" + xpcap);
        System.out.println("STR: " + player.getStr());
        System.out.println("DEX: " + player.getDex());
        System.out.println("END: " + player.getEnd());
        System.out.println();
        System.out.println("DAM: " + player.getDamage());
        System.out.println("ARM: " + player.getArmor());
        System.out.println("INV: " + inventory.size() + "/" + player.getInvSpace());
        System.out.println();
    }

    /**
     * 
     * Looks for an item with an appropriate type, then uses it and removes it from the player's reach.
     * 
     */
    private void useItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            switch(language){
                case ENGLISH:
                System.out.println("Use what?");
                break;
                case NEDERLANDS:
                System.out.println("Gebruik wat?");
                break;
            }
            return;
        }
        String gItem = command.getSecondWord();
        if(inventory.get(gItem) == null){
            switch(language){
                case ENGLISH:
                System.out.println("You don't have that item.");
                break;
                case NEDERLANDS:
                System.out.println("You hebt dat item niet.");
                break;
            }
            return;
        }
        Item item = inventory.get(gItem);   
        if(item.getType() == "Health"){
            currentHP = player.getTotalHP();
            inventory.remove(gItem);
            switch(language){
                case ENGLISH:
                System.out.println("Consumed " + gItem + ". HP restored.");
                break;
                case NEDERLANDS:
                System.out.println(gItem + " Gebruikt. HP vol.");
                break;
            }
        }
        else if(item.getType() == "Teleporter"){
            if(teleportRoom == null){
                teleportRoom = currentRoom;
                System.out.println("Teleport set.");
            }
            else{
                currentRoom = teleportRoom;
                System.out.println(currentRoom.getShortDescription());
                System.out.println("Teleport unset.");
                teleportRoom = null;              
            }
        }
        else if(item.getType() == "Key1"){
            Room nextRoom = currentRoom.getExit("west");
            if(nextRoom.getLock()){
                nextRoom.isUnlocked();
                inventory.remove(gItem);
                switch(language){
                    case ENGLISH:
                    System.out.println("You unlocked the door to the inner courtyard. Key thrown away.");
                    break;
                    case NEDERLANDS:
                    System.out.println("Deur tot de binnenplaats geopened. Sleutel weggegooid.");
                    break;
                }
            }
        }
        else if(item.getType() == "Key2"){
            Room nextRoom = currentRoom.getExit("north");
            if(nextRoom.getLock()){
                nextRoom.isUnlocked();
                inventory.remove(gItem);
                switch(language){
                    case ENGLISH:
                    System.out.println("You unlocked the door to the tower. Keychain thrown away.");
                    break;
                    case NEDERLANDS:
                    System.out.println("Deur tot de toren geopened. Sleutelbos weggegooid.");
                    break;
                }                
            }
        }
        else{
            switch(language){
                case ENGLISH:
                System.out.println("Item not usable.");
                break;
                case NEDERLANDS:
                System.out.println("Item niet bruikbaar.");
                break;
            }
        }
    }

    /**
     * 
     * Removes the specified item from the player's inventory, and adds it the current room. Item is also unequipped if it was equipped before.
     * 
     */
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {          
            switch(language){
                case ENGLISH:
                System.out.println("Drop what?");
                break;
                case NEDERLANDS:
                System.out.println("Zet what neer?");
                break;
            }
            return;
        }
        String gItem = command.getSecondWord();
        if(inventory.get(gItem) == null){
            switch(language){
                case ENGLISH:
                System.out.println("You don't have that item.");
                break;
                case NEDERLANDS:
                System.out.println("You hebt dat item niet.");
                break;
            }
            return;
        }
        Item item = inventory.get(gItem);
        if(item.getEquip()){
            item.unEquipIt();
            equipment.remove(item.getType());
        }
        currentRoom.items.put(item.getName(), item);        
        inventory.remove(gItem);
        System.out.println("Dropped " + item.getName() + ".");
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
            if(fight.getEnemyHP() < 1){
                winFight();
                return;
            }
        }
        else{
            System.out.println(fight.getAction("youmiss"));
        }
        boolean enemyhits = fight.enemyHitChance();
        boolean block = player.blockChance();
        if(enemyhits == true){
            int damage = fight.getEnemyDam();
            damage -= player.getArmor();
            if(damage < 0){
                damage = 0; 
            }
            if(block){
                System.out.println(fight.getAction("enemyhit"));
                System.out.println("Damage blocked with shield!");
                fight.addTurn();
            }
            else{
                currentHP -= damage;            
                System.out.println(fight.getAction("enemyhit"));
                System.out.println("Enemy dealt " + damage + " damage.");               
                if(currentHP < 1){
                    loseFight();
                    return;
                }
                fight.addTurn();
            }            
        }
        else{
            System.out.println(fight.getAction("enemymiss"));
            fight.addTurn();
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
            if(player.getTotalHP() > currentHP){
                currentHP += 1;
            }
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
        else if(boss){
            System.out.println("You have slain the evil king! Rejoice in your victory!\nGAME OVER");
            gameOver = true;        
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
            gameOver = true;
        }
    }

    private void take(Command command)
    {   
        if(currentRoom.items.size() < 1){
            System.out.println("No items here.");
        }
        else{
            if(!command.hasSecondWord()){
                int size = inventory.size();
                size += currentRoom.items.size();
                if(size <= player.getInvSpace()){
                    for (String i : currentRoom.items.keySet()) {                    
                        Item item = currentRoom.items.get(i);
                        inventory.put(item.getName(), item);
                    }
                    currentRoom.items.clear();
                    System.out.println("All items taken.");
                }
                else{
                    System.out.println("Can't carry all items!");
                }
            }
            else{
                String gItem = command.getSecondWord();
                if(currentRoom.items.get(gItem) != null){
                    if(inventory.size() < player.getInvSpace()){
                        Item item = currentRoom.items.get(gItem);
                        inventory.put(gItem, item);
                        currentRoom.items.remove(gItem);
                        System.out.println("You have taken the " + item.getName() + ".");
                    }
                    else{
                        System.out.println("Can't carry any more items!");
                    }
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
