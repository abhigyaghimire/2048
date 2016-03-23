import java.util.Scanner;

/**
 * @author: Abhigya Ghimire
 * @about:   Game class' main function is to take the input from the user using 
 *		     takeInput() , print the Board using printBoard(), print the Menu using
 *   		 printMenu() and makes sure that all the inputs are valid using checkInput()
 *   		 and checkGameOverInput(). 
 *   		 Game.java also has gameOver() method which checks if there are no possible
 *   		 moves left using the noPossibleMoves() method from Board class and it
 *   		 also lets the user know when they have a tile with 2048. * 
 */

public class Game {
	private Board board;
	private Scanner scanner;

	//Creates a Game with a Board
	public Game() {
		this.board = new Board();
	}
	
	//Creates a Game which contains Board given by the user. 
	public Game(Board board) {
		this.board = board;
	}

	// Method that runs the game until one reaches 2048 or have no more moves left.
	public void play() {
		board.generateRandomTwo();
		board.generateRandomTwo();
		boolean stopGame = board.noPossibleMoves();
		while (stopGame != true) {
			printMenu();
			printBoard();
			char input = takeInput();
			boolean okInput = checkInput(input);
			if (okInput) {
				if (input != 'q') {
					Board boardCopy = copyBoard();
					Direction dir = charToDir(input);
					board.move(dir);
					if (differentBoards(boardCopy)) {
						board.generateRandomTwo();
					}
				} else {
					return;
				}
			}
			stopGame = board.noPossibleMoves();
			gameOver();
		}
	}

	//Takes the input from the user on what move to make. 
	public char takeInput() {
		scanner = new Scanner(System.in);
		System.out.print("Make a move:  ");
		String input = scanner.nextLine();
		char c = 0;
		if (input.length() != 1) {
		} else {
			c = input.charAt(0);
		}
		return c;
	}

	//Checks the input of the user.
	public boolean checkInput(char input) {
		switch (input) {
		case 'w':
		case 'a':
		case 's':
		case 'd':
		case 'n':
		case 'q':
			return true;
		default:
			return false;
		}
	}

	//Changes the character input to Direction type to make it work with move() method.
	public Direction charToDir(char c) {
		Character.toLowerCase(c);
		Direction dir = null;
		switch (c) {
		case 'w':
			dir = Direction.UP;
			break;
		case 's':
			dir = Direction.DOWN;
			break;
		case 'a':
			dir = Direction.LEFT;
			break;
		case 'd':
			dir = Direction.RIGHT;
			break;
		case 'q':
			dir = Direction.QUIT;
		case 'n':
			dir = Direction.NEWGAME;
		default:
			break;
		}
		return dir;
	}
	
	//Prints board with the Tile values in a 4x4 matrix format. 
	public void printBoard() {
		for (int i = 0; i < board.boardHeight(); i++) {
			for (int j = 0; j < board.boardWidth(); j++) {
				int value = (board.getTile(i, j)).getValue();
				if (value == 0) {
					System.out.print("___ ");
				} else {
					System.out.print(value + "   ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	//Prints the menu with Controls
	public void printMenu() {
		System.out.println("\n" + "Controls: \n" + "w - Move Up \n" + "s - Move Down \n" + "a - Move Left \n"
				+ "d - Move Right \n" + "q - Quit \n" + "n - Reset Game \n");
	}

	//Checks if its 2048 or there are no possible moves.
	//Prints accordingly.
	public boolean printGameOver() {
		if (twenty48()) {
			printBoard();
			System.out.println("YOU WIN! \n" + "n - New Game \n" + "q - Quit \n");
			return true;
		} else if (board.noPossibleMoves()) {
			printBoard();
			System.out.println("GAME OVER! \n" + "n - New Game \n" + "q - Quit \n");
			return true;
		}
		return false;
	}

	//Makes a copy of the board.
	public Board copyBoard() {
		Board board2 = new Board();
		for (int i = 0; i < board.boardHeight(); i++) {
			for (int j = 0; j < board.boardWidth(); j++) {
				int board1value = (board.getTile(i, j)).getValue();
				(board2.getTile(i, j)).setValue(board1value);
			}
		}
		return board2;
	}

	//Checks if two boards are same or not. 
	public boolean differentBoards(Board boardCopy) {
		for (int i = 0; i < boardCopy.boardHeight(); i++) {
			for (int j = 0; j < boardCopy.boardWidth(); j++) {
				int board1value = board.getTile(i, j).getValue();
				int board2value = boardCopy.getTile(i, j).getValue();
				if (board1value != board2value) {
					return true;
				}
			}
		}
		return false;
	}

	//Checks if there is a tile with 2048.
	public boolean twenty48() {
		for (int i = 0; i < board.boardHeight(); i++) {
			for (int j = 0; j < board.boardWidth(); j++) {
				int board1value = (board.getTile(i, j)).getValue();
				if (board1value == 2048) {
					return true;
				}
			}
		}
		return false;
	}

	//Checking input once the Game is Over.
	public boolean checkGameOverInput(char input) {
		switch (input) {
		case 'n':
		case 'q':
			return true;
		default:
			return false;
		}
	}

	// Executes printGameOver() and checks for the valid input once the game ends.
	// Game will end either if a tile is 2048 or if there are no possible moves. 
	public void gameOver() {
		if (printGameOver()) {
			char input2 = takeInput();
			boolean okInput = checkGameOverInput(input2);
			if (okInput) {
				if (input2 != 'q') {
					Direction dir2 = charToDir(input2);
					board.move(dir2);
				} else {
					return;
				}
			}
		}
	}
}
