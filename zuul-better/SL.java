import java.util.HashMap;
/**
 * Stores strings for different languages to be used in the game.
 *
 * @author Quinten de Haan
 * @version 2020.1.24
 */
public class SL
{
    // instance variables - replace the example below with your own
    public HashMap<SLe, String> nlstrings;
    public HashMap<SLe, String> enstrings;

    /**
     * Constructor for objects of class SL
     */
    public SL()
    {
        nlstrings = new HashMap<SLe, String>();
        enstrings = new HashMap<SLe, String>();

        enstrings.put(SLe.WELCOME, "Welcome to the World of Zuul!\nWorld of Zuul is a text adventure game.\nType 'start' if you'd like to start the game. Type 'help' for commands. Type 'about' for more info.\n");
        nlstrings.put(SLe.WELCOME, "Welcome tot de World of Zuul!\nWorld of Zuul is een text adventure spel.\nTyp 'start' als je het spel wilt beginnen. Typ 'help' voor opdrachten en meer.\n");
        
        enstrings.put(SLe.WHAT, "I don't know what you mean.\n");
        nlstrings.put(SLe.WHAT, "Ik weet niet wat je bedoelt.\n");
        
        enstrings.put(SLe.R_OUTSIDE, "You're in front of the castle. To the north is the castle gate, to the west is a training ground.");
        nlstrings.put(SLe.R_OUTSIDE, "Je staat voor het kasteel. Noord is de kasteel port, naar het westen is een trainingsveld.");
        
        enstrings.put(SLe.R_TRAIN, "You are in a training ground. There is a soldier tending to his equipment. East is the castle gate.");
        nlstrings.put(SLe.R_TRAIN, "Je staat op een trainingsveld. Er is een soldaat bezig met zijn uitrusting. Oost is de kasteel port.");
        
        enstrings.put(SLe.R_GATE, "You are in the gatehouse of the outer wall. North is a courtyard, east are stairs leading to the top of the wall.");
        nlstrings.put(SLe.R_GATE, "Je bent in het wachthuis van de buitenmuur. Noord is een binnenhof, oost is een trap naar de top van de muur.");
        
        enstrings.put(SLe.R_OUTYARD, "You're in the courtyard of the castle. North is a raised drawbridge, east is a stone building, west is a wooden building, south is the gatehouse.");
        nlstrings.put(SLe.R_OUTYARD, "Je bent in het binnenhof van het kasteel. Noord is een omhooggetrokken klapbrug, oost is een stenen gebouw, west is een houten gebouw, zuid is het wachthuis.");
        
        enstrings.put(SLe.R_SWALL, "You're on the castle's southern wall. East is further along the wall, west is the gatehouse");
        nlstrings.put(SLe.R_SWALL, "Je bent op de zuiderlijke muur. Oost is meer van de muur, west is het wachthuis.");
        
        enstrings.put(SLe.R_SEWALL, "You're on the castle's south-eastern wall. North is further along the wall, south is towards the gatehouse");
        nlstrings.put(SLe.R_SEWALL, "Je bent op de zuidoosterlijke muur. Noord is meer van de muur, zuid is richting het wachthuis.");
        
        enstrings.put(SLe.R_NEWALL, "You're on the castle's north-eastern wall. West is towards the inner wall, south is back along the wall.");
        nlstrings.put(SLe.R_NEWALL, "Je bent op de noord-oosterlijke muur. West is richting de binnenmuur, zuid is terug over de muur.");
        
        enstrings.put(SLe.R_NWALL, "You are at inner wall. West is a door leading to the inner courtyard,  east is the north-east wall.");
        nlstrings.put(SLe.R_NWALL, "Je bent bij de binnenmuur. West is een deur tot the centrale binnenhof, oost is terug over de muur.");
        
        enstrings.put(SLe.R_WWALL, "You are on the west wall. North leads to a tower, east is a window to the warehouse.");
        nlstrings.put(SLe.R_WWALL, "Je bent op de westerlijke muur. Noord is richting een toren, oost is een open raam naar het warenhuis.");
        
        enstrings.put(SLe.R_INYARD, "You in the central courtyard. North is the castle keep, east is a door leading to the outer wall.");
        nlstrings.put(SLe.R_INYARD, "Je bent in the centrale binnenhof. Noord is de burcht, oost is een deur naar de buitenmuur.");
        
        enstrings.put(SLe.R_SMALL, "You are in a small room with some stuff. Above you is the trapdoor, too high to climb through. South is a door.");
        nlstrings.put(SLe.R_SMALL, "Je bent in een kleine kamer met wat spullen. Noord is de burcht, zuid is een deur.");
        
        enstrings.put(SLe.R_ARM, "You are in the armoury. To the north is a small room, to the west is a now-unlocked door.");
        nlstrings.put(SLe.R_ARM, "Je bent in een wapenkamer. Noord is een kleine kamer, west is een net geopened deur.");
        
        enstrings.put(SLe.R_NWWALL, "You are on the north-western wall. North is a tower door, south is back along the wall.");
        nlstrings.put(SLe.R_NWWALL, "Je bent op de noord-westerlijke muur. Noord is een deur tot de toren, zuid is terug over de muur.");
        
        enstrings.put(SLe.R_TOWER, "You are in the tower. To the south is the door to the western wall.");
        nlstrings.put(SLe.R_TOWER, "Je bent in de toren. Zuid is de deur naar de westerlijke muur.");
        
        enstrings.put(SLe.R_WHOUSE, "You are in a warehouse. To the east is a ladder up, west leads out into the courtyard.");
        enstrings.put(SLe.R_WHBED, "You are on the warehouse's higher floor. To the east is window to the wall, west is back down the ladder.");
    }
}
