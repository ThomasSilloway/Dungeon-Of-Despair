/**
 * The abstract class for Rooms
 * Contains all default responses
 * Contains lots of getters for information on monsters and character and text
 * @author Thomas Silloway
 * @version 1.10
 */
public abstract class Rooms {
	private String output;
	private String firstWord;
	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private Monster ogre;
	private Monster goblin;
	private Monster master;
	/**
	 * constructor initializes all variables
	 * @param plyr the character that is playing
	 */
	public Rooms(Player plyr, Monster ogre, Monster goblin, Monster master){
		output = "no output";
		firstWord = "";
		secondWord = "";
		word = 1;
		this.plyr = plyr;
		this.ogre = ogre;
		this.goblin = goblin;
		this.master = master;
	}
	
	/**
	 * Set text method, sets the variable 'word' to a number based on what word the user types
	 * @param i the input
	 */
	 public void setText(String[] i){
		 firstWord = i[0];
		 secondWord = i[1];
		 if(firstWord.equals("n"))
			 word = 1;
		 if(firstWord.equals("s"))
			 word = 2;
		 if(firstWord.equals("e"))
			 word = 3;
		 if(firstWord.equals("w"))
			 word = 4;
		 if(firstWord.equals("look")){
			 word = 5;
			 //System.out.println("Set word in Rooms.java");
		 }
		 if(firstWord.equals("listen"))
			 word = 6;
		 if(firstWord.equals("grab"))
			 word = 7;
		 if(firstWord.equals("drop"))
			 word = 8;
		 if(firstWord.equals("hide"))
			 word = 9;
		 if(firstWord.equals("attack"))
			 word = 10;
		 if(firstWord.equals("yell"))
			 word = 11;
		 if(firstWord.equals("agree"))
			 word = 12;
		 if(firstWord.equals("disagree"))
			 word = 13;
		 if(firstWord.equals("inventory")){
			 word = 14;
		 }
		 //System.out.println(""+word);
		 
	 }
	 /**
	  * get second word that the user types in
	  * @return the second word
	  */
	 public String getSecondWord(){
		 return secondWord;
	 }
	 /**
	  * Set output to whatever the room that calls it says to
	  */
	 public void setOutput(String s){
		 output = s;
	 }
	 /**
	  * append the string to output
	  * @param s the string to be appended to the output
	  */
	 public void appendOutput(String s){
		 output += s;
	 }
	 /**
	  * Get player, used to make sure the variable player in the inheriting classes 
	  * are the same as the one in this class
	  * @return the player
	  */
	 public Player getPlayer(){
		 return plyr;
	 }
	 /**
	  * Get the ogre used in first 5 rooms possibly
	  * used to make sure the variable ogre in the inheriting classes 
	  * are the same as the one in this class
	  * @return the ogre
	  */
	 public Monster getOgre(){
		 return ogre;
	 }
	 /**
	  * Get the goblin that is used in the 6th room
	  * used to make sure the variable goblin in the inheriting classes 
	  * are the same as the one in this class
	  * @return the goblin
	  */
	 public Monster getGoblin(){
		 return goblin;
	 }
	 /**
	  * Get the master - final boss
	  * used to make sure the variable master in the inheriting classes 
	  * are the same as the one in this class
	  * @return the master
	  */
	 public Monster getMaster(){
		 return master;
	 }
	 /**
	  * Used for testing
	  * @return the number of the word
	  */
	 public int getWord(){
		 return word;
	 }
	/**
	 * Get output method
	 * Used by the Backend class to get the output that should be displayed
	 * @return the output to be displayed
	 */
	 public String getOutput(){
		 return output;
	 }
	 
	 /**
	  * The FSM for each room will be different
	  */
	 public abstract int runFSM();
	 
	 
	 
	 
	 /*********  Protected Methods for only the extending classes to get ************/
	 /**
	  * Get default response for each command
	  */
	 protected void getDefault(){
		 //System.out.println(output);
		 switch (word) {
		 case 1: 
		 		output = "\n\nYou cannot go that way.";  // command = n
		 		break;
		 case 2: 
		 		output = "\n\nYou cannot go that way.";  // command = s
		 		break;
		 case 3: 
		 		output = "\n\nYou cannot go that way.";  // command = e
		 		break;
		 case 4: 
		 		output = "\n\nYou cannot go that way.";  // command = w
		 		break;
		 case 5: 
		 		output = "\n\nERROR: No default message for that word"; //command = look
		 		break;
		 case 6: 
		 		output = "\n\nERROR: No default message for that word"; // command = listen
		 		break;
		 case 7: 
		 		output = "\n\nYou cannot grab that!";  //command = grab
		 		break;
		 case 8: 
		 		output = "\n\nYou cannot drop that in here!";  //command = drop
		 		break;
		 case 9: 
		 		output = "\n\nYou begin to hide in the corner of the room."; //command = hide
		 		break;
		 case 10: 
		 		output = "\n\nYou can't attack that from here."; //command = attack
		 		break;
		 case 11: 
		 		output = "\n\nYou scream at the top of your lungs, but no one can help you now."; //command = yell
		 		break;
		 case 12:
		 		output = "\n\nYou nod your head in agreement."; //command = agree
		 		break;
		 case 13: 
		 		output = "\n\nYou shake your head violently in disagreement."; //command = disagree
		 		break;
		 case 14:
			 	output = "\n\n"+plyr.getInventory(); //command = inventory
			 	break;
		 }
		 
		 //output = "ERROR: No default message for that word";
	 }
	/**
	 * Get the list of items that the player currently has
	 */
	protected String getInventory(){
		return plyr.getInventory();
	}
	/**
	 * The response that happens when you win the game
	 */
	protected void getWin(){
		appendOutput("\nCongrats on beating the Dungeon of Despair.\n\nCredits:"+
		"\nGame Designer:  Thomas Silloway\n\nType any valid command to play again!");
	}
	/**
	 * The response that happens everytime player dies
	 */
	protected void getDeath(){
		//System.out.println("Get death was called");
		 output += "\n\nYou have died.\n\nType in any valid command to play again!";
	 }
	
}
