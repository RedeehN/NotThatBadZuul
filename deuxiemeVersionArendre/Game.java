
package deuxiemeVersionArendre;

/**
 * Classe Game est la classe principale du jeu. Elle est chargé de lancer le jeu
 */
public class Game
{
	private UserInterface aGui;
	private GameEngine aEngine;

    /**
     * Create the game and initialise its internal map. Create the inerface and link to it.
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
} // Game
