
public class Room7 extends Rooms {
	
	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	private boolean leftRoom; //true if you have left the room, false if you haven't
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room7(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		leftRoom = false;
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
	}
	
	/**
	 * State 0 is the gateway to room 8, 9 and 10.
	 */
	public int runFSM() {
		if(leftRoom){
			playerStatus = 1;
			leftRoom = false;
		}
		//System.out.println("DEBUG- FSM called.");
		if(playerStatus == 2){//moved north
			System.out.println("ERROR:: Room 8 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 4){//moved west
			System.out.println("ERROR:: Room 9 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 5){//moved east
			System.out.println("ERROR:: Room 10 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 7, but not called properly");
			System.exit(1);
		}
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		}
		return 1;
	}
	
	/**
	 * State 0: combat with dagger or sword
	 */
	public int state0(){
		if((word == 2) || word == 7 || ((word >= 9 && word <=10)) ||
			(word >= 12 && word <= 14)){
			this.getDefault();
		}
		else{
			//System.out.println("DEBUG- calling two actions from state2()");
			return zeroActions();
		}
		return 1;
	}
	/**
	 * The actions to happen when you are in the 3 way entrance room.
	 * yell or move e without invis = die
	 */
	public int zeroActions(){
		//System.out.println("DEBUG- word number: "+word);
		switch(word){
		case 1: setOutput("\n\nAs you open the door and step into a very dark room, you suddenly"+
				" remember where you have seen those large letters from the door before.  The"+
				" last time you saw them, they were words of warning posted on a sign outside"+
				" of a small cave in the middle of the woods.  Fear overwhelms you as the door"+
				" slams shut behind you and you are engulfed in complete darkness.");
				return 2; //goto room 8
		case 3: 
			if(plyr.getState() == 2){
				setOutput("\n\nAs you enter the Great Hall, your breath is taken away by its"+
				" vastness.  The size of this hall could be filled with a hundred elephants!"+
				"  There are thousands of goblins sitting at tables around you raising up their"+
				" steins, clanking them together and taking a drink as a larger goblin"+
				" speaks with authority from the front.  In front of this goblin, who you"+
				" believe to be the master, lies a large sheep on a long wooden table.  It"+
				" lies on its back and its underside has been sliced open tainting the pure"+
				" white wool with the red stain of blood.  The goblins are unaware of your presence.");
				leftRoom = true;
				return 5; //goto room 10
			}
			else{
				setOutput("\n\nAs you enter the Great Hall, your breath is taken away by its"+
				" vastness.  Thousands of goblins, sitting at tables with empty steins are now"+
				" looking at you.  They all have a look of surprise on their faces.  A larger"+
				" goblin near the front of the hall yells, \'Slay the human!\'  Before you"+
				" have time to react, goblin after goblin dives on you.  Your body is quickly"+
				" ripped apart, but your head is left intact and delivered to the larger"+
				" goblin at the front of the room.  \'Well, we were going to feast on his bones"+
				" eventually!\'  The goblins burst out with a roar of laughter.");
				getDeath();
				return 3; //die!
			}
		case 4: setOutput("\n\nYou open the door and walk into the room to your west.  You are"+
				" struck by awe as you look around at the vast library spanning the north and"+
				" west walls.  The south wall is covered by similar shelves that the books rest"+
				" on, but these shelves are filled with thousands and thousands of vials and"+
				" jars.  Some of the jars are filled with organs that vary in size and the"+
				" vials are full of a stunning array of colored liquids.  Due to the vastness"+
				" of the room, you completely overlooked a goblin standing directly in the"+
				" middle, right in front of a gigantic cast iron pot.  A dull blue"+
				" mist rises from the bubbling contents of the pot distorting the goblin's"+
				" figure.");
				leftRoom = true;
				return 4; //goto room 9
		case 5: 
			setOutput("\n\nAs you try to figure out which door to go through, the goblins to"+
			" the east let out a loud cheer and clank their steins together once again.  They"+
			" are unaware of your presence.  The"+
			" locked gate to the south stills frightens you, how did it magically get locked"+
			" shut?"); 
				break;
		case 6: setOutput("\n\nYou hear the goblins to the east starting a soft chant.");
				break;
		case 8: 
			if(secondWord.equals("shard") || secondWord.equals("buckler") || secondWord.equals("sword")){
				setOutput("\n\nYou don't want to drop your weapons, there may be more battles to come.");
			}
			else{
				getDefault();
			}
				break;
		case 11: //yell = die
			setOutput("\n\nAs you scream at the top of your lungs, you hear a rush of small feet"+
					" coming towards you.  Before you have time to react, the room is filled"+
					" with red goblins.  Most of them appear to be slightly drunk and still have their"+
					" steins in hand, but it only takes the one guard to slay you.");
					getDeath();
					return 3;
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
		Room7 room = new Room7(plyr, ogre, goblin, master);
		//ogre.changeState(3);
		//room.getDeath();
		//System.out.println(room.getOutput());
		//System.out.println("ogre's state: "+ogre.getState());
		plyr.flagItem(true,4);
		plyr.changeState(2);
		//Check zero state!
		
		//zero state look around room
		String s = "look nothing";
		String[] input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state n, goto dark room
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state e, should be goto great hall
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	
		//zero state w, should be goto library
		s = "w nothing";
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
		
		//zero state listen first time
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should be default error
		s = "grab goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state drop coin shouldn't work
		s = "drop sword nothing";
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
		s = "attack door nothing";
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
		
		
		//**** used to get the error if you change rooms **** /

//		zero state grab coin
		s = "grab coin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());			
*/
	}

}
