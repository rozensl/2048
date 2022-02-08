/**
 * @file: GameController.java
 * @Author: Lin Rozenszajn - rozensl
 * @Date: April 16, 2021
 * @Description: An ADT for a game board tile 
 */

class TileT{
	
	// State variables representing the merging status and value of a tile
    private boolean merged;
    private int value;
 
    /**
     * @brief constructor
     * @param val An integer value representing numeric value of tile
     */
    TileT(int val) {
        value = val;
    }
 
    /**
     * @brief Getter for value
     * @return Value of tile
     */
    int getValue() {
        return value;
    }
    
    /**
     * @brief Set merged status of tile 
     * @param m Boolean value representing whether the tile has undergone merging
     */ 
    void setMerged(boolean m) {
        merged = m;
    }
 
    /**
     * @brief Determine if current tile can merge with another tile
     * @param other The other tile 
     * @return A boolean value, true if possible to merge and false otherwise
     */
    boolean canMergeWith(TileT other) {
        return !merged && other != null && !other.merged && value == other.getValue();
    }
 
    /**
     * @brief Merges current tile with another tile
     * @details Merging constitutes doubling the value of the current tile
     * @param other The other tile
     * @return An integer flag representing success of merging operation
     */
    int mergeWith(TileT other) {
        if (canMergeWith(other)) {
            value *= 2;
            merged = true;
            return value;
        }
        return -1;
    }
}