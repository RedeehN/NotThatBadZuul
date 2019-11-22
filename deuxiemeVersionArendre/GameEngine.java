package deuxiemeVersionArendre;
/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */

import java.util.HashMap;

public class GameEngine
{
    private Parser        aParser;
    private Room          aCurrentRoom;
    private UserInterface aGui;

    private HashMap<String,Room> aEnsembleRoom;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {

        this.aParser = new Parser();
        this.aEnsembleRoom = new HashMap<String,Room>();
        this.createRooms();
    }

    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.print( "\n" );
        this.aGui.println( "Bienvenue dans le jeu /// nom poulple ou titre" );
        this.aGui.println( "Vous venez de vous reveiller dans la cuisine d'un cuisinier japonais" );
        this.aGui.println( "Vite! Essayez de vous en fuire  sinon vous aller vous faire cuisiner!!" );
        this.aGui.println( "Tape 'help' si tu en a besoin !" );
        this.aGui.print( "\n" );
        printLocationInfo();
        if ( this.aCurrentRoom.getImageName() != null )
            this.aGui.showImage( this.aCurrentRoom.getImageName() );
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room vRestaurantDebut = new Room("dans le restaurant d'un chef cuisinier ou vous allez vous faire cuisiner","Images/interieurRestaurant.png");
        Room vRueJaponaiseHub = new Room("dans la rue principal : le hub","Images/hub.png");
        Room vRueJaponaise2 = new Room("dans une rue parallele a la rue principal","Images/Rue2PartieEast.png");
        Room vRueJaponaise3 = new Room("work in progress","images/rue4.png");
        Room vRueJaponaise4 = new Room("dans une rue parallele a la rue principal direction la plage","Images/TEST1.png");
        Room vMaisonItemRue2 = new Room("dans la maison du senpai","Images/test2.gif");
        //positionner les sorties pour créer le "réseau"
        vRestaurantDebut.setExits("south",vRueJaponaiseHub);

        vRueJaponaiseHub.setExits("north", vRestaurantDebut);
        vRueJaponaiseHub.setExits("east", vRueJaponaise2);
        vRueJaponaiseHub.setExits("south", vRueJaponaise3);
        vRueJaponaiseHub.setExits("west", vRueJaponaise4);

        vRueJaponaise2.setExits("west",vRueJaponaiseHub);
        vRueJaponaise2.setExits("down", vMaisonItemRue2);

        vRueJaponaise3.setExits("north",vRueJaponaiseHub);

        vRueJaponaise4.setExits("east",vRueJaponaiseHub);

        vMaisonItemRue2.setExits("up", vRueJaponaise2);

        // Attribution des Items
        vRestaurantDebut.addItem("potion","Potion de vie",0.1,"Cet item vous redonne de la vie",20);
        vRestaurantDebut.addItem("eau","Eau Sacré",0.1,"Cet item vous redonne de la vie",5);

        vMaisonItemRue2.addItem("couteau","Couteau de cuisine du senpai : Roi des poulpes", 0.2,"Cet Item vous donne une nouvelle attaque",0);

        // creation hashMap de l'ensemble des room disponibles
        this.aEnsembleRoom.put("interieur Restaurant", vRestaurantDebut);
        this.aEnsembleRoom.put("hub", vRueJaponaiseHub);
        this.aEnsembleRoom.put("rue 2", vRueJaponaise2);
        this.aEnsembleRoom.put("rue 3", vRueJaponaise3);
        this.aEnsembleRoom.put("rue 4", vRueJaponaise4);
        this.aEnsembleRoom.put("maison item 1", vMaisonItemRue2);

        //initialiser le lieu courant 
        this.aCurrentRoom = vRestaurantDebut;
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine );
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "Je ne sais pas ce que vous voulez dire..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "look" ) )
            this.look(vCommand);
        else if ( vCommandWord.equals( "eat" ) )
            this.eat();
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {

        this.aGui.println("Vous etes poursuivie par des cuisiniers.");
        this.aGui.println("Vous voulez rejoindre votre famille.");
        this.aGui.println("Ne Paniquez pas!!! \\o/.\n");
        this.aGui.println("Vos commandes sont :");
        this.aGui.println("\t"+this.aParser.getCommandString());

    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom( final Command pCommand ) 
    {
        if ( ! pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "Aller ou ?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vNextRoom = this.aCurrentRoom.getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "Impossible d'aller dans cette direction" );
        else {
            this.aCurrentRoom = vNextRoom;
            printLocationInfo();
            if ( this.aCurrentRoom.getImageName() != null )
                this.aGui.showImage( this.aCurrentRoom.getImageName() );
        }
    }

    private void endGame()
    {
        this.aGui.println( "Merci d'avoir joué." );
        this.aGui.enable( false );
    }

    private void look(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aGui.println(this.aCurrentRoom.getLongDescription());
        } else{
            String vSecondWord = pCommand.getSecondWord();
            this.aGui.println(this.aCurrentRoom.getItemDescription(vSecondWord));
        }
    }

    private void eat()
    {
        this.aGui.println("Vous avez mangé. Vous n'avez plus fain");
    }

    private void printLocationInfo(){

        //afficher le lieu courant
        this.aGui.println( this.aCurrentRoom.getLongDescription());
    }

}
