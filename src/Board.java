/**
 * @File: Board.java
 * @Author: Lin Rozenszajn - rozensl
 * @Date: April 16, 2021
 * @Description: Using the model, view controller schema: this is the model module for storing the state and logic of the game
 */

public class Board {
	
    static TileT[][] gameboard;
    private static int side = 4;
	private static Board model = null;
	private static int target_value = 2048;
    private static int top_value;
    static boolean checkingAvailableMoves;
    private static State gamestate = State.start;
	static enum State {
        start, won, running, over
    }
    
	/**
     * @brief constructor for a Board
     */
    private Board() {}
    
    /**
     * @brief Method for obtaining a single instance of a Board
     * @return A Board object assigned to state variable model
     */
    public static Board getInstance() {
    	if (model == null) {
    		return model = new Board();
    	}
    	return model;
    }
	
    /**
     * @brief Method to begin the game
     * @details Game is begun by changing its state to running, initializing the value
     * of the top tile on the board to 0 and adding the first two tiles (of random value) to 
     * the board.
     */
	static void startGame() {
        if (gamestate != State.running) {
            top_value = 0;
            gamestate = State.running;
            gameboard = new TileT[getSide()][getSide()];
            UserInterface.addRandomTile();
            UserInterface.addRandomTile();
        }
    }
	
	/**
     * @brief Method for game moves
     * @param x An integer value signifiying horizontal movement
     * @param y An integer value for vertical movement
     * @return A boolean flag indicating a successful move
     * @details A move consists of moving every tile on the board as far in the chose direction
     * as possible, then checking for tiles that are capable of merging. All tiles that can 
     * merge merge. After merging is complete, the method checks for available moves. If none
     * are available, the game is over. Otherwise, a random tile is generated. If the top
     * value tile on the board is equal to the target value, the game is won.
     */
	public static boolean move(int x, int y) {
        boolean moved = false;
        int countDownFrom = 0;
		if (x != -1 && y != -1) {
        	countDownFrom = getSide() * getSide() - 1;
        }
 
        for (int i = 0; i < getSide() * getSide(); i++) {
            int j = Math.abs(countDownFrom - i);
 
            int r = j / getSide();
            int c = j % getSide();
 
            if (gameboard[r][c] == null)
                continue;
 
            int nextR = r + y;
            int nextC = c + x;
 
            while (nextR >= 0 && nextR < getSide() && nextC >= 0 && nextC < getSide()) {
 
                TileT next = gameboard[nextR][nextC];
                TileT curr = gameboard[r][c];
 
                if (next == null) {
 
                    if (checkingAvailableMoves)
                        return true;
 
                    gameboard[nextR][nextC] = curr;
                    gameboard[r][c] = null;
                    r = nextR;
                    c = nextC;
                    nextR += y;
                    nextC += x;
                    moved = true;
 
                } else if (next.canMergeWith(curr)) {
 
                    if (checkingAvailableMoves)
                        return true;
 
                    int value = next.mergeWith(curr);
                    if (value > top_value)
                        top_value = value;
                    gameboard[r][c] = null;
                    moved = true;
                    break;
                } else
                    break;
            }
        }
 
        if (moved) {
            if (top_value < target_value) {
                clearMerged();
                UserInterface.addRandomTile();
                if (!movesAvailable()) {
                    gamestate = State.over;
                }
            } else if (top_value == target_value)
                gamestate = State.won;
        }
 
        return moved;
    }
 
	/**
     * @brief Method for initating a move in the up direction
     * @return The result of move() in the up direction
     */
    static boolean moveUp() {
        return move(0, -1);
    }
 
    /**
     * @brief Method for initating a move in the down direction
     * @return The result of move() in the down direction
     */
    static boolean moveDown() {
        return move(0, 1);
    }
 
    /**
     * @brief Method for initating a move in the left direction
     * @return The result of move() in the left direction
     */
    static boolean moveLeft() {
        return move(-1, 0);
    }
 
    /**
     * @brief Method for initating a move in the right direction
     * @return The result of move() in the right direction
     */
    static boolean moveRight() {
        return move(1, 0);
    }
 
    /**
     * @brief Changes the merged status of all tiles on the Board object to false
     */
    static void clearMerged() {
        for (TileT[] row : gameboard)
            for (TileT TileT : row)
                if (TileT != null)
                    TileT.setMerged(false);
    }
 
    /**
     * @brief Determine whether any moves on the board are possible
     * @return A boolean value indicating whether any moves are possible
     */
    static boolean movesAvailable() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }
    
    /**
     * @brief Returns the game state
     * @return The gamestate
     */
    static State getGamestate() {
    	return gamestate;
    }

    /**
     * @brief Returns the game board side length
     * @return The game board side length
     */
	public static int getSide() {
		return side;
	}

}
