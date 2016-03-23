import java.util.ArrayList;
import java.util.Random;

/**
 * @author: Abhigya Ghimire
 * @email:  aghimire@ucsd.edu
 * @PID:    A11628841
 * @user:   cs8bbe
 * @about: Board class creates a Board and checks if a move can be made or not.
 *         This class also merges the tiles and does most of the mathematics
 *         involved in the game.
 */

public class Board {
	private static final int SIZE = 4;
	private Tile[][] tiles2D = new Tile[SIZE][SIZE];
	private Random randGen;

	// Creates a playing board with all the tiles with the value of 0.
	public Board() {
		for (int i = 0; i < tiles2D[0].length; i++) {
			for (int j = 0; j < tiles2D.length; j++) {
				tiles2D[i][j] = new Tile();
			}
		}
	}

	// Gets the width of the board.
	public int boardWidth() {
		return Board.SIZE;
	}

	// Gets the height of the board.
	public int boardHeight() {
		return Board.SIZE;
	}

	// Tests whether the tile can be moved or not.
	protected boolean canMove(ArrayList<Tile> tileArray) {
		for (Tile tile : tileArray) {
			if (tile.getValue() != 0) {//need to check if value != 0
				return false;
			}
		}
		return true; //true if value is 0
	}

	/*
	 * move() method moves the board in the direction given by the user. It
	 * breaks 2d Tile array into an Array list of type Tile and does the
	 * movement of the tiles if possible.
	 */
	public void move(Direction dir) {
		for (int i = 0; i < SIZE; i++) {
			ArrayList<Tile> tileArray = new ArrayList<Tile>();
			for (int j = 0; j < SIZE; j++) {
				switch (dir) {
				case UP:
					tileArray.add(tiles2D[j][i]);
					break;
				case DOWN:
					tileArray.add(tiles2D[SIZE - j - 1][i]);
					break;
				case LEFT:
					tileArray.add(tiles2D[i][j]);
					break;
				case RIGHT:
					tileArray.add(tiles2D[i][SIZE - j - 1]);
					break;
				case QUIT:
					break;
				case NEWGAME:
					Game game = new Game();
					game.play();
				default:
					break;
				}
			}
			if (!(canMove(tileArray))) { // Slide if the tile value != 0
				slideAndMerge(tileArray);//if value is not zero then you slide and merge
			}
		}
	}

	// Slides the value to next tile and clears the tile whose value was moved.
	// Shifting left
	private void slideTo(ArrayList<Tile> tileArray, int index) {
		for (int i = index; i < (tileArray.size() - 1); i++) {
			int nextValue = (tileArray.get(i + 1)).getValue();
			(tileArray.get(i)).setValue(nextValue);
		}
		tileArray.get(tileArray.size() - 1).clear();
	}

	// Checks whether tile has a 0 value or not.
	private boolean zeroTile(ArrayList<Tile> tileArray, int i) {
		ArrayList<Tile> remainingTile = new ArrayList<Tile>();
		for (int j = i; j < tileArray.size(); j++) {
			remainingTile.add(tileArray.get(j));
		}
		return (canMove(remainingTile)); //true if value is 0
	}

	// Slides the tiles to the edge of the board. It prevents OutOfBounds errors. 
	private void slideToEdge(ArrayList<Tile> tileArray) {
		for (int i = 0; i < tileArray.size(); i++) {
			if (zeroTile(tileArray, i)) {//if 0 tile, do nothing
				return;
			}
			while (tileArray.get(i).getValue() == 0) { 
				slideTo(tileArray, i); //slide current tile to next value
			}
		}
	}

	// If two tile values are equal, then the tiles will merge to the left 
	// and the values will be added. It will also the "right" tile. 
	private void mergeTile(ArrayList<Tile> tileArray) {
		for (int i = 0; i < tileArray.size() - 1; i++) {
			if ((tileArray.get(i)).equals(tileArray.get(i + 1))) {
				(tileArray.get(i)).addTiles(tileArray.get(i + 1));
				(tileArray.get(i + 1)).clear();
				slideTo(tileArray, i + 1);
			}
		}
	}

	// Slides to the edge and then merges it. 
	private void slideAndMerge(ArrayList<Tile> tileArray) {
		slideToEdge(tileArray);
		mergeTile(tileArray);
	}

	// Checks for empty tiles on the board.
	private boolean hasEmptyTile() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (tiles2D[i][j].getValue() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	// Checks if next tile has equal values.
	private boolean nextSameTileValue() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (i < SIZE - 1) { 
					//Checks if it has the same tile value in the tile in the next row
					if (tiles2D[i][j].equals(tiles2D[i + 1][j])) {
						return true;
					}
				}
				if (j < SIZE - 1) { 
					//Checks if it has the same tile value in the tile in the next column
					if (tiles2D[i][j].equals(tiles2D[i][j + 1])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/* Checks if there are the next move is possible or not. 
	 *  If there is an empty tile (tile with value 0) or if there is a tile with
	 * same value, then it will return false. 
	*/  
	public boolean noPossibleMoves() {
		if (hasEmptyTile()) {
			return false;
		}
		return !(nextSameTileValue());
	}

	// Finds an empty tile and changes its value to 2 randomly. 
	public boolean generateRandomTwo() {
		if (!(hasEmptyTile())) {
			return false;
		}
		randGen = new Random();
		while (true) {
			int x = randGen.nextInt(SIZE);
			int y = randGen.nextInt(SIZE);
			if (tiles2D[x][y].getValue() == 0) {
				tiles2D[x][y].setValue(2);
				return true;
			}
		}
	}

	// Gets a tile from the 2D Tile Array with given row and column number. 
	public Tile getTile(int i, int j) {
		return tiles2D[i][j];
	}

	//Takes in an integer 2D array and turns it into a board.
	//Used for JUnit Testing.
	public Board setBoard(int[][] values) {
		Board testBoard = new Board();
		for (int i = 0; i < testBoard.boardWidth(); i++) {
			for (int j = 0; j < testBoard.boardHeight(); j++) {
				(testBoard.getTile(i, j)).setValue(values[i][j]);
			}
		}
		return testBoard;
	}

	/* JUST FOR FUNZIES
	 *
	 *
	//Takes in an arrayList of tiles and converts it into a board. 
	public Board tileArrayToBoard(ArrayList<Tile> tileArray) {
		Board newBoard = new Board();
		int z = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				(newBoard.getTile(i, j))
						.setValue((tileArray.get(z)).getValue());
				z++;
			}
		}
		return newBoard;
	}

	// Takes in a board and extracts the tiles from it. 
	public Tile[][] boardToTiles(Board board) {
		Tile[][] tiles = new Tile[SIZE][SIZE];
		for (int i = 0; i < board.boardWidth(); i++) {
			for (int j = 0; j < board.boardHeight(); j++) {
				int value = (board.getTile(i, j)).getValue();
				tiles[i][j].setValue(value);
			}
		}
		return tiles;
	}
*/
	
}