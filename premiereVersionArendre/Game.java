package premiereVersionArendre;
/**
 * Classe Game est la classe principale du jeu. Elle est chargé de lancer le jeu
 */
public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;

    /**
     * Constructeur class Game() elle initialise la map
     */
    public Game()
    {
        this.aParser = new Parser();
        this.createRooms();
    }

    /**
     * Procedure qui initialise des Rooms
     */
    private void createRooms()
    {
        //Declaration des 5 lieux
        Room vRestaurantDebut = new Room("Vous etes dans le restaurant d'un chef cuisinier ou vous allez vous faire cuisiner");
        Room vRueJaponaiseHub = new Room("Vous etes dans la rue principal : le hub");
        Room vRueJaponaise2 = new Room("Vous etes dans une rue parallele a la rue principal");
        Room vRueJaponaise3 = new Room("work in progress");
        Room vRueJaponaise4 = new Room("Vous etes dans une rue parallele a la rue principal direction la plage");
        Room vMaisonItemRue2 = new Room("vous etes dans la maison du senpai");
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
        //initialiser le lieu courant 
        this.aCurrentRoom = vRestaurantDebut;
    }//createRooms()

    /**
     * Procedure qui lance le jeu
     */
    public void play()
    {
        printWelcome();
        
        boolean vFinished=false;
        Command vCommand;
        while (!vFinished)
        {
            vCommand = this.aParser.getCommand();
            vFinished = processCommand(vCommand);
        }
        System.out.println("Merci d'avoir joué.");
    }

    /**
     * Procedure qui permet le déplacement dans le jeu
     * @param pCommande commande des actions
     */
    private void goRoom(final Command pCommande)
    {
        if (!pCommande.hasSecondWord()) {
            System.out.println("Aller ou ?");
            return;
        }

        String vDirection = pCommande.getSecondWord();

        // on change de room
        Room vNextRoom = aCurrentRoom.getExit(vDirection);

        if (vNextRoom == null) System.out.println("Impossible d'aller dans cette direction");
        else{
            //changer de lieu
            this.aCurrentRoom = vNextRoom;

            // afficher le lieu
            printLocationInfo();
        }
    }

    /**
     * Message de démarrage du jeu
     * 
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help. \n");
        printLocationInfo();

    }// printWelcome()

    /**
     * Affiche l'aide 
     */
    private void printHelp()
    {
        System.out.println("Vous etes poursuivie par des cuisiniers.");
        System.out.println("Vous voulez rejoindre votre famille.");
        System.out.println("Ne Paniquez pas!!! \\o/.\n");
        System.out.println("Vos commandes sont :");
        System.out.println("\t"+this.aParser.showCommands());
    }//printHelp()

    /**
     * Quitte le jeu
     * @param pCommand commande qui permet de quitter 
     * @return un boolean (true) si on quitte
     */
    private boolean quit(final Command pCommand)
    {
        if(pCommand.getSecondWord()!=null) {System.out.println("Quit what?"); return false;}
        else return true;
    }//quit()

    /**
     * Méthode qui appelle la bonne méthode en fonction de la commande passée en paramètre
     * @param pCommand
     * @return un boolean (true) si la commande est quit
     */
    private boolean processCommand(final Command pCommand)
    {
        if(!pCommand.isUnknown()){
            switch (pCommand.getCommandWord())
            {
                case "quit":
                return quit(pCommand);

                case "go":
                this.goRoom(pCommand);
                return false;

                case "help":
                this.printHelp();
                return false;

                case "look":
                this.look();
                return false;

                case "eat":
                this.eat();
                return false;

                default:
                return false;
            }
        }
        else{
            System.out.println("I don't know what you mean...");
            return false;
        }
    }

    private void look()
    {
        System.out.println(this.aCurrentRoom.getLongDescription());
    }

    private void eat()
    {
        System.out.println("Vous avez mangé. Vous n'avez plus fain");
    }

    private void printLocationInfo(){

        //afficher le lieu courant
        System.out.println(this.aCurrentRoom.getLongDescription());
    }
} // Game
