# bathuni-dungeonsofDoom
Installation: The dungeon Of Doom can be access by running the GameConsole class in eclipse.
If you are using a linux terminal, type in javac GameConsole.java to compile the game type
java GameConsole to load the game.

Game setup: Please note that when you load the game you will be asked to chooses a map file.
	    A sample map file has been included but feel free to include your own map file in the project file
	    There will be a prompt asking you to choose a map. 
	    If there are problems with the file of your choice you can enter a choice up to 3 times, 
	    then you will be asked if you want To select the default map. 
            If you give an answer other than yes the program will terminate.


Game Description: Welcome to Dungeons Of Doom! The aim of this game is for you to collect                        
                  all the gold on the map and to exit safely. However, there are two catches:
                  
 1)You can't see the map.
 2)A crazy robot is chasing you! The robot won't tell you where it moves but it will 
   tell you when it looks. If it sees you it will store your coordinates when you were seen and Will attempt to chase you.
 
Not to fear though! The game has equip you with helpful commands:
                  
HELLO:This command returns the amount of gold required to win. To activate this command type: HELLO
                
GOLD: This command displays how much gold you currently own. To activate this command type: GOLD
                
MOVE <direction>:This command enables you to move north, south-east or      
		 west. In order to call this method successfully:
	         MOVE with a space followed by N,S,E or W has to be         
		 called. If a wall is blocking your way you will receive a FAIL 
	         indicating that your command was unsuccessful or SUCCESS
                 otherwise.                                                                          
	

PICKUP:This method enables you to pickup gold. If there is no gold on the         
       space you are currently standing on a Fail will be displayed.
       This will indicate that pickup was unsuccessful and it will be displayed by your amount of gold before   
       PICKUP was called. If you do happen to stumble upon gold and use PICKUP,   
       SUCCESS will be displayed followed by your updated amount of gold. To activate the command type: PICKUP
                 
LOOK: This enables you to view a 5x5 grid of your surrounding area. This method is also  
      accessible by the bot. To activate this command type: LOOK
                 

QUIT:Feeling tired of playing? You can simply quit the game with this command. This command will  
     return loose if you weren't able to collect all the gold on the map regardless of if you are 
     standing on an exit tile. In the unexpected case that you were in fact standing on an exit    
     tile and had collected all of the gold you would in fact win. To activate this command type in QUIT

 Any other method apart from this will return you a message saying that your input was 
 invalid. Whether your turn was successful or not this will still count as a turn.
 If you enter the wrong input, don't worry you have 3 tries to enter the right command
 Or invalid will be returned.

Ending conditions:
The game will only end if you land on an exit tile and call the QUIT method, 
having enough gold to satisfy the win condition
Or if the bot catches you. 



                                           Enjoy Dungeons Of Doom!
-------------------------------------------------------------------------------------------------------------
              

     					
					
