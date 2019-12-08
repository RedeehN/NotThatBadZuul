package deuxiemeVersionArendre;

/**
 * Décrivez votre classe Item ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Item
{
    private String aId;
    private String aItemName;
    private double aPoids;
    private String aDescription;
    private int    aGainVie;
    
    public Item(final String pId, final String pItemName, final double pPoids, final String pDescription, final int pGainVie)
    {
        this.aId = pId;
        this.aItemName = pItemName;
        this.aDescription = pDescription;
        this.aPoids = pPoids;
        this.aGainVie = pGainVie;
    }

    public String getLongItemDescription(){
        return "Item :\t"+this.aItemName +"\n\tEst un Objet de poids :"+this.aPoids +"kg\n\t" + this.aDescription
                    + "\n\tSa capaciter à vous donner de la vie est : "+this.aGainVie+" pv";
    }

    public String getCourteItemDescription(){
        return "Item :\t"+this.aItemName + " \n\t\tDescription : " +this.aDescription;
    }
    public String getId(){
        return this.aId;
    }
    public String getItemName(){
        return this.aItemName;
    }

    public double getPoids(){
        return this.aPoids;
    }

    public String getDescription(){
        return this.aDescription;
    }

    public int getGainVie(){
        return this.aGainVie;
    }
}
