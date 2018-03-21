/**
 * This is the 2nd part of the hallway
 * To escape go south
 * @author Thomas Silloway
 * @version 1.2
 */
public class Room3 extends Rooms {

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
	public Room3(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		this.ogre = getOgre();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		
	}
	

	/**
	 * State 0 is initially without doing anything, ogre is in next room to west so only one move
	 * 			before he catches up to you
	 * State 1 is when ogre is in room with you, anything but move east, south,yell = die
	 */
	public int runFSM() {
		//System.out.println("ogre's state, 0 if not chase, 1 if chase: "+ogre.getState());
		if(ogre.getState() == 3){ // ogre dead
			playerStatus = 1;
			state = 2;
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 4 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 2, but not called properly");
			System.exit(1);
		}
		if(ogre.getState() == 1){
			state = 1;
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
		if ((word == 1) || (word >= 7 && word <= 8) || (word >= 10 && word <=14)){
			this.getDefault();
			if(word == 14){
				appendOutput("\n\nThe ogre slowly walks up to you and growls.");
				ogre.changeState(1);
			}
			else{
				getOgreInRoom();
			}
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
		if (word == 2){
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
	public int state2(){  // ogre death state
		if((word == 1) || ((word >= 7 && word <=14))){
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
	 * Only get one move before ogre is in room with you
	 */
	private int zeroActions(){
		switch(word){
		
		case 2: setOutput("\n\nThe ogre stands just beyond the opening to the dark alley that"+
				" you ducked into.  He scratches his head in confusion, you must be hidden by"+
				" the darkness.   After your eyes adjust, you see the faint image of another human"+
				" at the end of the alley, but the bottom half of his torso is missing!  His upper half looks very"+
				" similar to yourself and even has on the same clothes.");
				ogre.changeState(0);//he gets lost, so he doesn't know where you are
				return 2;  //move south
				
		case 3:	setOutput("\n\nAs you try to move east, the ogre lets out a rumbling bellow"+
				" that stuns you in your tracks.  You fail to move east.");
				getOgreInRoom();
				return 1;
		
		case 4: setOutput("\n\nYour brain is screaming \'DANGER!\' but your body isn't"+
				" listening as your legs take you back towards the ogre.  He is still walking"+
				" towards you, but doesn't react quickly enough to your agile move his in"+
				" direction.  You are crushed beneath the ogre's next step.");
				getDeath();
				return 3;  //you die
				
		case 5:	setOutput("\n\nThe hall to the east darkens as the ogre catches up to you from the west.");
				getOgreInRoom();
				return 1;
				
		case 6: setOutput("\n\nThe distant sound of some sort of demons laughing and cheering"+
				" comes from the east.  The thought of traveling towards them heightens your fear.");
				getOgreInRoom();
				return 1;
		case 9: setOutput("\n\nYou can't hide, the ogre is looking straight at you!");
				getOgreInRoom();
				return 1;
		}
		return 1;
	}
	/**
	 * State one's actions
	 * If ogre is in chase mode, only way to stay alive is to yell
	 */
	private void oneActions(){
		if(word == 3 || word == 4){
			word = 3;
		}
		if(word == 12 || word == 13){
			word = 12;
		}
		switch(word){
		case 1: getDefault();
			break;
		case 3: setOutput("\n\nThe ogre grabs you as you try to run.");
			break;
		case 5: setOutput("\n\nYou turn around looking for an escape path, but it's too late.");
			break;
		case 6: setOutput("\n\nYou can only hear your heart pounding as the ogre towers over you.");
			break;
		case 7: setOutput("\n\nGrabbing the "+secondWord+" does nothing to help.");
			break;
		case 8: setOutput("\n\nDropping your "+secondWord+" does nothing to help.");
			break;
		case 9: setOutput("\n\nThe ogre is looking straight at you, you can't hide!");
			break;
		case 10: setOutput("\n\nYour fists beat wildly on the chest of the ogre.  In one"+
			" strong blow, the ogre's massive fist crushes your head.  Your lifeless body"+
			" falls to the ground.");
				getDeath();
			break;
		case 11:
			setOutput("\n\nThe ogre guard covers his ears as a high pitched scream comes from your mouth."+
			" He is temporarily stunned giving you an opportunity to escape.");
			break;
		
		case 12:
			setOutput("\n\nThe ogre reacts quickly to the first sign of movement");
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
		case 2: setOutput("\n\nYou walk back into the dark room to the south.");
				return 2;
		case 3: setOutput("\n\nAs you walk into the end of the hallway, water starts quickly"+
				" running down the muddy walls behind you.  The walls begin to erode away as"+
				" the water passes over them.  Almost instantly, the ceiling above the hallway to the"+
				" west collapses.  A thick layer of mist and fog covers"+
				" the floor around you and appears to be coming at you from all sides.  Behind the"+
				" fog to the north, you can barely make out an open gateway guarded by what appears to be a"+
				" goblin.  There are symbols written in the muddy walls to the south and east."+
				"  You cannot decipher their meaning.");
				
				return 4;
		case 4: setOutput("\n\nYou go back to the west.");
				return 5;
		case 5: setOutput("\n\nThe hallway continues to the east and west.  Through the darkness to the south, you"+
				" can barely see the outline of the ogre that you brutally"+
				" murdered with the mirror.");
				break;
		case 6: setOutput("\n\nYou hear faint cheering and the clang of steins in the distance.");
				break;
		}
		return 1;
	}
	/**
	 * Determines which death to use based on the counter in player
	 * does getDeath2 or getDeath3
	 */
	private void whichDeath(){
		if((plyr.getCounter() % 3) == 0){
			getDeath2();
		}
		else if((plyr.getCounter() % 3) == 1){
			getDeath3();
		}
		else{
			getDeath4();
		}
	}
	private void getDeath2(){
		appendOutput("  Your attempt to escape has failed.  The ogre picks you up by the legs"+
		" and smashes you into the ceiling and walls.  Everyone bone in your body is broken as"+
		" he throws you to the ground.");
		getDeath();
	}
	private void getDeath3(){
		appendOutput("  Your attempt to escape has failed.  The ogre yells, \'Me not fail the"+
		" master!\'  He grabs you by the back of the head and pushes your face into the muddy"+
		" floor.  A tingling sensation flows through you as you suffocate.");
		getDeath();
	}
	private void getDeath4(){
		appendOutput("  Your attempt to escape has failed.  The ogre yells, \'Me not fail the"+
		" master!\'  He smacks you with the back of his hand and your lifeless body flies into"+
		" the northern wall.");
		getDeath();
	}
	private void getOgreInRoom(){
		appendOutput("  The ogre slowly walks up to you and growls.");
		ogre.changeState(1);
	}
	/**
	 * main method used for testing
	 * @param args not used
	 */
	public static void main(String[] args){
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room3 room = new Room3(plyr, ogre, goblin, master);
		String s = "";
		String[] input;
//		ogre.changeState(1); //coming in initially with ogre aggroed
		ogre.changeState(3);//ogre is dead
		//Check zero state!
	
//		zero state w
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state s
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	

		
// **********************  Transitions into state 1 ************************************** /
		
//		zero state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		plyr.increaseCounter();
		plyr.increaseCounter();
		
		//zero state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state e, should be can't go that way  **works
		s = "e nothing";
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
				
		//zero state drop coin shouldn't work
		s = "drop coin nothing";
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
		
//		zero state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
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
		s = "drop coin nothing";
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
		

		
//		one state inventory check, should be empty
		s = "inventory nothing";
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
*/		
		
	}
	
}