
public class Room10 extends Rooms {
	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	private boolean firstAttack; //true if you have left the room, false otherwise
	private int timeInState0;
	private boolean leftRoom;
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room10(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		firstAttack = true;
		timeInState0 = 0;
		leftRoom = false;
		
	}
	

	/**
	 * State 0 is initially without doing anything, move n two times to goto state 1
	 * 			  make grab stein = dead
	 * State 1 you are uninvis, all but attack master to 2 or grab master to 3 = dead
	 * State 2 attacking with master, after a couple round of atk, he dies and you live to 4
	 * State 3 have master by the throat with weapon, grab potion stay in 3, n leave and win
	 * state 4 can go n to win, south = trip and die , other defaults
	 */
	public int runFSM() {
		if(leftRoom){
			playerStatus = 1;
			leftRoom = false;
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 7 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 10, but not called properly");
			System.exit(1);
		}
		
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		case 2: playerStatus = state2(); return playerStatus;
		case 3: playerStatus = state3(); return playerStatus;
		case 4: playerStatus = state4(); return playerStatus;
		}
		return 1;

	}
	
	/**
	 * State 0: can go n once and stay in, twice to state 1, anything not default = dead
	 */
	public int state0(){
		if(timeInState0 >= 1 && word == 2){
			zeroActions();
		}
		else{
		  if ((word == 2 || word == 3) || word == 9 ||
			(word >= 12 && word <= 14)){
			this.getDefault();
		  }
		  else{
		 	return this.zeroActions();
		  }
		}
		return 1;
	}
	/**
	 * State 1 you are uninvis, all but attack master to 2 or grab master to 3 = dead
	 * @return
	 */
	public int state1(){
		return oneActions();
	}
	/**
	 * state 2: you agreed with the first statement agree/disagree to state 0
	 */
	public int state2(){
		return twoActions();
	}
	/**
	 * State 3: You have the master by the throat, you can grab his vial and run to the north
	 */
	public int state3(){  // ogre death state
		return threeActions();
	}
	/**
	 * state 4 can go n to win, south = trip and die , other defaults
	 */
	public int state4(){ //everything is dead
		if((word >= 3 && word <= 4) || (word >= 9 && word <= 14)){
			getDefault();
			appendOutput("  You are bleeding, you should get out of there!");
		}
		else{
			return fourActions();
		}
		return 1;
	}
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State 0: can go n once and stay in, twice to state 1, anything not default = dead
	 * Grab stein = dead, otherwise default and not dead
	 * Don't die if you yell cause you are invis and they are drunk
	 */
	private int zeroActions(){
		switch(word){
		
		case 1: 
			if(timeInState0 == 0){
			  setOutput("\n\nYou begin to move north along the side of the room with great"+
			  " care.  Invisibility may hide you from sight, but it does not dampen the sound of"+
			  " footsteps.  Luckily, the goblins are being so loud with their drinking ritual"+
			  " that there is no chance they can hear you.  The smell of the rotting sheep blood"+
			  " becomes stronger.");
			}
			else{
				setOutput("\n\nYou slowly creep up the stairs and you feel your skin stop tingling."+
				"  You look down and can see your body!  You look up and the master goblin is"+
				" gazing at you with hatred.");
				state = 1;
			}
			timeInState0++;
			break;  //move east
			
		case 2: setOutput("\n\nYou begin to move south along the side of the room with great"+
			" care.  Invisibility may hide you from sight, but it does not dampen the sound of"+
			" footsteps.  Luckily, the goblins are being so loud with their drinking ritual"+
			" that there is no chance they can hear you.  The smell of the rotting sheep blood"+
			" becomes weaker.");
			timeInState0 = 0;
			break;
		case 4:
			if(timeInState0 == 0){
				setOutput("\n\nYou move west into the three way connecting room.  There are"+
				" doors to the west and north and the Great Hall to the east.");
				leftRoom = true;
				return 2;
			}
			else{
				setOutput("\n\nYou cannot go that way!");
			}
			break;
		case 5:	setOutput("\n\nAs you look around the room, you notice the drunken look on"+
			" all of the goblins'  faces.  One of them is spinning his empty stein on the table"+
			" like a top.");
			if(timeInState0 >= 1){
				appendOutput("  The only way to get around the master's throne is to walk up"+
				" the steps directly in front of him and walk around his throne.");
			}
			else{
				appendOutput("  The exit is farther north of you behind the master's throne.");
			}
			break;
				
		case 6: setOutput("\n\nThe goblins around you start chanting softly as the master in"+
			" the front waves his hands over the dead lamb.  Something is about to happen.");
			break;
		case 7: 
			if(secondWord.equals("stein")){
				setOutput("\n\nAs you try to grab one of the goblins' stein.  You accidently drop"+
				" it.  The goblin you tried to steal it from gets very offended and takes out"+
				" a blade.  As he stabs it through your chest, you shout in agony.  The master"+
				" walks down from his throne and begins a chant.  Your head feels like it is"+
				" going to explode as you collapse over a table.");
				getDeath();
				return 3;
			}
			else{
				getDefault();
			}
			break;
		case 8: 
			if(secondWord.equals("shard") || secondWord.equals("buckler") || secondWord.equals("sword")){
				setOutput("\n\nYou don't want to drop your weapons, there may be more battles to come.");
			}
			else{
				getDefault();
			}
			break;
		case 10: setOutput("\n\nAs you try to attack "+secondWord+", your invisibility falls.  The"+
			" goblins around you pin your arms against the wall and start ripping your"+
			" body to pieces.");
			getDeath();
			return 3;
		case 11: setOutput("\n\nYou let out a high pitched scream.  All of the goblins begin to"+
			" look around to see where this aweful noise came from.  They are all drunk and"+
			" forget quickly what just happened.  The master begins talking and the goblins"+
			" begin to chant.");
			break;
		}
		return 1;
	}
	/**
	 * State 1 you are uninvis, all but attack master to 2 or grab master to 3 = dead
	 */
	private int oneActions(){
		if((word >= 1 && word <= 4)){
			word = 1;
		}
		switch(word){
		case 1: setOutput("\n\nThe master goblin blocks your way as you try to run.");
			break;
		case 5: setOutput("\n\nYou look around with confusion, but it's easy... you're going to die.");
			break;
		case 6: setOutput("\n\nYou listen for a sign of hope.");
			break;
		case 7: 
			if(secondWord.equals("master")){
				setOutput("\n\nYou jump over the master quicker than you knew you could and"+
				" grab him around the chest with one arm.  With the other, you drop your weapon"+
				" and clamp your hand around the master's throat.  You could easily squeeze and"+
				" kill him if you wanted, but then there's still the other thousands of goblins"+
				" to worry about.  The horde of goblins is now rushing at you to save their master.");
				state = 3;
				return 1;
			}
			else{
				setOutput("\n\nYou try to grasp a wisp of hope.");
			}
			break;
		case 8: setOutput("\n\nYou try to drop your "+secondWord+", but it doesn't help.");
			break;
		case 9: setOutput("\n\nYour attempt to hide fails as every goblin in the Great Hall"+
				" stares at you.");
			break;
		case 10: 
			if(secondWord.equals("master")){
				setOutput("\n\nYou realize that your only hope is to take out the whole clan of"+
			" goblins.  You thrust your weapon towards the master goblin's throat, but he"+
			" dodges.\nThe master strikes back at you with his stein in hand.  As it strikes"+
			" your shoulder, the stein shatters and you feel no pain.  Over a thousand goblins"+
			" jump over their tables and begin to rush towards you.  The outcome looks grim.");
			state = 2;
			return 1;
			}
			else{
				setOutput("\n\nYou try to attack "+secondWord+", but fail.");
			}
			break;
		case 11: setOutput("\n\nThe master goblin gives you a rude stare as you scream in his face!");
			break;
		case 12: setOutput("\n\nYou nod your head in agreement to this hopeless situation.");
			break;
		case 13: setOutput("\n\nYou shake your head in disagreement to this situation");
			break;
		case 14: setOutput("\n\nYou quickly rummage through your inventory and find nothing useful.");
			break;
		}
		appendOutput("  The goblins rush over their tables towards you as you enter a fighting"+
		" stance.  They are only armed with steins.  You are able to fend off a few of them,"+
		" but numbers always beat advanced technology in a brawl.  You are quickly torn apart"+
		" and eaten by these hungry goblins.");
		getDeath();
		return 3;
	}
	/**
	 * You attack the master, but failed to kill
	 * After attacking a couple times, getting saved by the wizard you goto state 4
	 * You die if you do anything but attack
	 */
	public int twoActions(){
		if((word >= 1 && word <= 4)){
			word = 1;
		}
		switch(word){
		case 1: setOutput("\n\nThe master goblin blocks your way as you try to run.");
			break;
		case 5: setOutput("\n\nYou look around with confusion, but it's easy... you're going to die.");
			break;
		case 6: setOutput("\n\nYou listen for a sign of hope.");
			break;
		case 7: 
			setOutput("\n\nYou try to grasp a wisp of hope.");
			break;
		case 8: setOutput("\n\nYou try to drop your "+secondWord+", but it doesn't help.");
			break;
		case 9: setOutput("\n\nYour attempt to hide fails as every goblin in the Great Hall"+
				" stares at you.");
			break;
		case 10: 
			if(secondWord.equals("master") && firstAttack){
				setOutput("\n\nYou thrust your weapon again towards the large goblin and you strike"+
			" him in the leg, rendering his left leg useless.  You glance behind you at the"+
			" impending doom of the clan of goblins running in your direction, but suddenly"+
			" in the back of the Great Hall there is a blinding light that you have only seen"+
			" once before.  The old man appears and yells to you, \' Run my Prince!  Get the"+
			" King his potion! \'  The horde of red goblins turn around and all charge towards"+
			" the old man.\nAs you hear the old man's last word you feel a sharp pain in your"+
			" back.  The master goblin must have pulled a dagger out of his pocket because"+
			" there is now one stuck in your back.  You are bleeding.");
				firstAttack = false;
				return 1;
			}
			else if(secondWord.equals("master")){
				setOutput("\n\nYou attack the master goblin once more and this time your aim is"+
			" true.  You strike him in the eye with your weapon.  As he falls to the"+
			" ground, a small vial like one you saw in the old man's room falls out of the"+
			" master's pocket.\nBehind you, there is a large rush of wind and fog"+
			" that completely covers the room.  You can hear the anguished screams of goblins,"+
			" but you can't see them or the old man.  The cries of the goblins die out and"+
			" the fog lifts.  In front of you lies a motionless Great Hall aside from the"+
			" occasional stein spinning on it's side.  All of the goblins are gone and so"+
			" is the old man.  You say a short prayer on his behalf.");
				state = 4;
				return 1;
			}
			else{
				setOutput("\n\nYou try to attack "+secondWord+", but fail.");
			}
			break;
		case 11: setOutput("\n\nThe master goblin gives you a rude stare as you scream in his face!");
			break;
		case 12: setOutput("\n\nYou nod your head in agreement to this hopeless situation.");
			break;
		case 13: setOutput("\n\nYou shake your head in disagreement to this situation");
			break;
		case 14: setOutput("\n\nYou quickly rummage through your inventory and find nothing useful.");
			break;
		}
		appendOutput("  The goblins rush over their tables towards you as you enter a fighting"+
		" stance.  They are only armed with steins.  You are able to fend off a few of them,"+
		" but numbers always beat advanced technology in a brawl.  You are quickly torn apart"+
		" and eaten by these hungry goblins.");
		getDeath();
		return 3;
	}
	/**
	 * Have goblin master by the throat and you can leave after get vial
	 */
	public int threeActions(){
		
	  if(word == 1 && plyr.getItem(6)){//win game
			setOutput("\n\nYou release the master goblin and start to run out the exit to the"+
			" north.  Just before you make it, you feel a sharp pain in your back.  The master"+
			" has thrown a knife in your back and you are bleeding, but you manage to escape."+
			"  You walk slowly and wounded with the dagger still in your back towards the castle"+
			" in the horizon.  As the castle comes closer and looms over you,"+
			" you become more and more faint and find it harder to walk.  As you reach the gate"+
			" to your grandiose home, the guards see you and help you up to the King's Throne"+
			" Room.  You can smell your own blood as you tell the King, \' ...Father... I have"+
			" ...returned... with ...the... vial. \'  A grin spreads over the King's face when"+
			" he hears that final word escape your mouth.  The King takes the vial from your"+
			" hand and you discover that it isn't your own blood that you smell, but the same"+
			" rotting bloody smell you experienced in the goblin cave.  Your eyes begin to get"+
			" heavy and you feel weak as the king pours the contents of the vial over the slit"+
			" underside of the sheep that lay on his feasting table.  The sheep begins to shrink"+
			" rapidly until nothing is left but a small brown square.  With your last breath"+
			" you whisper, \' Now you may truly experience life my father.... \'  With this, the"+
			" King places the small square onto his tongue and closes his mouth around it."+
			"  A grin spreads across the King's face as he experiences, for the first time, the"+
			" taste of chocolate.");
			getWin();
			leftRoom = false;
			return 3;
	  }
	  else{//begin else
		if((word >= 1 && word <= 4) || (word >= 8 && word <= 13)){
			word = 1;
		}
		switch(word){
		case 1: setOutput("\n\nYou're in a tense situation here, you are fending off thousands"+
			" of goblins with the goblin master as your hostage.");
			break;
		case 5:	setOutput("\n\nYou still have your hand around the master goblin's throat.  You"+
			" are fending off thousands of goblins by holding him hostage.");
			if(!plyr.getItem(6)){
				appendOutput("  After glaring at the goblins around you, you notice a small"+
				" vial of liquid on the table in front of the master.");
			}
			break;
		case 6: setOutput("\n\nYou hear the master goblin's breathing quicken as you squeeze"+
				" tighter around his neck.");
			break;
		case 7: 
			if(!plyr.getItem(6) && (secondWord.equals("vial") || secondWord.equals("potion"))){
				setOutput("\n\nYou grab the vial of liquid.  You can now escape and return the"+
				" vial to your father!");
				plyr.flagItem(true, 6);
			}
			else{
				setOutput("\n\nYou're in a tense situation here, you are fending off thousands"+
			" of goblins with the goblin master as your hostage.");
			}
			break;
		}//end switch
	  }//end else
	  return 1;
	}
	/**
	 * everybody dead, vial on floor, n to win
	 */
	public int fourActions(){
	  if(word == 1 && plyr.getItem(6)){//win game
			setOutput("\n\nYou leave the dungeon behind, wounded with the dagger still in your"+
			" back, walking slowly towards the castle"+
			" in the horizon.  As the castle comes closer and looms over you,"+
			" you become more and more faint and find it harder to walk.  As you reach the gate"+
			" to your grandiose home the guards see you and help you up to the King's Throne"+
			" Room.  You can smell your own blood as you tell the King, \' ...Father... I have"+
			" ...returned... with ...the... vial. \'  A grin spreads over the King's face when"+
			" he hears that final word escape your mouth.  The King takes the vial from your"+
			" hand and you discover that it isn't your own blood that you smell, but the same"+
			" rotting bloody smell you experienced in the goblin cave.  Your eyes begin to get"+
			" heavy and you feel weak as the king pours the contents of the vial over the slit"+
			" underside of the sheep that lay on his feasting table.  The sheep begins to shrink"+
			" rapidly until nothing is left but a small brown square.  With your last breath"+
			" you whisper, \' Now you may truly experience life my father.... \'  With this, the"+
			" King places the small square onto his tongue and closes his mouth around it."+
			"  A grin spreads across the King's face as he experiences, for the first time, the"+
			" taste of chocolate.");
			getWin();
			return 3;
	  }
	  else{//begin else
		switch(word){
		case 1: setOutput("\n\nYour father would be very disappointed if you left without the vial.");
			break;
		case 2: setOutput("\n\nIt's a shame that you didn't try to escape while you could.  As"+
			" you start slowly walking south with your injured back, you trip and fall down"+
			" the steps.  You have lost so much blood and pass out.");
			getDeath();
			return 3;
		case 5:	setOutput("\n\nYou look down at the master goblin's bloody corpse.  The Great"+
			" Hall is now completely empty except for you and the master goblin's corpse.");
			if(!plyr.getItem(6)){
				appendOutput("  After looking around the room, you see the"+
				" vial of liquid on the ground where the master goblin dropped it.");
			}
			break;
		case 6: setOutput("\n\nAn awkward silence has fallen over the Great Hall.  You only"+
			" hear your painful breathing as you struggle to stay alive.");
			break;
		case 7: 
			if(!plyr.getItem(6) && (secondWord.equals("vial") || secondWord.equals("potion"))){
				setOutput("\n\nYou grab the vial of liquid.  You can now escape and return the"+
				" vial to your father!");
				plyr.flagItem(true, 6);
			}
			else{
				setOutput("\n\nYou don't have the strength to pick that up.  Your life is"+
				" slipping away");
			}
			break;
		case 8: setOutput("\n\nYou don't have the strength to drop anything now.  Your life is slowly"+
				" slipping away.");
			break;
		}//end switch
	  }//end else
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
		Room10 room = new Room10(plyr, ogre, goblin, master);
		
		//Check zero state!
		
		//zero state look around room
		String s = "look nothing";
		String[] input = s.split("\\s");
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
		
		//zero state w, 
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state listen 
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should be default 
		s = "grab ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab should be death
		s = "grab stein nothing";
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
		s = "attack goblin nothing";
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

		//zero state n, should be can't go that way
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());

		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
// ****************  STATE ONE ********************************* //
		//do one at a time
//		first state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state n,
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state s, 
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state e, 
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state w, 
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
		s = "grab stein nothing";
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
		s = "attack nothing";
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
		
// *************  Transition into State 3 or 2 ******************* //
		
		//first state grab should goto state 3
		s = "grab master nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		first state attack should be goto state 2
		s = "attack master nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());

		
// *************** State three or two**************************  //
		
//		first state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state n,
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state s, 
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state e, 
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state w, 
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
		s = "grab stein nothing";
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
		
		//transition into state 4
//		first state attack shouldn't work
		s = "attack master nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack master nothing";
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

// ****************** Transition to win ******************************* //
		
//		first state grab coin should be default error
		s = "grab vial nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state n,
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state n,
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());

		
// ***************** State Four ********************************************* //
//		first state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state n,
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state s, 
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state e, 
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state w, 
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
		s = "grab stein nothing";
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
		s = "attack nothing";
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
		
		//Transition to win
//		first state grab coin should be default error
		s = "grab vial nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
//		first state inventory check, should be empty
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
//		first state inventory check, should be empty
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
*/
	}

}
