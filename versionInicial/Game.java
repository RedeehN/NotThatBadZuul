package versionInicial;

public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;
    public Game()
    {
        this.aParser = new Parser();
        this.createRooms();
    }

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
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void createRooms()
    {
        //Declaration des 5 lieux
        Room vOutside = new Room("outside the main entrance of the university");
        Room vTheatre = new Room("in a lecture theatre");
        Room vPub = new Room("in the campus pub");
        Room vLab = new Room("in a computing lab");
        Room vOffice = new Room("in the computing admin office");

        //positionner les sorties pour créer le "réseau"
        vOutside.setExits(null,vTheatre,vLab,vPub);
        vTheatre.setExits(null,null,null,vOutside);
        vPub.setExits(null,vOutside,null,null);
        vLab.setExits(vOutside,vOffice,null,null);
        vOffice.setExits(null,null,null,vLab);

        //initialiser le lieu courant 
        this.aCurrentRoom = vOutside;
    }//createRooms()

    private void goRoom(final Command pCommande)
    {
        if (!pCommande.hasSecondWord()) {
            System.out.println("Go where ?");
            return;
        }

        Room vNextRoom= null;
        String vDirection = pCommande.getSecondWord();

        switch (vDirection)
        {
            case "north":
            // System.out.println("nord");
            vNextRoom = this.aCurrentRoom.aNorthExit;
            break;
            case "east":
            vNextRoom = this.aCurrentRoom.aEastExit;
            // System.out.println("east");
            break;
            case "south":
            vNextRoom = this.aCurrentRoom.aSouthExit;
            // System.out.println("south");
            break;
            case "west":
            vNextRoom = this.aCurrentRoom.aWestExit;
            // System.out.println("west");
            break;
            default:
            System.out.println("Unknown direction !");
            break;
        }
        if (vNextRoom == null) System.out.println("There is no door !");
        else{
            //changer de lieu
            this.aCurrentRoom = vNextRoom;

            //afficher le lieu courant
            System.out.println(this.aCurrentRoom.getDescription());

            //afficher les sorties disponibles
            if (!(this.aCurrentRoom.aEastExit==null)){
                System.out.println("sortie disponible east");
            }
            if (!(this.aCurrentRoom.aNorthExit==null)){
                System.out.println("sortie disponible north");
            }
            if (!(this.aCurrentRoom.aSouthExit==null)){
                System.out.println("sortie disponible south");
            }
            if (!(this.aCurrentRoom.aWestExit==null)){
                System.out.println("sortie disponible west");
            }
        }
    }

    /**
     * message de début
     * 
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help. \n");
        System.out.println("You are outside the main entrance of the university");
        System.out.println("Exits: east south west");

    }// printWelcome()

    /**
     * affiche l'aide 
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone.");
        System.out.println("You wander around at the university.\n");
        System.out.println("Your command words are:\n\tgo quit help");
    }//printHelp()

    /**
     * quit le jeu
     */
    private boolean quit(final Command pCommand)
    {
        if(pCommand.getSecondWord()!=null) {System.out.println("Quit what?"); return false;}
        else return true;
    }//quit()

    /**
     * appeler la bonne méthode en fonction de la commande passée en paramètre
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
                default:
                return false;
            }
        }
        else{
            System.out.println("I don't know what you mean...");
            return false;
        }
    }
} // Game
