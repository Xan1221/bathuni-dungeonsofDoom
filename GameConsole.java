import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;
/**
 * This class is in charge of the main method and runs the game
 */

public class GameConsole {
	private Scanner choice;	 
	/**
	 * Asks the user to select a file
	 * if the file is found the file is loaded otherwise prints out a message
	 * indicating that something went wrong
	 * @return the fileName if it can successfully be loaded
	 */
	public String selectFile() {
		boolean successful=false;
		int count=0;

		String fileName = null;
		while(!successful&&count<3) {
			System.out.println("Please enter the  map would you like to load.\nyou can even create your own map and add it to the directory");
			System.out.println("samplemap.txt");
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader (System.in)); 
				fileName = in.readLine();
				in = new BufferedReader(new FileReader(fileName));
				successful=true;
			}

			catch(Exception e){
				System.out.println("Wrong file selected");
				fileName="-1";
				count++;
			}
		}
		return fileName;
	} 


	public static void main(String[] args)  {
		String input="-1";
		GameConsole game=new GameConsole();
		/*
		 * Ask the user to input the file they want to load for 3 times
		 */
		input=game.selectFile();

		/*
		 * If the user doesn't enter the right file name for 3 times,
		 * a prompt asking the user to load the default file is called
		 * depending on the response the program either exits or the map is loaded
		 */
		if(input.equals("-1")) {
			String response="";
			System.out.println("Do you want to load the default file?");
			game.choice = new Scanner(System.in);
			response=game.choice.next();
			if(response.equals("yes")||response.equals("Yes")) {
				input="samplemap.txt";
			}
			else {
				return;
			}
		}

		GameLogic logic = new GameLogic(input);
		logic.playGame();
		/*
		 * After the game ends delete all instances of logic and of game
		 */
		logic=null;
		game=null;
	}
}
