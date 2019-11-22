package versionInicial;


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
     */
    public boolean hasSecondWord()
    {
        return this.aCommandWord!=null && this.aSecondWord!=null;
    }

    /**
     * on regarde si la commande est connue
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    }
} // Command
