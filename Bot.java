
/**
 *Runs the game with a bot player. This code generates an input from the bot player.
 *The bot chases the player by using LOOK and locking on to the player's coordinates
 *relative to the bot at the center of the look map
 */
public class Bot extends Player {

	/*The direction that the bot moves in*/
	private String direction;

	/*The counter determining whether the bot uses LOOK or moves*/
	private int count;

	/*Stores the players x-coordinate relative to the bot in LOOK*/
	private int playerXcoordinate;

	/*Stores the players y-coordinate relative to the bot in LOOK*/
	private int playerYcoordinate;

	/*Stores the bot's previous x-coordinate*/
	private int ghostx;

	/*Stores the bot's previous y-coordinate*/
	private int ghosty;

	/*Whether the bot spots the player or not*/
	private boolean Spot;

	/**
	 * default constructor. Constructs the bot player
	 * @param piece
	 */
	public Bot(char piece) {
		super(piece);
		count=1;
		direction="";
		ghostx=-1;
		ghosty=-1;
		playerXcoordinate=100;
		playerYcoordinate=100;
		Spot=false;
	}

	/**
	 * Sets up the player's X-coordinate and Y-coordinate relative to the bot at the center of LOOK 
	 * whose coordinates are (0,0)
	 * @param view(The map of the view function)
	 */
	protected void botSetupCheck(String view) {
		playerXcoordinate=determineXcoordinateOfPlayer(view);
		playerYcoordinate=determineYcoordinateOfPlayer(view);
	}

	/**
	 * Updates the player's X-coordinate and Y-coordinate relative to the bot when the bot moves.
	 * The bot's coordinates are at the center of the map at (0,0)
	 * @param x
	 * @param y
	 */
	protected void botUpdateCoordinatesRelToBot(int x, int y){
		if(Spot) {
			playerXcoordinate+=x;
			playerYcoordinate+=y;
		}
	}

	/**
	 * Takes the map and interprets it to find the x-coordinate of the player
	 * @param view(The view of the LOOK map)
	 * @return  X-displacement of the human player respective to the bot's y coordinate
	 */
	private int determineXcoordinateOfPlayer(String view) {
		int startingCount=-2;
		for(int i=0; i<view.length(); i++) {
			if(view.charAt(i)=='P') {
				break;
			}
			if(view.charAt(i)=='\n') {
				startingCount=-2;
			}
			else {
				startingCount++;
			}
		}
		return startingCount;
	}


	/**
	 * Takes the map and interprets it to find the y-coordinate of the player
	 * by counting the number of new line characters
	 * @param view(The view of the LOOK map)
	 * @return the Y-displacement of the human player respective to the bots y coordinate
	 */
	private int determineYcoordinateOfPlayer(String view) {
		int countingY=-2;
		for(int i=0; i<view.length(); i++) {
			if(view.charAt(i)=='P') {
				break;
			}
			if(view.charAt(i)=='\n') {
				countingY++;;
			}
		}
		return countingY;
	}

	/**Sets the command of the bot by calling look every 3 times and randomizing look and move
	 * when the count has been reset to 1
	 * @param bot
	 * @param map of type Map
	 * @return the string command chosen by this algorithm
	 */

	protected String setCommand(Bot bot, Map map) {

		String array[]= {setInput(bot,map),"LOOK"};

		if(count%3==0) {
			count++;
			if(count==10) {
				count=1;
			}
			return  array[1];
		}

		else {
			count++;
			if(count==10) {
				count=1;
			}
			return array[0];
		}
	}


	/**
	 * Sets the bot's input depending on the humanPlayer's coordinates from the LOOK functi
	 * and whether there is a wall blocking the bot.
	 * @param player
	 * @param bot
	 * @param map
	 * @return The direction the bot should move in.
	 */
	private String setInput(Bot bot,Map map) {
		int x=0;
		int y=0;

		/**the conditions such that the player is within 2 units left,right above or below of the bot
		 * excluding when x or y  equals 0
		 */
		boolean BotLeft=playerXcoordinate<0;
		boolean BotRight=playerXcoordinate>0;
		boolean BotUp=playerYcoordinate>0;
		boolean BotDown=playerYcoordinate<0;

		/**the conditions such that the bot's movement is equal to the bot's previous position 
		 */
		boolean ghostUp=(bot.getYcoordinate()+1)==ghosty
				&&bot.getXcoordinate()==ghostx;

		boolean ghostDown=(bot.getYcoordinate()-1)==ghosty
				&&bot.getXcoordinate()==ghostx;

		boolean ghostLeft=bot.getYcoordinate()==ghosty
				&&(bot.getXcoordinate()-1)==ghostx;

		boolean ghostRight=bot.getYcoordinate()==ghosty&&
				(bot.getXcoordinate()+1)==ghostx;



		/*if the player is in the bot's line of sight and is to the left,right,above,below the bot 
		 * and the new coordinates aren't equal to the bot's previous coordinates move towards the player
		 */
		if((BotUp&&Spot&&!ghostUp)) {
			x=0;
			y=1;
			direction="MOVE S";
		}

		else if(BotDown&&Spot&&!ghostDown) {
			x=0;
			y=-1;
			direction="MOVE N";
		}

		else if(BotRight&&Spot&&!ghostRight) {
			x=1;
			y=0;
			direction="MOVE E";
		}

		else if(BotLeft&&Spot&&!ghostLeft) {
			x=-1;
			y=0;
			direction="MOVE W";
		}

		/*Otherwise move in a random direction*/
		else {
			direction=returnRandDirection();
			switch(direction) {
			case "MOVE N": x=0; y=-1;
			case "MOVE S": x=0; y=1;
			case"MOVE E": x=1; y=0;
			default: x=-1; y=0;
			}
		}


		/*If there is no collision with a wall move in the direction above*/
		if(!map.checkCollision(bot,y,x)){
			return direction;
		}

		/*otherwise change the directions of the bot*/
		else  {
			switch(direction) {
			case "MOVE S": direction="MOVE E";   break;
			case "MOVE N": direction="MOVE W";   break;
			case "MOVE E": direction="MOVE N";   break;
			case "MOVE W": direction="MOVE S";   break;
			}
		}
		return direction;
	}

	/**
	 * Chooses a random direction for the bot to move in
	 * @return generated direction that the bot will move in
	 */
	private String returnRandDirection(){
		String movement[]= {"MOVE E","MOVE W","MOVE S","MOVE N"};
		int random =(int)(Math.random()*4);
		String direction=movement[random];
		return direction;
	}

	/**
	 * sets the bots previous coordinates when the bot is about to move to a new location
	 * @param x (x-coordinate)
	 * @param y (y-coordinate)
	 */
	protected void  botGhostSet(int x,int y) {
		ghostx=x;
		ghosty=y;
	}

	/**set spot to true when the player is spotted*/
	protected void setSpot(boolean spotting) {
		Spot=spotting;
	}


	/*If the bot could look for the player without look the code would look like the code below without a constrcutor
	/*Direction of the bot*/
	
//	
//	private int count;
//	private int ghostx;
//	private int ghosty;
//	private boolean Spotted;
//	private int playerYcoordinate;
//	private int playerXcoordinate;
//	final int botx =0;
//	final int boty =0;
//	
//	
//	
	
//	
//	/**
//	 * Sets the command of the bot by calling look every 5 times and randomizing look and move
//	 * when the count has been reset to 1
//	 * @param player
//	 * @param bot
//	 * @param map
//	 * @return the string command chosen by this algorithm
//	 */
//	protected String setCommand(HumanPlayer player,Bot bot, Map map) {
//		
//		
//		
//		String array[]= {setInput( player, bot, map),"LOOK"}; 
//		if(count == 1) {
//		count++;
//		int randomInt=(int) (Math.random()*2);
//		return array[randomInt];
//		}
//		
//		else if(count%5==0) {
//			count++;
//			if(count==10) {
//				count=1;
//			}
//			return array[1];
//		}
//		
//		else {
//			count++;
//			if(count==10) {
//				count=1;
//			}
//			return array[0];
//		}
//	}
//
//	
//	protected void botSetupCheck(String view) {
//		int startingCount=-2;
//		for(int i=0; i<view.length(); i++) {
//			if(view.charAt(i)=='P') {
//				Spotted=true;
//				break;
//			}
//			if(view.charAt(i)=='\n') {
//				count++;
//			}
//		}
//		
//		
//		
//	}
//	
//	
//	/**
//	 * Sets the bot's input depending on the humanPlayer's coordinates in the bot's line of sight 
//	 * and whether there is a wall blocking the bot.
//	 * @param player
//	 * @param bot
//	 * @param map
//	 * @return The direction the bot should move in.
//	 */
//	private String setInput(HumanPlayer player,Bot bot,Map map) {
//		int x=0; 
//		int y=0;
//	
//		/**the conditions such that the player is within 2 units left,right above or below of the bot
//		 * excluding when x or y  equals 0
//		 */
//		boolean BotLeft=bot.getXcoordinate()-player.getXcoordinate()<=2
//				&&bot.getXcoordinate()-player.getXcoordinate()>0;
//		
//		boolean BotRight=bot.getXcoordinate()-player.getXcoordinate()>=-2
//				&&bot.getXcoordinate()-player.getXcoordinate()<0;
//		
//		boolean BotUp=bot.getYcoordinate()-player.getYcoordinate()>=-2
//				&&bot.getYcoordinate()-player.getYcoordinate()<0;
//		
//		boolean BotDown=bot.getYcoordinate()-player.getYcoordinate()<=2
//				&&bot.getYcoordinate()-player.getYcoordinate()>0;
//		
//		/**the conditions such that the player is within 2 units left,right above or below of the bot
//		 * including when the bot is on the same axis as the human
//		 */
//		boolean BotLeftEq=bot.getXcoordinate()-player.getXcoordinate()<=2
//				&&bot.getXcoordinate()-player.getXcoordinate()>=0;
//		
//		boolean BotRightEq=bot.getXcoordinate()-player.getXcoordinate()>=-2
//				&&bot.getXcoordinate()-player.getXcoordinate()<=0;
//		
//		boolean BotUpEq=bot.getYcoordinate()-player.getYcoordinate()>=-2
//				&&bot.getYcoordinate()-player.getYcoordinate()<=0;
//		
//		boolean BotDownEq=bot.getYcoordinate()-player.getYcoordinate()<=2
//				&&bot.getYcoordinate()-player.getYcoordinate()>=0;
//		
//		/**the conditions such that the bot's movement is equal to the bot's previous position 
//		 */
//		boolean ghostUp=(bot.getYcoordinate()+1)==ghosty
//				&&bot.getXcoordinate()==ghostx;
//		
//		boolean ghostDown=(bot.getYcoordinate()-1)==ghosty
//				&&bot.getXcoordinate()==ghostx;
//		
//		boolean ghostLeft=bot.getYcoordinate()==ghosty
//				&&(bot.getXcoordinate()-1)==ghostx;
//		
//		boolean ghostRight=bot.getYcoordinate()==ghosty&&
//				(bot.getXcoordinate()+1)==ghostx;
//		
//		if((BotUp&&(BotLeftEq||BotRightEq))&&!ghostUp) {
//			x=0;
//			y=1;
//			direction="MOVE S";
//		}
//		
//		else if((BotDown&&(BotLeftEq||BotRightEq))&&!ghostDown) {
//			x=0;
//			y=-1;
//			direction="MOVE N";
//		}
//		
//		else if((BotRight&&(BotUpEq||BotDownEq))&&!ghostRight) {
//			x=1;
//			y=0;
//			direction="MOVE E";
//		}
//		
//		else if((BotLeft&&(BotUpEq||BotDownEq))&&!ghostLeft) {
//			x=-1;
//			y=0;
//			direction="MOVE W";
//		}
//		
//		else {
//				direction=returnRandDirection();
//				System.out.println(direction);
//			}
//		
//		
//		if(!map.checkCollision(bot,y,x)){
//			System.out.println(direction);
//			return direction;
//		}
//		
//		else  {
//			switch(direction) {
//			case "MOVE S": direction="MOVE E";   break;
//			case "MOVE N": direction="MOVE W";   break;
//			case "MOVE E": direction="MOVE N";   break;
//			case "MOVE W": direction="MOVE S";   break;
//			}
//			return direction;
//		}
//	}
//	
//	/**
//	 * Chooses a random direction for the bot to move in
//	 * @return generated direction that the bot will move in
//	 */
//	private String returnRandDirection(){
//	String movement[]= {"MOVE E","MOVE W","MOVE S","MOVE N"};
//	int random =(int)(Math.random()*4);
//	String direction=movement[random];
//	return direction;
//	}
//	
//	/**
//	 * sets the bots previous coordinates when the bot is about to move to a new location
//	 * @param x (x-coordinate)
//	 * @param y (y-coordinate)
//	 */
//	protected void  botGhostSet(int x,int y) {
//		ghostx=x;
//		ghosty=y;
//	}

} 
