/**
 * This class is a superclass of all players throughout the game 
 * it stores information about the player
 */
public class Player {
	
	/*players token*/
	private char piece;
	
	/*players x-coordinate*/
	private int xcoordinate;
	
	/*players y-coordinate*/
	private int ycoordinate;

	/**
	 * Default constructor
	 * @param piece
	 */
	public Player(char piece){
		this.xcoordinate=0;
		this.ycoordinate=0;
		this.piece=piece;
	}

	/**
	 * returns the characters token
	 * @return the players token
	 */
	protected char getPiece() {
		return piece;
	}

	/**
	 * sets the player's coordinates
	 * @param xcoordinate
	 * @param ycoordinate
	 */
	protected void setCoordinates(int xcoordinate, int ycoordinate) {
		this.xcoordinate=xcoordinate;
		this.ycoordinate=ycoordinate;
	}

	/**
	 * returns the player's x-coordinate
	 * @return the x coordinate of the player
	 */
	protected int getXcoordinate() {
		return xcoordinate;
	}

	/**
	 * returns the player's y-coordinate
	 * @return the y coordinate of the player
	 */
	protected int getYcoordinate() {
		return ycoordinate;
	}

}
