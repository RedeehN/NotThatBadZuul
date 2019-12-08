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

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class GameEngine
{
    private Parser        aParser;
    private UserInterface aGui;
    private HashMap<String,Room> aEnsembleRoom;
    private Player         aPlayer;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aPlayer = new Player("nom du joueur"); 
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
     * Message de démarrage du jeu.
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
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
    }

    /**
     * Procedure qui initialise des Rooms et les lient entre elles.
     */
    private void createRooms()
    {
        Room vRestaurantDebut = new Room("dans le restaurant d'un chef cuisinier ou vous allez vous faire cuisiner","deuxiemeVersionArendre/Images/interieurRestaurant.png");
        Room vRueJaponaiseHub = new Room("dans la rue principal : le hub","deuxiemeVersionArendre/Images/hub.png");
        Room vRueJaponaise2 = new Room("dans une rue parallele a la rue principal","deuxiemeVersionArendre/Images/Rue2PartieEast.png");
        Room vRueJaponaise3 = new Room("work in progress","images/rue4.png");
        Room vRueJaponaise4 = new Room("dans une rue parallele a la rue principal direction la plage","deuxiemeVersionArendre/Images/TEST1.png");
        Room vMaisonItemRue2 = new Room("dans la maison du senpai","deuxiemeVersionArendre/Images/test2.gif");
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
        vRestaurantDebut.addItem("potion","Potion de vie",10,"Cet item vous redonne de la vie",20);
        vRestaurantDebut.addItem("eau","Eau Sacré",5,"Cet item vous redonne de la vie",5);
        vRestaurantDebut.addItem("magicCookie","Magic Cookie",10,"Cet item augemente le poids maximal que vous pouvez porter",0);

        vMaisonItemRue2.addItem("couteau","Couteau de cuisine du senpai : Roi des poulpes", 20,"Cet Item vous donne une nouvelle attaque",0);

        // creation hashMap de l'ensemble des room disponibles
        this.aEnsembleRoom.put("interieur Restaurant", vRestaurantDebut);
        this.aEnsembleRoom.put("hub", vRueJaponaiseHub);
        this.aEnsembleRoom.put("rue 2", vRueJaponaise2);
        this.aEnsembleRoom.put("rue 3", vRueJaponaise3);
        this.aEnsembleRoom.put("rue 4", vRueJaponaise4);
        this.aEnsembleRoom.put("maison item 1", vMaisonItemRue2);

        //initialiser le lieu courant 
        this.aPlayer.move(vRestaurantDebut);
        //this.aAncienneRoom = this.aCurrentRoom;
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
            this.eat(vCommand);
        else if ( vCommandWord.equals( "back" ) )
            this.back(vCommand);
        else if ( vCommandWord.equals( "test" ) )
            this.test(vCommand);
        else if ( vCommandWord.equals( "take" ) )
            this.take(vCommand);
        else if (vCommandWord.equals("items"))
            this.inventaire();
        else if (vCommandWord.equals("status"))
            this.status();
        else if ( vCommandWord.equals( "drop" ) )
            this.drop(vCommand);
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
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "Impossible d'aller dans cette direction" );
        else {
            this.aPlayer.move(vNextRoom);
            printLocationInfo();
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    }

    private void test(final Command pCommand){
        if (pCommand.hasSecondWord()){
            try {
                Scanner vSr = new Scanner(new File("deuxiemeVersionArendre/"+pCommand.getSecondWord()+".txt"));
                //PrintWriter pPw = new PrintWriter("deuxiemeVersionArendre/"+pCommand.getSecondWord()+".txt");
                String line;
                while(vSr.hasNextLine()){
                    line = vSr.nextLine();
                    this.interpretCommand(line);
                }
            }catch(FileNotFoundException pExc){
                this.aGui.println("le fichier n'a pas pu etre ouvert ou lu");
            }
        }
        else {
            this.aGui.println("quel fichier ?");
        }

    }

    private void back(final Command pCommand){
        if (pCommand.hasSecondWord())
            this.aGui.println("Retourner ou ?");
        else {
            this.aPlayer.backPlayer();
            this.printLocationInfo();
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    }

    private void take(final Command pCommand){
        if(!pCommand.hasSecondWord())  
            this.aGui.println("Prendre quoi ?");
        else{
            String vItemTake = pCommand.getSecondWord();
            if(this.aPlayer.getCurrentRoom().getItem(vItemTake) != null){
                if (!this.aPlayer.canBeTake(vItemTake)) {
                    this.aGui.println("L'item est trop lord pour que vous puissiez le prendre");
                    return;
                }
                this.aPlayer.playerTake(vItemTake);
                this.aGui.println("Vous avez bien pris l'Item."); 
            }
            else{
                this.aGui.println("Cet objet n'est pas présent dans cette piece!");
            }
        }
    }

    private void drop(final Command pCommand){
        if(!pCommand.hasSecondWord())   this.aGui.println("Enlever quoi ?");
        else{     
            String vItemDrop = pCommand.getSecondWord();
            if(this.aPlayer.getItem(vItemDrop) != null){
                this.aPlayer.playerDrop(vItemDrop);
                this.aGui.println("L'item a bien été déposé.");
            }
            else this.aGui.println("L'Item que vous voulez déposer n'existe pas dans votre inventaire.");
        }
    }

    private void inventaire(){
        this.aGui.println(this.aPlayer.getItemsName());
    }

    private void endGame()
    {
        this.aGui.println( "Merci d'avoir joué." );
        this.aGui.enable( false );
    }

    private void look(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
        } else{
            String vSecondWord = pCommand.getSecondWord();
            this.aGui.println(this.aPlayer.getCurrentRoom().getItemDescription(vSecondWord));
        }
    }

    private void eat(final Command pCommand)
    {
        if(pCommand.hasSecondWord()){
            String vItemName = pCommand.getSecondWord();
            if(this.aPlayer.getItem(vItemName) != null)
                if(vItemName.equals("magicCookie") || vItemName.equals("potion")){
                    this.aPlayer.playerEat(vItemName);
                    this.aGui.println("Vous avez consomé votre Item");
                }else 
                    this.aGui.println("Vous ne pouvez pas consomer cette Item");
            else this.aGui.println("Cette Item n'est pas dans votre inventaire !");
        }else
            this.aGui.println("Vous avez mangé. Vous n'avez plus fain");
    }

    private void status(){
        this.aGui.println(this.aPlayer.playerStatus());
    }

    private void printLocationInfo(){

        //afficher le lieu courant
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription());
    }

}
