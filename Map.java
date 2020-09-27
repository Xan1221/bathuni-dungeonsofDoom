
import java.io.File;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;


/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	Random random = new Random();

	/* Representation of the map */
	private char[][] map;

	/* Map name */
	private String mapName;

	/* Gold required for the human player to win */
	private int goldRequired;

	/* Horizontal dimension of the map */
	private int dimensionHorizontal = 0;

	/* Vertical dimension of the map */
	private int dimensionVertical = 0;

	//characters of the tile
	private char goldTile='G';
	private char exitTile='E';
	private char wallTile='#';
	private char emptyTile='.';

	/*temporary tile*/
	private char tempTile=' ';
	
	/*The bot's temporary tile*/
	private char botTempTile=' ';


	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Very Small Labyrinth of Dooom";
		goldRequired=0;
		map = new char[][]{
			{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
			{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
			{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
			{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
			{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
			{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
			{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
			{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
			{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};

		dimensionVertical=map.length;

		dimensionHorizontal=map[0].length;
		setGoldRequired(map);
	}

	/**
	 * Constructor that accepts a map to read in from
	 * then sets up the map
	 *
	 * @param : The filename of the map file.
	 */
	public Map(String fileName){
		readMap(fileName);

		dimensionHorizontal=map[0].length;
		dimensionVertical=map.length;
		System.out.println(getMapName());
		setGoldRequired(map);
	}

	/**
	 * @return : Gold required to exit the current map.
	 */
	protected int getGoldRequired() {
		return goldRequired;
	}

	/**
	 * @return :The map as stored in memory.
	 */
	protected char[][] getMap() {
		return map;
	}


	/**
	 * @return :The name of the current map.
	 */
	protected String getMapName() {
		return mapName;
	}

	/**
	 * This function reads the string map by the use of an ArrayList then converts it to the map.
	 * @param fileName
	 * @exception : A file not found exception if the file isn't found or there is no file provided
	 */
	protected void readMap(String fileName)  {

		File file = new File(fileName);
		Scanner filescanner;

		try {
			ArrayList<String> myList=new ArrayList<String>();
			filescanner=new Scanner(file);

			while(filescanner.hasNextLine()){
				String line=filescanner.nextLine();
				myList.add(line);
			}

			mapName=myList.get(0);

			char map0[][]=new char[myList.size()-2][];

			for(int i = 2; i<myList.size() ; i++){
				map0[i-2] = myList.get(i).toCharArray();
			}

			map=map0;
			filescanner.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drops a player onto the map. The player isn't determined hence it is of type Player
	 * @param player
	 */
	protected void dropPlayer(Player player) {
		int randomy=random.nextInt(dimensionVertical);
		int randomx=random.nextInt(dimensionHorizontal);

		while(!checkRightTile(randomy,randomx)) {
			randomy=random.nextInt(dimensionVertical);
			randomx=random.nextInt(dimensionHorizontal);
		}

		if(map[randomy][randomx]==exitTile) {
			if(player.getPiece()=='B') {
				botTempTile=exitTile;
			}

			else {
				tempTile=exitTile;
			}
		}

		else {
			if(player.getPiece()=='B'){
				botTempTile=emptyTile;
			}
			else {
				tempTile=emptyTile;
			}
		}

		map[randomy][randomx]=player.getPiece();
		player.setCoordinates(randomx, randomy);
	}

	/**
	 * Checks if the x-coordinate and y-coordinate chosen are valid
	 * @param player's y coordinate
	 * @param player's x-coordinate
	 * @return true if the coordinates are valid and false otherwise
	 */
	private boolean checkRightTile(int y, int x){
		if(map[y][x]==emptyTile||map[y][x]==exitTile) {
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * calculates the amount of gold on the map
	 * @param map
	 */
	private void setGoldRequired(char map[][]) {
		for(int i=0; i<dimensionVertical; i++) {
			for(int j=0; j<dimensionHorizontal; j++){
				if(map[i][j]==goldTile) {
					goldRequired++;
				}
			}
		}
	}


	/**
	 *Checks if the player collides with a wall
	 * @param player
	 * @param y The players y-coordinate
	 * @param x The players x-coordinate
	 * @return true if the player collides with a wall and false otherwise
	 */
	protected boolean checkCollision(Player player,int y,int x) {
		if(map[player.getYcoordinate()+y][player.getXcoordinate()+x]==wallTile) {
			return true;
		}

		return false;
	} 

	/**
	 * This function checks if the human player wins
	 * @param player 
	 * @return true if a human player collides with the Exit tile and has enough gold and false otherwise
	 */
	protected boolean checkWin(HumanPlayer player) { 
		if(getMap()[player.getYcoordinate()][player.getXcoordinate()]==player.getPiece()&&tempTile==exitTile) {
			if(player.getGold()==getGoldRequired()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the human player is standing on gold
	 * @param player
	 * @return if the human player is standing on gold
	 */
	protected boolean checkStandingOnGold(HumanPlayer player){
		if(getMap()[player.getYcoordinate()][player.getXcoordinate()]==player.getPiece()&&tempTile==goldTile){
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * returns a 5*5 window around the player to GameLogic to be printed for the human player.
	 * @param player
	 * @return the 5*5 string map around the player
	 */
	protected String returnLookMap(Player player) {
		String mapup="";
		for(int i = player.getYcoordinate()-2; i<=player.getYcoordinate()+2; i++) {
			for(int j=player.getXcoordinate()-2; j<=player.getXcoordinate()+2; j++) {
				if(i>dimensionVertical-1||i<0||j<0||j>dimensionHorizontal-1) {
					mapup+=('#');
				}

				else {
					mapup+=map[i][j];
				}

			}
			mapup+=('\n');
		}

		return mapup;
	}

	/**
	 * sets the the human player's temporary tile
	 * @param tile
	 */
	protected void setTempTile(char tile){
		this.tempTile=tile;
	}

	/**
	 * returns the human player's temporary tile
	 * @return 
	 */
	protected char getTempTile() {
		return tempTile;
	}

	/**
	 * sets the the bot's temporary tile
	 * @param tile
	 */
	protected void setBotTempTile(char botTempTile ) {
		this.botTempTile=botTempTile;
	}

	/**
	 * returns the the bot's temporary tile
	 * @return the bot's temporary tile
	 */
	protected char getBotTempTile() {
		return botTempTile;
	}

}
