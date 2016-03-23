import java.util.InputMismatchException;
/**
 * @author: Abhigya Ghimire
 * @about:   Start class has main method which creates a new Board, makes a Game 
 * 			 using the created board, and then calls play() method for the program 
 *   		 to execute. 
 */

public class Start {
	public static void main(String[] args) throws InputMismatchException {
		Board board= new Board();
		Game game = new Game(board);
		game.play();
	}
}
