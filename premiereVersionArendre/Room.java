package premiereVersionArendre;
import java.util.HashMap;
import java.util.Set;
/**
 * Classe Room qui gère les différentes pièces du jeu
 */
public class Room
{
    private String  aDescription;
    private HashMap<String, Room> aExits;

    /**
     * Constructeur class Room
     * @param pDescription
     */
    public Room(final String pDescription)
    {
        this.aDescription=pDescription;
        aExits = new HashMap<String,Room>();
    }

    /**
     * Fonction qui renvoie la description de la Room
     * @return aDescription la description de la Room
     */
    public String getDescription()
    {
        return this.aDescription;
    }

    /**
     * Procedure qui cree les liens entre les rooms 
     * @param pDirection
     * @param pNeighbor
     */
    public void setExits(final String pDirection, final Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    }

    /**
     * Fonction qui renvoie la bonne room selon la direction désirer
     * @param pDirection
     * @return une room
     */
    public Room getExit(final String pDirection)
    {
        return aExits.get(pDirection);
    }

    /**
     * Renvoie une description des sorties de la 
     * piece, par exmple, "Sorties : north west".
     * @return Une description des sorties possibles
     */
    public String getExitString()
    {
        String vSorties = "Sorties : ";
        Set<String> keys = aExits.keySet();
        //afficher les sorties disponibles
        for(String exit : keys){
            vSorties+=" "+exit;
        }
        return vSorties;
    }
    
    /**
     * Renvoie une longue description de cette Room sous la forme:
     *      Vous êtes dans la cuisine.
     *      Exits : north west
     * @return Une description de la room avec ces sorties
     */
    public String getLongDescription()
    {
        return "Vous êtes " + this.aDescription + ".\n" + getExitString();
    }
    
    
    
    
    
} // Room
