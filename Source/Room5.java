/**
 * Bug with shard and bucklers not going to state 1 soon enough.
 * @author Thomas
 *
 */
public class Room5 extends Rooms {
	
	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private Monster goblin;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	private int numberItems; // the number of items player has
	private boolean daggerDropped; //if the dagger is on the ground, true, false otherwise
	private boolean time; //true if first time that FSM is called
	private boolean hasBeenToRoom6;
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room5(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		this.goblin = getGoblin();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		time = true;
		if(this.plyr.getItem(3)){//has shard
			numberItems = 1;
		}
		else{
			numberItems = 0;
		}
		daggerDropped = false;
		hasBeenToRoom6 = false;
		
	}

	/**
	 * State 0 is initially without doing anything, grab too many swords or shields and goto state 1
	 * State 1 is when you are overloaded with swords and shields, pick up another and you fall on table and die.
	 */
	public int runFSM() {
		if(hasBeenToRoom6){
			playerStatus = 1;
		}
		if(time && plyr.getItem(3)){
			numberItems = 1;
		}
		time = false;
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 6 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 5, but not called properly");
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
	 * State 0: normal, grab too many swords and shields to goto 1, yell = die
	 */
	public int state0(){
		if ((word >= 2 && word <= 4) || (word >= 9 && word <= 10) || (word >= 12 && word <=14)){
			this.getDefault();
		}
		else{
			return this.zeroActions();
		}
		return 1;
	}
	/**
	 * state 1: can only drop stuff or grab another and die, otherwise almost passed out
	 */
	public int state1(){
		if (word == 11){  //yell and die
			return this.zeroActions();  
		}
		else if((word >= 9 && word <= 10) || (word >= 12 && word <=14)){
			this.getDefault();
			return 1;
		}
		else{
			return this.oneActions();	
		}
	}

	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * Die if yell, grab more than 3 items = overloaded.
	 * Look should change for dagger on the ground
	 */
	private int zeroActions(){
		//System.out.println("DEBUG- Number of items in inventory: "+numberItems);
		switch(word){
		
		case 1: 
			if(goblin.getState() != 3){
				setOutput("\n\nYou move out of the fog towards the goblin.  He is wearing a"+
				" tight fitting shirt and leather pants and wields no weapons, yet he"+
				" intimidates you.  With his fists wrapped in cloth, he strikes at you, but"+
				" you luckily sidestep out of the way. ");
				hasBeenToRoom6 = true;
			}
			else{
				setOutput("\n\nYou move north to the room with the gate.");
			}
				return 2;  //move north
				
		case 5:	
			if(!daggerDropped){
				setOutput("\n\nYou have a hard time seeing anything through the dense fog,"+
				" but you can see a small table in the corner of the room.  Along the side of"+
				" the table there lies a line of short swords with their handles on the ground"+
				" and the points resting against the edge of the table.  On top of the table you"+
				" see several worn wooden bucklers.");
			}
			else{
				setOutput("\n\nYou have a hard time seeing anything through the now dense fog,"+
				" but you can see a small table in the corner of the room.  Along the side of"+
				" the table there lies a line of short swords with their handles on the ground"+
				" and the tips resting against the edge of the table.  On top of the table you"+
				" see several shabby wooden bucklers.  A shard of glass is on the ground near you.");
			}
				return 1;
				
		case 6: setOutput("\n\nYou can hear the dense fog swirling around you.");
				return 1;
				
		case 7: 
			//can't pick up dagger and sword  or two swords at same time
			if(((plyr.getItem(3) || plyr.getItem(4)) && secondWord.equals("sword")) ||
				(plyr.getItem(4) && secondWord.equals("shard") && daggerDropped)){
				setOutput("\n\nYou cannot pick up more than one offensive weapon at a time.");
				return 1;
			}
		  if(numberItems <= 1){
			if(secondWord.equals("sword")){
				setOutput("\n\nYou take a short sword away from the table.  It feels sturdy"+
				" in your hands.");
				plyr.flagItem(true, 4);
			}
			else if(secondWord.equals("buckler")){
				setOutput("\n\nYou take a wooden buckler off of the table.  The wood appears"+
				" to be rotting.");
				plyr.flagItem(true, 5);
			}
			else if(!plyr.getItem(3) && daggerDropped && 
					(secondWord.equals("shard") || secondWord.equals("glass"))){
				setOutput("\n\nYou pick up the shard of glass off the ground and it cuts your"+
				" hand.  You are bleeding.");
				plyr.flagItem(true, 3);
				plyr.changeState(1);
				daggerDropped = false;
			}
			else{
				this.getDefault();
				return 1;
			}
			numberItems++;
		  }
		  else if(numberItems >= 2){
			setOutput("\n\nYou feel very heavy as you become encumbered with the additional"+
			" weight.  Your legs are incapacitated, but you are still standing for now.");
			if(secondWord.equals("sword")){
				plyr.flagItem(true, 4);
			}
			else if(secondWord.equals("buckler")){
				plyr.flagItem(true, 5);
			}
			else if(!plyr.getItem(3) && daggerDropped && 
					(secondWord.equals("shard") || secondWord.equals("glass"))){
				appendOutput("  You are bleeding.");
				plyr.flagItem(true, 3);
				plyr.changeState(1);
				daggerDropped = false;
			}
			else{
				this.getDefault();
				return 1;
			}
			numberItems++;
			state = 1;
		  }
		  break;
		case 8:
			if(secondWord.equals("sword") && plyr.getItem(4)){
				setOutput("\n\nYou place the sword back against the table.");
				plyr.flagItem(false, 4);
			}
			else if(secondWord.equals("buckler") && plyr.getItem(5)){
				setOutput("\n\nYou place the buckler back on the table.");
				plyr.flagItem(false, 5);
			}
			else if((secondWord.equals("shard") || secondWord.equals("glass")) && plyr.getItem(3)){
				setOutput("\n\nYou drop the shard of glass on the ground.  Dropping the dagger"+
				" cures your wounds, you are no longer bleeding.");
				plyr.flagItem(false, 3);
				plyr.changeState(0);
				daggerDropped = true;
			}
			else{
				this.getDefault();
				return 1;
			}
			numberItems--;
			break;
		case 11: //yell = die
			setOutput("\n\nAs you scream at the top of your lungs, you hear the goblin guard"+
			" to the north yell something.  Before you have time to react, the room is filled"+
			" with red goblins.  Most of them appear to be slightly drunk and still have their"+
			" steins in hand, but it only takes the one guard to slay you.");
			getDeath();
			return 3;
		}
		return 1;
	}
	/**
	 * State one's actions
	 * If ogre is in chase mode, only way to stay alive is to yell
	 */
	private int oneActions(){
		if(word >= 1 && word <= 4){
			word = 1;
		}
		if(word == 12 || word == 13){
			word = 12;
		}
		switch(word){
		case 1: setOutput("\n\nYou are encumbered.  You cannot move!");
			break;
		case 5: setOutput("\n\nThe rooms starts to get black as the weight of your extra weapon"+
				" overloads your senses.");
			break;
		case 6: setOutput("\n\nYou faintly hear the fog swirling about you over the loud"+
				" ringing in your ears from extra weight on your body.");
			break;
		case 7: 
			if(secondWord.equals("sword") || secondWord.equals("buckler") ||
				((secondWord.equals("shard") || secondWord.equals("glass")) && daggerDropped)){
					setOutput("\n\nAs you pick up the "+secondWord+", you fall towards the"+
					" table, collapsing from the weight of too many weapons.  Your head hits"+
					" hard on the table and your chest is pierced by several of the swords that"+
					" are lined up along the table.");
					getDeath();
					return 3;
			}
			else{
				getDefault();
			}
			break;
		case 8: 
			if(secondWord.equals("sword")){
				setOutput("\n\nYou feel light again as you put the short sword back on the"+
				" table");
				plyr.flagItem(false, 4);
			}
			if(secondWord.equals("buckler")){
				setOutput("\n\nYou feel light again as you put the wooden buckler back on the"+
				" table");
				plyr.flagItem(false, 5);
			}
			if((secondWord.equals("shard") || secondWord.equals("glass")) && plyr.getItem(3)){
				setOutput("\n\nYou feel light again as you drop your shard of glass on the"+
				" ground.  You are no longer bleeding.");
				plyr.flagItem(false, 3);
				daggerDropped = true;
			}
			numberItems--;
			state = 0;
			break;
		}
		return 1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		//plyr.flagItem(true, 3);
		Room5 room = new Room5(plyr, ogre, goblin, master);
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
		
		//zero state n, should be exit
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
		
		//zero state grab should get a sword
		s = "grab buckler nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should get a shield
		s = "grab buckler nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
	//zero state drop coin shouldn't work
		s = "drop shard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state drop coin shouldn't work
		s = "grab buckler nothing";
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
		
		
//		zero state yell should = dead
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
		
//		zero state inventory check
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
		//**** Transition into State 1 **** /
	
//		zero state grab coin
		s = "grab buckler nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
		System.out.println("\n**State 1**");
		
// ***************  STATE 1  ************************************************ //
		
//		one state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());

		
//		zero state n, should be exit
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
		
		//zero state grab should get a sword
		s = "grab sword nothing";
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
		
		
//		zero state yell should = dead
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
		
//		zero state inventory check
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
// **********  Transition to state 0   ************************ //
		
//		zero state drop buckler
		s = "drop buckler";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		System.out.println("\n**State 0**");
//		one state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//**** Transition into State 1 **** /
		
//		zero state grab coin
		s = "grab buckler nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
		System.out.println("\n**State 1**");
		
// ***************  STATE 1  ************************************************ //
		
//		one state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
*/		
	}

}
