/**
 * The first section of hallway you can go int
 * Either E to escape or yell/agree/disagree and then E
 * @author Thomas Silloway
 * @version 1.2
 *
 */
public class Room2 extends Rooms {

	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private Monster ogre;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room2(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		this.ogre = getOgre();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		
	}
	

	/**
	 * State 0 is initially without doing anything, if you yell, agree, or disagree then to 1
	 * State 1 is when ogre is in room with you, anything but move east,yell = die
	 */
	public int runFSM() {
		if(ogre.getState() == 3){ // ogre dead
			playerStatus = 1;
			state = 2;
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 3 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 2, but not called properly");
			System.exit(1);
		}
		
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		case 2: playerStatus = state2(); return playerStatus;
		}
		return 1;

	}
	
	/**
	 * State 0: normal
	 */
	public int state0(){
		if ((word == 1 || word == 4) || (word >= 7 && word <= 10) || (word == 14)){
			this.getDefault();
		}
		else{
			//System.out.println("Calling zeroActions() in Room2");
			return this.zeroActions();
		}
		return 1;
	}
	/**
	 * state 1: aggro from Ogre and die unless move east or yell = stun
	 */
	public int state1(){
		if (word == 3){
			return this.zeroActions();
		}
		else if(word == 11){
			this.oneActions();
			return 1;
		}
		else{
			this.oneActions();
			return 3;
		}
	}
	public int state2(){  //death state
		if((word == 1 || word == 4) || ((word >= 7 && word <=14))){
			this.getDefault();
		}
		else{
			return twoActions();
		}
		return 1;
	}
	
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * Nothing happened yet, change to state 1 if yell/disagree/agree
	 */
	private int zeroActions(){
		switch(word){
		
		case 2: setOutput("\n\nYour brain is screaming \'DANGER!\' but your body isn't"+
				" listening as your legs take you back into the jail cell.  The ogre looks up"+
				" from his coin and sees you.  He wraps his extraordinarily large hands around"+
				" your chest and begins to squeeze in anger.  You catch your last short breath"+
				" as the life is pressed out of you.");
				getDeath();
				return 3;  //you die
				
		case 3:	if(ogre.getState() == 0){
					setOutput("\n\nYou are in another section of hallway, which continues to the"+
					" east, with muddy walls around you.  Your eye catches a quick flash of light"+
					" at the end of a small alley to the south.  As you look behind you, to the"+
					" west, you hear a frustrated, booming yell come from the jail cell.  The ogre"+
					" must have finally realized that you have escaped!");
				}
				else {
					setOutput("\n\nYou are in another section of hallway, which continues to the"+
					" east, with muddy walls around you.  Your eye catches a quick flash of light"+
					" at the end of a small alley to the south.  As you look behind you, to the"+
					" west, the ogre is again walking slowly towards you.");
				}
				return 2;
				
		case 5:	setOutput("\n\nThe hall to the east darkens as fear from your recent escape lingers.");
				return 1;
				
		case 6: setOutput("\n\nThe distant sound of some sort of demons laughing and cheering"+
				" comes from the east.  The thought of traveling towards them heightens your fear.");
				return 1;
				
		case 11:
			setOutput("\n\nThe ogre guard hears a high pitched scream come from your mouth"+
			" and he looks up from his coin with frustration.  A look of pure horror consumes your"+
			" face as he starts walking slowly into the hallway towards you.");
			state = 1;
			ogre.changeState(1); //Ogre is now in chase mode
			return 1;
			
		case 12:
			setOutput("\n\nAs you absentmindedly nod your head, the ogre looks up"+
			" from his coin with frustration.  A look of pure horror consumes your face as he"+
			" begins to walk slowly into the hallway towards you.");
			state = 1;
			ogre.changeState(1); //Ogre is now in chase mode
			return 1;
		
		case 13:
			setOutput("\n\nAs you shake your head in sarcastic disagreement, the ogre looks up"+
			" from his coin with frustration.  A look of pure horror consumes your face as he"+
			" begins to walk slowly into the hallway towards you.");
			state = 1;
			ogre.changeState(1); //Ogre is now in chase mode
			return 1;
		}
		return 1;
	}
	/**
	 * State one's actions
	 * If ogre is in chase mode, only way to stay alive is to yell
	 */
	private void oneActions(){
		if(word == 1 || word == 4){
			word = 1;
		}
		switch(word){
		case 1: getDefault();
			break;
		case 2: setOutput("\n\nThe ogre blocks your path to the south.");
			break;
		case 5: setOutput("\n\nYou look around frantically.");
			break;
		case 6: setOutput("\n\nYou strain your ears to listen for a sign of hope.");
			break;
		case 7: setOutput("\n\nYou frantically try to grasp a wisp of hope.");
			break;
		case 8: setOutput("\n\nDropping your "+secondWord+" does nothing to help.");
			break;
		case 9: setOutput("\n\nThe ogre is looking straight at you, you can't hide!");
			break;
		case 10: setOutput("\n\nYour fists beat wildly on the chest of the ogre.  In one swift stroke, the"+
				" ogre sends you flying against the wall with a heavy backhand.  One last breath"+
				" escapes from your crippled body.");
				getDeath();
			break;
		case 11:
			setOutput("\n\nThe ogre guard covers his ears as a high pitched scream comes from your mouth."+
			" He is temporarily stunned giving you an opportunity to escape.");
			break;
		
		case 12:
			setOutput("\n\nThe ogre takes your nod as a threatening gesture.");
			break;
		case 13:
			setOutput("\n\nThe ogre takes your head shaking as a threatening gesture.");
			break;
		case 14:
			setOutput("\n\nYou quickly check your inventory and you still have nothing!");
			break;
		}
		if((word >=1 && word <=9) || (word >= 12 && word <= 14)){
			whichDeath();
		}
	}
	/**
	 * The actions to happen when ogre is dead
	 */
	public int twoActions(){
		switch(word){
		case 2: setOutput("\n\nYou walk back into the jail cell");
				return 4;
		case 3: setOutput("\n\nYou continue down the hallway.");
				return 2;
		case 5: setOutput("\n\nYou see the open jail cell to the south.  Studying the tracks in"+
				" the muddy floor, you see footprints leading to the east.");
				break;
		case 6: setOutput("\n\nYou hear faint cheering in the distance.");
				break;
		}
		return 1;
	}
	/**
	 * Determines which death to use based on the counter in player
	 * does getDeath2 or getDeath3
	 */
	private void whichDeath(){
		if((plyr.getCounter() % 2) == 0){
			getDeath2();
		}
		else{
			getDeath3();
		}
	}
	
	//Common death text to be added to output after a player dies!
	
	private void getDeath2(){
		appendOutput("  Your attempt to escape has failed.  The ogre tries to walk closer, but trips"+
		" over his own feet.  His massive body begins to fall to the ground, but you are"+
		" unfortunately in the way.  Your unprotected body is now just mush on the underside of"+
		" the ogre.");
		getDeath();
	}
	private void getDeath3(){
		appendOutput("  Your attempt to escape has failed.  The ogre towers over you as you cower"+
		" in fear.  Your sight goes black as your head is enveloped by the ogre's mouth.  The"+
		" last thing you hear is the gushing of your own blood as it spews out of the ogre's"+
		" mouth.");
		getDeath();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room2 room = new Room2(plyr, ogre, goblin, master);
		//ogre.changeState(3);
		//System.out.println("Number should be 0: "+ogre.getState());
		
		//Check zero state!
	
		//zero state look around room
		String s = "look nothing";
		String[] input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		plyr.increaseCounter();
		//zero state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state s, should be can't go that way  **works
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state e, should be can't go that way  **works
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
		
//		zero state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
// **********************  Transitions into state 1 ************************************** /
		
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
		
/*		
// ***********    State One   *********************** //
		
//		one state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state n
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state s
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state e
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state w
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state 
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//one state grab
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
			
		//one state drop coin
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
				
//		one state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		one state attack 
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		one state attack 
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		one state yell 
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		one state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		one state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		one state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
*/
	}

}
