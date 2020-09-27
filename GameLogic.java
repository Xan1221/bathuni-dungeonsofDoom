

/**
 * Contains the main logic part of the game, as it processes.
 *
 */

public class GameLogic{

	/*Map*/
	private Map map;

	/*Human player*/
	private HumanPlayer human;

	/*the Bot player*/
	private Bot bot;

	/*state of the game*/
	private boolean gameState = true;


	/**
	 * Constructor when a filename is provided. This loads the map and instantiates the players
	 * @param fileName
	 */
	public GameLogic(String fileName)  {
		map = new Map(fileName);
		human = new HumanPlayer('P');
		bot = new Bot('B');
	}

	/**
	 * Default constructor. This loads the map and instantiates the players
	 */
	public GameLogic() {
		map = new Map();
		human = new HumanPlayer('P');	
		bot = new Bot('B');
		System.out.println("The amount of gold on the map is "+map.getGoldRequired());
	}


	/**
	 * Checks if the game is running
	 * @return if the game is running.
	 */
	private boolean gameRunning() {
		return gameState;
	}

	/**
	 * Returns the gold required to win.
	 * @return :Protocol Gold required to win.
	 */
	private String hello() {
		return "Gold to win: "+Integer.toString(map.getGoldRequired());
	}

	/**
	 * Returns the gold currently owned by the player.
	 * @return : Protocol Gold currently owned.
	 */
	private String gold() { 
		return "Gold owned: "+ Integer.toString(human.getGold());
	}

	/**
	 * Checks if movement is legal and updates player's location on the map.
	 * @param direction : The direction of the movement.
	 * @return : Protocol if the movement was successful or not.
	 */
	private String move(char direction,Player player) {
		int x=0;
		int y=0;
		switch(direction) {
		case'N':x=0; y=-1; break;
		case'S':x=0; y=1; break;
		case'E':x=1; y=0; break;
		case'W':x=-1; y=0; break;
		}

		if(map.checkCollision(player,y,x)){
			/*If the bot doesn't move set its previous coordinates to coordinates outside of the map*/
			if(player.getPiece()=='B') {
				bot.botGhostSet(-1,-1);
			}
			return "FAIL"; 
		}

		else {
			checkMap(player);
			if(player.getPiece()=='B') {
				/*If the bot successfully moves set the bot's coordinates before movement as its "ghost" coordinates*/
				bot.botGhostSet(bot.getXcoordinate(),bot.getYcoordinate());
			}
			
			player.setCoordinates(player.getXcoordinate()+x,player.getYcoordinate()+y); 
			checkMapArrived(player);
			map.getMap()[player.getYcoordinate()][player.getXcoordinate()]=player.getPiece();
			return"SUCCESS";
		}
	}

	/**
	 * checks what is stored in the temporary tile or the bot's temporary tile depending on the player
	 *  and puts the tile back on the map
	 * @param player
	 */
	private void checkMap(Player player) {
		if(player.getPiece()==bot.getPiece()) {
			map.getMap()[player.getYcoordinate()][player.getXcoordinate()]=map.getBotTempTile();
		}

		else  {
			map.getMap()[player.getYcoordinate()][player.getXcoordinate()]=map.getTempTile();
		}
	}

	/**
	 * checks what is on the map at the players updated coordinates and stores the tile into the
	 * human player's temporary tile or the bot's temporary tile depending on which player is passed in
	 * @param player
	 */
	private void checkMapArrived(Player player) {
		if(player.getPiece()==bot.getPiece()) {
			map.setBotTempTile(map.getMap()[player.getYcoordinate()][player.getXcoordinate()]);
		}

		else {
			map.setTempTile(map.getMap()[player.getYcoordinate()][player.getXcoordinate()]);
		}
	}


	/**
	 *Converts the map from a 2D char array to a single string.
	 *@return Protocol A 5*5 String representation of the game map is then returned.
	 */
	private String look(Player player) {
		String view=map.returnLookMap(player);
		if(player.getPiece()=='B') {
			if(view.contains("P")){
				bot.setSpot(true);
				bot.botSetupCheck(view);
				return view;
			}

			else{
				bot.setSpot(false);
			}
		}

		return view;
	}

	/**
	 * Processes the player's pickup command, updating the map and the player's gold amount.
	 *
	 * @return Protocol SUCCESS plus the amount of Gold owned or Fail plus the player's amount of gold.
	 */
	private String pickup() {
		if(map.checkStandingOnGold(human)){
			map.setTempTile('.');
			human.pickUp();
			return "SUCCESS. Gold owned: "+human.getGold();
		}
		else {
			return "FAIL. Gold owned: "+human.getGold();
		}
	}

	/**
	 * Protocol Quits the game, shutting down the application.
	 */
	private void quitGame() {
		if(map.checkWin(human)) {
			winMessage();
		}

		else {
			loseMessage();
		}
	}

	/**
	 * prints out a win message and stops the game
	 */
	private void winMessage() {
		System.out.println("WIN");
		System.out.println("Dungeon DEFEATED");
		gameState=false;
	}

	/**
	 * prints out a loose message and stops the game
	 */
	private void loseMessage() {
		System.out.println("LOSE");
		gameState=false;
		return;
	}

	/**
	 * Interprets the human player's input
	 * @param input
	 */
	private void methodCall(String input) {
		String output="";
		switch(input) {
		case "HELLO":System.out.println(hello()); break;
		case "GOLD":output=gold(); break;
		case "MOVE N":output=move('N',human); break;
		case "MOVE E":output=move('E',human); break;
		case "MOVE S":output=move('S',human); break;
		case "MOVE W":output=move('W',human); break;
		case "PICKUP":output=pickup(); break;
		case "LOOK": output=look(human);break;
		case "QUIT": quitGame(); return;
		default:output="Invalid input";
		}
		System.out.println(output);
	}

	/**
	 *  Interprets the bot's methods
	 * @param input
	 */
	private void methodCallBot(String input) {
		switch(input) {
		case "MOVE N":move('N',bot); bot.botUpdateCoordinatesRelToBot(0,1);break;
		case "MOVE E":move('E',bot); bot.botUpdateCoordinatesRelToBot(-1,0);break;
		case "MOVE S":move('S',bot); bot.botUpdateCoordinatesRelToBot(0,-1);break;
		case "MOVE W":move('W',bot); bot.botUpdateCoordinatesRelToBot(1,0);break;
		/*When the bot uses look, the bot doesn't move so the previous coordinates should be set to the outside of the map*/
		case "LOOK":printLook();look(bot); bot.botGhostSet(-1, -1);return;
		}
	}

	/**
	 * Informing the player that the bot has used look
	 */
	private void printLook() {
		System.out.println("The bot has used look!");
	}

	/**
	 * A method that deletes all instances of the objects human and bot once the game ends.
	 */
	private void resetEverything() {
		human.closeScanner();
		human=null;
		bot=null;
	}


	/**  
	 * A method that runs that runs through the process of the game
	 */
	protected void playGame() {
		map.dropPlayer(human);
		map.dropPlayer(bot);
		while(gameRunning()) {
			methodCall(human.getInputFromConsole());
			System.out.println('\n');
			if(gameState==false) {
				resetEverything();
				return;
			}

			methodCallBot(bot.setCommand(bot,map));
			if(map.getBotTempTile()==human.getPiece()) {
				loseMessage();
				resetEverything();
				return; 
			}

			if(map.getTempTile()==bot.getPiece()) {
				loseMessage();
				resetEverything();
				return;
			}
		}   
	}
}
