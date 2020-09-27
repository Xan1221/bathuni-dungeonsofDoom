import java.util.Scanner;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 */
public class HumanPlayer extends Player{
	private Scanner scanner;
	private int amountOfGold;

	/**
	 * constructor. constructs the human player
	 */
	public HumanPlayer(char piece){
		super(piece);
		amountOfGold=0;		
	}


	/**
	 * Reads player's input from the console.
	 * @return A string containing the input the player entered.
	 */
	protected String getInputFromConsole() {
		int count = 1;
		printMessage();
		scanner = new Scanner(System.in);
		String input=scanner.nextLine();
		input = getNextAction(input);

		/*If a player has entered an invalid input they have 2 more tries to enter the right input
		 * otherwise their command will be set to invalid.
		 */
		while(input=="Invalid"&&count<3) {
			printMessage();
			input=scanner.nextLine();
			input = getNextAction(input);
			count++;
		}

		return input;
	}

	/**
	 * prints a prompt for input
	 */
	private void printMessage() {
		System.out.println("Please enter your input");
	}


	/**
	 * Checks the validity of the players input
	 * @return
	 */
	private boolean checkValidity(String input) {
		switch(input) {
		case "HELLO": return true;
		case "GOLD": return true;
		case "MOVE N": return true;
		case "MOVE E": return true;
		case "MOVE S": return true;
		case "MOVE W": return true;
		case "PICKUP": return true;
		case "LOOK": return true;
		case "QUIT": return true;
		default: return false;
		}
	}

	/**
	 * Increases the amount of gold held by the player
	 */
	protected void pickUp() {
		amountOfGold++;
	}

	/**
	 * returns the amount of gold held by the player
	 * @return
	 */
	protected int getGold() {
		return amountOfGold;
	}

	/** 
	 * closes the scanner after it has been used
	 */
	protected void closeScanner() {
		scanner.close();
	}


	/**
	 * Processes the command. It should return a reply in form of a String, as the protocol dictates.
	 * Otherwise it should return the string "Invalid".
	 *
	 * @return : Processed output or Invalid if the @param command is wrong.
	 */
	private String getNextAction(String input) {

		if(checkValidity(input)) {
			return input;
		}

		else {
			return "Invalid";
		}
	}
}