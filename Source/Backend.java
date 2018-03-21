/**
 * This is the backend model of Dungeon of Despair
 * This class will keep track of which room the player is in
 * Using a Finite State Machine of sorts (looping is handled by GUI)
 * Displays opening text of the game.
 * @author Thomas Silloway
 * @version 1.10
 */
public class Backend {
	
	private String[] input;
	private String output;
	private int state;
	private Player plyr;
	private boolean firstTime; //true if it is the first time program is run
	//rooms
	private Room1 room1;
	private Room2 room2;
	private Room3 room3;
	private Room4 room4;
	private Room5 room5;
	private Room6 room6;
	private Room7 room7;
	private Room8 room8;
	private Room9 room9;
	private Room10 room10;
	//monsters
	private Monster ogre;
	private Monster goblin;
	private Monster master;  
	/**
	 * null constructor
	 */
	public Backend(){
		input = "initial input".split("\\s");
		output = "";
		state = 0;
		firstTime = true;
		initializeMonsters();
		initializeRooms();
		output = "";
	}
	/**
	 * Called by GUI, sets the input
	 * @param s the input as given by the GUI
	 */
	public void setText(String s){
		//give string extra word so no errors will occur later on when calling input[1]
		s += " nothing";
		input = s.split("\\s");
		//System.out.println("input: "+input[0]);
	}
	/**
	 * Called by GUI, gets the output
	 * @return the output that should be displayed to user
	 */
	public String getOutput(){
		return output;
	}
	
	/**
	 * Runs the FSM.  State zero is new game
	 * Room 1 is the jail, north to hallway
	 * Room 2 the first section of hallway, move east to room 3
	 * Room 3 is the second section of hallway, move south to dark room, or east to foggy room
	 * Room 4 is the dark room with the mirror in it
	 * Room 5 is the foggy room with weapons and armor
	 * Room 6 is the goblin guard encounter
	 * Room 7 is the three way connector to Room 8, 9, and 10
	 * Room 8 is a death trap
	 * Room 9 is where you talk to the old man and get invisible
	 * Room 10 is the final room, kill master or use him as a hostage to escape and win
	 */
	public void runFSM(){
		//System.out.println("state variable: "+state);
		plyr.increaseCounter();
		int inRoom = 1; //1 for in room, 2 for next room, 3 for restart
		switch (state){
		
		case 0: if(!firstTime){
					initializeMonsters();
					initializeRooms();
				}
				firstTime = false;
				output += "\n\nA cold breeze drifts through a damp and dark room in which you are now"+
				" visitor.  You become chilled as you look down to see only a ragged tunic"+
				" and leather pants covering your body.   With only three mud walls in view,"+
				" you spin around to take in the rest of the room.  You begin to frown as you"+
				" realize that you are not a visitor, but a prisoner in some kind of dilapidated"+
				" cell.  Through the metal bars that block the northern exit, you can see the shadow of a"+
				" large ogre standing right outside of your door.  You wonder how you are going"+
				" to escape the Dungeon of Despair.";
				state = 1;
				break;
				
				
		case 1: //System.out.println("In room 1");
				room1.setText(input);
				inRoom = room1.runFSM();
				output = room1.getOutput();
				//System.out.println(output);
				if (inRoom == 2)  
					state = 2;     //enter north
				if (inRoom == 3)
					state = 0;		//new game
				break;
				
		case 2: room2.setText(input);
				inRoom = room2.runFSM();
				output = room2.getOutput();
				if (inRoom == 2)
						state = 3;  //enter east
				if (inRoom == 3)
						state = 0;  //new game
				if (inRoom == 4)
						state = 1; //go south
				break;
				
		case 3: room3.setText(input);
				inRoom = room3.runFSM();
				output = room3.getOutput();
				if (inRoom == 2)
					state = 4;  //enter south
				if (inRoom == 3)
					state = 0;  //new game
				if (inRoom == 4)
					state = 5; // went east
				if (inRoom == 5)
					state = 2; //went west
				break;
		
		
		case 4: room4.setText(input);
				inRoom = room4.runFSM();
				output = room4.getOutput();
				if (inRoom == 2)
					state = 3;  //enter next room
				if (inRoom == 3)
					state = 0;  //new game
				break;
		
		case 5: room5.setText(input);
				inRoom = room5.runFSM();
				output = room5.getOutput();
				if (inRoom == 2)
					state = 6;  //enter next room
				if (inRoom == 3)
					state = 0;  //new game
				break;
		
		case 6: room6.setText(input);
				inRoom = room6.runFSM();
				output = room6.getOutput();
				//System.out.println("inRoom variable: "+inRoom);
				if (inRoom == 2)
					state = 7;  //enter next room
				if (inRoom == 4){
					//System.out.println("inRoom variable: "+inRoom);
					state = 5;  //enter room 5
				}
				if (inRoom == 3)
					state = 0;  //new game
				break;
				
		case 7: room7.setText(input);
				inRoom = room7.runFSM();
				output = room7.getOutput();
				if (inRoom == 2)
					state = 8;  //enter next room
				if (inRoom == 4)
					state = 9; //go west
				if (inRoom == 5)
					state = 10; //go east
				if (inRoom == 3)
					state = 0;  //new game
				break;
		
		case 8: room8.setText(input);  //you die in this room
				inRoom = room8.runFSM();
				output = room8.getOutput();
				if (inRoom == 3)
					state = 0;  //new game
				break;	
		
		case 9: room9.setText(input);
				inRoom = room9.runFSM();
				output = room9.getOutput();
				if (inRoom == 2)
					state = 7;  //enter next room
				if (inRoom == 3)
					state = 0;  //new game
				break;
			
		case 10:
			room10.setText(input);
				inRoom = room10.runFSM();
				output = room10.getOutput();
				if (inRoom == 3)
					state = 0;  //new game
				if (inRoom == 2)
					state = 7;
		}

		
	}
	
	/**
	 * initializes the rooms for a new game
	 */
	private void initializeRooms(){
		room1 = new Room1(plyr, ogre, goblin, master);
		output = "\n\n\n\n\n\n";
		room2 = new Room2(plyr, ogre, goblin, master);
		room3 = new Room3(plyr, ogre, goblin, master);
		room4 = new Room4(plyr, ogre, goblin, master);
		room5 = new Room5(plyr, ogre, goblin, master);
		room6 = new Room6(plyr, ogre, goblin, master);
		room7 = new Room7(plyr, ogre, goblin, master);
		room8 = new Room8(plyr, ogre, goblin, master);
		room9 = new Room9(plyr, ogre, goblin, master);
		room10 = new Room10(plyr, ogre, goblin, master);
	}
	/**
	 * initializes the Monsters and Player
	 */
	private void initializeMonsters(){
		plyr = new Player();
		ogre = new Monster();
		goblin = new Monster();
		master = new Monster();
	}
	/**
	 * Used for testing
	 * @param args not used
	 */
	public static void main(String[] args) {
/*		Backend backend = new Backend();
		
		//Room 1
		
		//Typical win
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("drop coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("hide");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		
/*		//Dying test 
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("drop coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("yell");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		//restart game test
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("listen");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("drop coin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		//Room 2
		
		//Typical win 1
		backend.setText("e");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
/*		//Typical win 2
		backend.setText("yell");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		
		//Room 3
		
		//Typical win 1  
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("s");
		backend.runFSM();
		System.out.println(backend.getOutput());

		
/*		//Typical win 2
		backend.setText("s");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
	
		
		//Room 4
		
		//Typical win and back to room 5
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab mirror");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("attack ogre");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("s");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("s");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e");
		backend.runFSM();
		System.out.println(backend.getOutput());

		
		//Room 5
		
		//Typical win
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab buckler");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab sword");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		//Room 6
		
		//Typical win
		backend.setText("attack goblin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("attack goblin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("s");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		//room 7
		
		//typical win
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		//room 8
		/*
		//you die in here!
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("attack");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("look");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		//room 9
		
		//typical win
		backend.setText("attack goblin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("agree");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("disagree");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("w");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("attack goblin");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e"); //goto room 7
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("e"); //goto room 10
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		
		//room 10
		
		//Typical win
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab master");
		backend.runFSM();
		System.out.println(backend.getOutput());
		
		backend.setText("grab vial");
		backend.runFSM();
		System.out.println(backend.getOutput());

		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());

		backend.setText("n");
		backend.runFSM();
		System.out.println(backend.getOutput());
*/
	}

}
