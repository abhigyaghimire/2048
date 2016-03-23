/**
 * @author: Abhigya Ghimire
 * @email:  aghimire@ucsd.edu
 * @PID:    A11628841
 * @user:   cs8bbe
 * @about:   Tile creates the tiles of the game where the values (2 ^n) are stored.
 *		 	 Tile class makes it easy for users to create a board in Board class using a 
 *  		 2d Tile array. 
 */

public class Tile {
	private int value;

	// Creates a Tile with value: 0.
	public Tile() {
		this(0);
	}

	// Creates a Tile with the value that user inputs. 
	public Tile(int value) {
		this.value = value;
	}

	// Checks if two tiles have the same values.
	public boolean equals(Tile tile) {
		return tile.getValue() == this.getValue();
	}

	// Add values of two tiles. Used to add tiles with same values.
	public void addTiles(Tile tile) {
		this.setValue(value + tile.getValue());
	}

	// Zeros the Tile. 
	public void clear() {
		this.setValue(0);
	}

	//Gets the value of the tile.
	public int getValue() {
		return value;
	}
	
	//Sets the value of the tile. 
	public void setValue(int value) {
		this.value = value;
	}
}
