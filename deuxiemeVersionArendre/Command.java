package deuxiemeVersionArendre;

 
/**
 * Classe Command qui gère les commandes de l'utilisateur
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;

    /**
     * Constructuer de commande
     * @param pCommandWord
     * @param pSecondWord
     */
    public Command(final String pCommandWord,final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;

    }

    /**
     * Accesseur de commandWord
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    }

    /**
     * Modificateur de secondWord()
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }

    /**
     * On regarde si la commande a deux mots
     * @return boolean s'il y a un deuxieme mot
     */
    public boolean hasSecondWord()
    {
        return this.aCommandWord!=null && this.aSecondWord!=null;
    }

    /**
     * On regarde si la commande est connue
     * @return boolean
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    }
    

} // Command
