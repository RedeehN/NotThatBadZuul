package deuxiemeVersionArendre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
/**
 * Classe Room qui gère les différentes pièces du jeu
 */
public class Room
{
    private String                  aDescription;
    private HashMap<String, Room>   aExits;
    private String                  aImageName;
    //    private Item                    aItem;
    private ArrayList<Item>         aItems;

    /**
     * Constructeur class Room
     * @param pDescription
     */
    public Room(final String pDescription,final String pImage)
    {
        this.aDescription=pDescription;
        this.aImageName = pImage;
        aExits = new HashMap<String,Room>();
        aItems = new ArrayList<Item>();
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
        this.aExits.put(pDirection, pNeighbor);
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
        Set<String> keys = this.aExits.keySet();
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
        if (this.aItems.isEmpty())
            return "Vous êtes " + this.aDescription + ".\n" + this.getExitString() + "\nItems : Aucun item ici ";
        else 
        {   
            String vItemDescription = "";

            for(Item item : this.aItems){
                vItemDescription += item.getCourteItemDescription()+"\n";
            }
            return "Vous êtes " + this.aDescription + ".\n" + this.getExitString() + "\n" + vItemDescription;
        }
    }

    /**
     * Return a string describing the room's image name
     */
    public String getImageName()
    {
        return this.aImageName;
    }

    /*
    private void setItem(final String pId, final String pItemName, final double pPoids, final String pDescription, final int pGainVie){
    this.aItem = new Item(pId, pItemName,pPoids,pDescription,pGainVie);
    }*/

    public void addItem(final String pId, final String pItemName, final double pPoids, final String pDescription, final int pGainVie){
        this.aItems.add(new Item(pId, pItemName,pPoids,pDescription,pGainVie));
    }

    public String getItemDescription(String pNomItem){
        if (this.aItems.isEmpty()) return "Aucun objet est présent dans cette piece";
        else {
            String vItemDescription = "";
            for (Item item : this.aItems){
                if (pNomItem.equalsIgnoreCase(item.getId()))
                    return item.getLongItemDescription();
            }
            return "Cet Item n'est pas présent dans cette piece ";
        }
    }
} // Room
