/**
 * @file: GameController.java
 * @Author: Lin Rozenszajn - rozensl
 * @Date: April 16, 2021
 * @Description: Controller module that handles inputs and links model and view module
 */

package a4;

public class GameController {
	
	// State variable for a Game Controller
	private static GameController controller = null;

	/**
     * @brief constructor
     * @param model - model module (BoardT)
     */
	private GameController(Board model) {}

    /**
     * @brief public static method for obtaining a single instance
     * @param model - model module (BoardT)
     * @return the single GameController object
     */	
	public static GameController getInstance(Board model) {
		if (controller == null) {
			controller = new GameController(model);
		}
		return controller;
	}
	
	/**
     * @brief Calls on the Board object to begin the game
     */
	void startGame() {
		Board.startGame();
    }
	
	/**
     * @brief Calls on the Board object to move
     * @param x Integer value representing horizontal movement
     * @param y Integer value representing vertical movement
     */
	public void move(int x, int y) {
        Board.move(x, y);
    }
 
	/**
     * @brief Passes an up-direction move to the Board
     */
    public void moveUp() {
        Board.move(0, -1);
    }
 
    /**
     * @brief Passes a down-direction move to the Board
     */
    public void moveDown() {
        Board.move(0, 1);
    }
 
    /**
     * @brief Passes a left direction move to the Board
     */
    public void moveLeft() {
        Board.move(-1, 0);
    }
 
    /**
     * @brief Passes a right direction move to the Board
     */
    public void moveRight() {
        Board.move(1, 0);
    }
 
    /**
     * @brief Tells the Board to change the merged status of all tiles on the Board object to false
     */
    public void clearMerged() {
        Board.clearMerged();
    }
 
    /**
     * @brief Interacts with Board to check available moves
     * @return Result of Board's movesAvailable() method
     */
    public boolean movesAvailable() {
        return Board.movesAvailable();
    }
    
    /**
     * @brief Interacts with Board to retrieve gamestate
     * @return Result of Board's getGamestate() method
     */
    public Board.State getGamestate() {
    	return Board.getGamestate();
    }

}
