
public class Room8 extends Rooms {
	private String secondWord;
	private int word; //the number used for FSM
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room8(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		
	}
	

	/**
	 * State 0 is initially without doing anything, give special output, append regular
	 * State 1 same as zero, except you die at the end
	 */
	public int runFSM() {
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 2, but not called properly");
			System.exit(1);
		}
		
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		}
		return 1;

	}
	
	/**
	 * State 0: do zero actions and move to state 1
	 */
	public int state0(){
		this.zeroActions();
		state = 1;
		return 1;
	}
	/**
	 * state 1: you die from bugs!
	 */
	public int state1(){
		this.zeroActions();
		return 3;
	}
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * Nothing happened yet, you die if state = 1
	 */
	private void zeroActions(){
		setOutput("\n\n");
		if(word >= 1 && word <=4){
			word = 1;
		}
		switch(word){
		
		case 1: appendOutput("As you stumble over yourself while trying to run,");
				break;  
				
		case 5:	appendOutput("As you try to look around in the dark,");
				break;
		case 6:	appendOutput("As you try to listen for a sign of hope,");
				break;
				
		case 7: appendOutput("As you try to grasp a wisp of hope,");
				break;
		case 8: appendOutput("As you try to drop your "+secondWord+",");
				break;
		case 9: appendOutput("As you stumble over yourself while trying to hide,");
				break;
		case 10: appendOutput("As you futilely try to attack something in the dark,");
				break;
		case 11: appendOutput("As you let out a cry for help,");
				break;
		case 12: appendOutput("As you agree with this horrible situation,");
				break;
		case 13: appendOutput("As you shake your head in disbelief,");
				break;
		case 14: appendOutput("As you try to reach down to check your inventory,");
		}
		if(state == 0){
			appendOutput(" you feel an itchy sensation by your feet and small pricks of pain.  You"+
			" begin to scream in agony as you realize you are being eaten alive by tiny insects."+
			"  They are devouring your flesh and crawling up your legs.");
		}
		else{
			appendOutput(" you find yourself in extreme pain.  The insects move up your body"+
			" and after eating all of your flesh, they begin to feast on your innards.  You"+
			" collapse to the ground in silence and your life slips away from your body.");
			getDeath();
		}
	}
	/**
	 * Used for testing only
	 * @return the state
	 */
	public int getState(){
		return state;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room8 room = new Room8(plyr, ogre, goblin, master);
		
		//Check zero state!  Test all one at a time
		
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
		s = "grab bum nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state drop coin shouldn't work
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
		s = "attack bugs nothing";
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
		

		//System.out.println("new State number: "+room.getState());
	
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
		s = "attack bugs nothing";
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

	//used to get dead call
//		first state listen second time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
*/
	}

}
