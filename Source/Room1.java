/**
 * This is the first room that you enter
 * It's a jail, grab coin, drop coin, hide, n to escape
 * Player has the solve the puzzle by looking around the room for the coin
 * Listen to the Ogre to hear the riddle and figure out how to get him the coin
 * But still live to see the outside
 * 
 * @author Thomas Silloway
 * @version 1.10
 */
public class Room1 extends Rooms {
	
	//private String output;
	private String secondWord;
	private int word; //the number used for FSM
	//private Player plyr;
	private Monster ogre;
	private int state; //state of the room that you're in for the FSM
	private int listen; // the number of times the player has listened (two listening states)
	private int playerStatus;
	
	/**
	 * constructor initializes all variables
	 * @param plyr the character used in this game
	 */
	public Room1(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		listen = 0;
		//this.plyr = getPlayer();
		this.ogre = getOgre();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		
	}
	

	/**
	 * State 0 is initially without doing anything
	 * State 1 is when you have the coin, you die if you yell and ogre sees you
	 * State 2 is if you drop the coin, die if you don't hide after first round
	 * State 3 is if you are hiding, die if anything but look listen and move n
	 */
	public int runFSM() {
		if(ogre.getState() == 3){ // ogre dead
			playerStatus = 1;
			state = 4;
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 2 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 1, but not called properly");
			System.exit(1);
		}
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); 
			//System.out.println("Room1 output after state0 method: "+getOutput()); 
			return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		case 2: playerStatus = state2(); return playerStatus; 
		case 3: playerStatus = state3(); return playerStatus;
		case 4: playerStatus = state4(); return playerStatus;
		}
		return 1;

	}
	
	/**
	 * State 0: which actions to get from?
	 */
	public int state0(){
		if ((word >= 1 && word <= 4) || (word >= 8 && word <= 9) || (word >= 12 && word <= 14)){
			this.getDefault();
		}
		else{
			//System.out.println("Calling zeroActions() in Room1");
			this.zeroActions();
		}
		return 1;
	}
	/**
	 * state 1: which actions to get from?
	 */
	public int state1(){
		if ((word >= 1 && word <= 4) || (word == 7) || (word == 9) || (word >= 12 && word <= 14)){
			this.getDefault();
			return 1;
		}
		else if (word == 8  || word == 11){
			return this.oneActions();
		}
		else{
			this.zeroActions();
			return 1;
		}
	}
	/**
	 * State 2: which actions to get from?
	 */
	public int state2(){
		//System.out.println("Number of word: "+getWord());
		if ((word == 9 || (word == 10 && getSecondWord().equals("ogre")))){
			
			return this.twoActions();
		}
		else{
			setOutput("\n\nThe ogre opens the door and becomes aware of your presence.");  
			this.getDeath2();
			return 3;
		}
	}
	/**
	 * state 3: which actions to get from?
	 */
	public int state3(){
		if(word == 14){
			this.getDefault();
		}
		else{
			return this.threeActions();
		}
		return 1;
	}
	
	public int state4(){  //death state
		if((word >= 2 && word <= 4) || ((word >= 7 && word <=14))){
			this.getDefault();
		}
		else{
			return fourActions();
		}
		return 1;
	}
	
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * For grab coin change state to 1
	 */
	private void zeroActions(){
		switch(word){
		case 5:  
			//System.out.println("The word was look in zeroActions() of Room1");
			if(!getPlayer().getItem(1)){  //character doesn't have coin
				setOutput("\n\nAs you look around the room a glint of metal catches your eye in the"+
					" corner.  A coin is kicked up as you use your foot to pull some"+
					" dirt out of the corner.  The walls around you are completely"+
					" made of mud and it looks like there is no"+
					" way out other than through the locked jail door.");
				//System.out.println("output has been set");
				
			}
			else{
				setOutput("\n\nAs you look around the room the walls appear to be closing in on you"+
				" as paranoia sets in.  The walls are completely made of mud and it looks like"+
				" there is no way out other than through the locked jail door.");
				
			}
			break;
		case 6:
			if(listen == 0){
				setOutput("\n\nYou begin to listen to the sounds around you and a soft cheering"+
				" can be made out in the distance.  You can't tell what kind of creature is"+
				" making the noise.  The ogre jingles his keys and begins to chant softly."+
				"  You can't make out the words, perhaps you should listen harder.");
				listen++;
				
			}
			else{
				setOutput("\n\nYou barely make out a chant from the ogre guard:  "
					+"\n\nMe am big, me am strong\nMe like to sing this pretty song\n"+
					"Me am scared of Prince in dream\nMe like things that gleam!");
				listen = 0;
				
			}
			break;
		case 7:
			if(getSecondWord().equals("coin") && !getPlayer().getItem(1)){  //don't have the coin and pick it up
				setOutput("\n\nYou pick up and dust off the coin from the corner of the room.");
				getPlayer().flagItem(true, 1);
				state = 1;
			}
			else{
				this.getDefault();
			}
			break;
		case 10:
			if(getSecondWord().equals("ogre")){
				setOutput("\n\nYou cannot see the ogre from here.");
			}
			else{
				this.getDefault();
			}
			break;
		case 11:
			setOutput("\n\nThe ogre guard hears a high pitched scream come from your mouth"+
			" and comes to see what you are yelling about.  A look of pure horror consumes your"+
			" face.  The ogre guard laughs and walks back to his"+
			" post next to the jail cell door.");
			break;
		}
	}
	/**
	 * State one's actions
	 * For drop coin change state to 2
	 */
	private int oneActions(){
		switch(word){
		case 8:
			//System.out.println(secondWord);   //Used for testing
			//System.out.println(""+getPlayer().getItem(1));  //used for testing
			if(secondWord.equals("coin") && getPlayer().getItem(1)){  //You have the coin and you drop it
				setOutput("\n\nYou drop the shiny coin on the floor and it makes a clanging sound"+
				" as it strikes a rock embedded in the ground.  The shadow in front of the cell"+
				" door begins to move and the ogre slowly moves around to look into the cell."+
				"  The ogre's eyes widen as his stare fixes on the coin that lays at your feet."+
				"  He begins to unlock the jail cell door.");
				getPlayer().flagItem(false,1);
				state = 2;
			}
			else{
				setOutput("\n\nYou can't drop that!");
			}
			return 1;
		case 11:
			setOutput("\n\nThe ogre guard hears a high pitched scream come from your mouth"+
			" and comes to see what you are yelling about."+
			"  The ogre sees the shiny coin in your hands and opens the door.  In the blink of an"+
			" eye, the ogre rushes over to you and forcefully grabs the coin from you, but in the"+
			" process your arms are torn off by the brute force of the beast.\n\nYou quickly bleed"+
			" to death.");
			this.getDeath();
			return 3;
		}
		return 1; //default for errors
			
	}
	/**
	 * State two's actions
	 * For hide change state to 3
	 */
	private int twoActions(){
		switch(word){
		case 9:
			setOutput("\n\nYou hide in the corner of the room.  The ogre is beguiled by the coin and"+
			" the door is unguarded.  You see an opportunity for escape.");
			state = 3;
			return 1;
		case 10:
			this.getDeath3();
			return 3;
		default: 
			setOutput("\n\nERROR: You failed to die from not hiding in state 2 room 1");
			return 1;
		}
	}
	/**
	 * State three's actions, you can escape now
	 */
	private int threeActions(){
		if (word >= 2 && word <= 4){
			word = 2;
		}
		
		
		switch(word){
		case 1:
			setOutput("\n\nYou slip out the unlocked jail cell to the north.\n\nTo the south you"+
			" see the ogre standing in the jail cell completely engaged in the coin moving"+
			" between his fingers.  To the east appears to be a long hallway with muddy walls.");
			return 2;
		case 2:
			setOutput("\n\nYou try to move in a blocked direction and the ogre becomes aware of your"+
			" presence.");
			this.getDeath2();
			return 3;
		case 5: //look
			setOutput("\n\nStanding with his back to the door, the ogre is fondling the shiny coin with"+
			" his digustingly fat fingers.  The ogre's obsession with the coin leaves the door"+
			" completely unguarded.");
			return 1;
		case 6: //listen
			setOutput("\n\nAs you stand in the corner you can hear a low rumble.  It appears"+
			" to be coming from the ogre's stomach.");
			return 1;
		case 7:
			setOutput("\n\nAs you try to grab the "+secondWord+", the ogre becomes aware of your presence.");
			this.getDeath2();
			return 3;
		case 8:
			setOutput("\n\nAs you try to drop the "+secondWord+", the ogre becomes aware of your presence.");
			this.getDeath2();
			return 3;
		case 9:
			setOutput("\n\nAs you try to hide again, you become unhidden and the ogre becomes aware"+
			" of your presence.");
			this.getDeath2();
			return 3;
		case 10:
			if(secondWord.equals("ogre")){
				this.getDeath3();
			}
			else{
				setOutput("\n\nAs you wildly try to attack the "+secondWord+", the ogre becomes aware of"+
				" your presense.");
				this.getDeath2();
			}
			return 3;
		case 11:
			setOutput("\n\nYour yelling has caught the attention of the now angry looking ogre.");
			this.getDeath2();
			return 3;
		case 12:
			setOutput("\n\nYour absentminded nod reveals you from the shadows of the corner and the"+
			" ogre becomes aware of your presence.");
			this.getDeath2();
			return 3;
		case 13:
			setOutput("\n\nThe ogre senses your sarcastic disapproval and moves closer.");
			this.getDeath2();
			return 3;
		}
		return 1; //default return for error
	}
	/**
	 * The actions to happen when ogre is dead and you go back to the room
	 */
	public int fourActions(){
		switch(word){
		case 1: setOutput("\n\nYou walk out into the hallway.");
				return 2;
		case 5: setOutput("\n\nThe dusty jail cell around you does not appear to have been"+
		" cleaned for quite some time.  An open jail cell door awaits you to the north.");
				break;
		case 6: setOutput("\n\nYou hear faint cheering in the distance.");
				break;
		}
		return 1;
	}
	
	//Common death text to be added to output after a player dies!
	
	private void getDeath2(){
		appendOutput("\n\nYou see the bloodlust in his eyes as"+
			" his large hand reaches for your head.  Your body slumps to the ground as your head"+
			" is crushed by the ogre's brute strength.");
		this.getDeath();
	}
	private void getDeath3(){
		setOutput("\n\nYour fists beat wildly on the chest of the ogre.  In one swift stroke the"+
		" ogre sends you flying against the wall with a heavy backhand.  One last breath"+
		" escapes from your crippled body.");
		this.getDeath();
	}
	
	
	/**
	 * Used for testing 
	 * @param args not used
	 */
	public static void main(String[] args) {
/*		
		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room1 room = new Room1(plyr, ogre, goblin, master);
		ogre.changeState(3);
		//room.getDeath();
		//System.out.println(room.getOutput());
		//System.out.println("ogre's state: "+ogre.getState());
		
		//Check zero state!
		
		//zero state look around room
		String s = "look nothing";
		String[] input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state s, should be can't go that way
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state e, should be can't go that way
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state w, should be can't go that way
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state listen first time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state listen second time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should be default error
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should be default error
		s = "grab butt nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state drop coin shouldn't work
		s = "drop coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state drop pants shouldn't work
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		zero state hide should work
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state attack shouldn't work
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		zero state attack shouldn't work
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state yell should be some crazy thing
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		zero state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		zero state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
		//**** Transition into State 1 **** /

//		zero state grab coin
		s = "grab coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
	
		//**************  STATE ONE - Have coin ******************* //
		
		//first state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state s, should be can't go that way
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state e, should be can't go that way
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state w, should be can't go that way
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state listen first time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state listen second time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab should be default error
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab coin should be default error
		s = "grab coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		

	
//		first state drop pants shouldn't work
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		first state hide should work
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state attack shouldn't work
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		first state attack shouldn't work
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	
//		first state yell should be some crazy thing and die
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		first state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		first state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		//System.out.println(""+plyr.getItem(1));
		
//***********  Transition into state two *********** //
		//first state drop coin should work
		s = "drop coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput()); 
		
//************ State Two tests   ***************   ///
		//only let one of these run at a time, because each one except hide should mean death
		
		
		
		//two state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state s, should be can't go that way
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state e, should be can't go that way
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state w, should be can't go that way
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state listen first time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state listen second time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state grab should be default error
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state grab should be default error
		s = "grab butt nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state drop coin shouldn't work
		s = "drop coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state drop pants shouldn't work
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		two state attack shouldn't work
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		two state attack shouldn't work
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state yell should be some crazy thing
		s = "yell ogre";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		two state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		two state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
		
		
//****************  Transition into State 3 *********************** //
		
//		two state hide should work
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//*************** State Three Tests ************************ //
		
		//Only N, look, listen and inventory should be non death
		//try all 4 in a row, with N last to make sure that none of them return death's
		
//		two state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
		//two state s, should be can't go that way
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state e, should be can't go that way
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state w, should be can't go that way
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state listen first time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
		//two state grab should be default error
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	
		//two state grab should be default error
		s = "grab coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//two state drop coin shouldn't work
		s = "drop coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state drop pants shouldn't work
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		two state attack shouldn't work
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		two state attack shouldn't work
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state yell should be some crazy thing
		s = "yell ogre";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		two state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		two state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		two state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
		
//		two state hide should work
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//***********  TRANSITION INTO ROOM TWO  ************** //
		
		
		//two state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
*/
	}

}
