
public class Room4 extends Rooms {
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
	public Room4(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		this.ogre = getOgre();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		
	}
	

	/**
	 * State 0 is initially without doing anything, if you yell or grab mirror then to 1
	 * State 1 is when ogre is in room with you
	 * 			attack w/ mirror or grab mirror if you don't have mirror = dead ogre and state 2
	 * State 2 if you have mirror and drop it before ogre dead, it breaks and no way to win
	 * State 3 ogre is dead!
	 */
	public int runFSM() {
		if(ogre.getState() == 3){ // ogre dead
			playerStatus = 1;
			state = 3;
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 3 reached but not called from room 4");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 2, but not called properly");
			System.exit(1);
		}
		
		//System.out.println("DEBUG- state = "+state);
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		//case 2: playerStatus = state2(); return playerStatus;
		case 3: playerStatus = state3(); return playerStatus;
		}
		return 1;

	}
	
	/**
	 * State 0: normal
	 */
	public int state0(){
		if ((word >= 2 && word <= 4) || (word >= 9 && word <= 10) || (word >= 12 && word <= 14)){
			this.getDefault();
		}
		else{
			//System.out.println("Calling zeroActions() in Room2");
			return this.zeroActions();
		}
		return 1;
	}
	/**
	 * state 1: aggro from Ogre and die unless grab mirror if don't have or attack
	 */
	public int state1(){
		//System.out.println("DEBUG- reached state1()");
		return oneActions();
	}
	/*public int state2(){  
		return twoActions();
	}*/
	public int state3(){ //ogre = dead
		if((word >= 2 && word <= 4) || ((word >= 9 && word <=14))){
			getDefault();
			/*if(plyr.getState() == 1){
				appendOutput("\nYou are bleeding.");
			}*/
		}
		else{
			return threeActions();
		}
		return 1;
	}
	
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * Nothing happened yet, change to state 1 if yell or grab mirror
	 */
	private int zeroActions(){
		switch(word){
		
		case 1: setOutput("\n\nYour brain is screaming \'DANGER!\' but your body isn't"+
				" listening as your legs take you back towards the ogre.  He wraps his"+
				" extraordinarily large hands around"+
				" your chest and begins to squeeze in anger.  You catch your last short breath"+
				" as the life is pressed out of you.");
				getDeath();
				return 3;  //you die
				
		case 5:	
			if(!plyr.getItem(2)){
				setOutput("\n\nYou stare at the half of the human at the end of the room."+
				"  You are enthralled by the similarity of his physical characteristics to your"+
				" own.  Just behind this upper torso of a human, you see an ogre through an opening"+
				" scratching his head!  You realize that you are not looking at a person and"+
				" an ogre, but you are looking into a mirror.");
			}
			else{
				setOutput("\n\nYou are in a dimly lit room, you can barely see anything around"+
				" you.  Outside to the north, the ogre stands guard waiting for any sign of"+
				" his escaped prisoner.");
			}
				return 1;
				
		case 6: setOutput("\n\nYou hear growling from the ogre to the north.  He is obviously"+
				" frustrated by your disappearance.");
				return 1;
		
		case 7: 
			if(secondWord.equals("mirror")){
				setOutput("\n\nAs you struggle to lift the mirror off the wall, you pull a"+
				" little bit too hard and the mirrors bounces off your head with a hollow thump."+
				"  The noise from your obviously empty head gets the ogre's attention and he"+
				" rushes into the room with you.  \'Me not fail the master!\' he yells."+
				"  The ogre walks toward you blindly swinging his fists in the air in an attempt"+
				" to strike you down.  He misses.");
				state = 1;
				plyr.flagItem(true, 2);
			}
			else{
				getDefault();
			}
			break;
		case 8:
			getDefault();
			break;
		case 11:
			setOutput("\n\nThe ogre guard hears a high pitched scream come from your mouth"+
			" and he rushes into the room with you.  \'Me not fail the master!\' he yells."+
			"  The ogre walks toward you blindly swinging his fists in the air in an attempt"+
			" to strike you down.  He misses.");
			state = 1;
			ogre.changeState(1); //Ogre is now in chase mode
			return 1;
			
		}
		return 1;
	}
	/**
	 * State one's actions
	 * Only way to stay alive is to yell, grab mirror or attack
	 */
	private int oneActions(){
		//System.out.println("DEBUG- reached oneActions()");
		//System.out.println("DEBUG- word: "+word);
		if(word >= 2 && word <= 4){
			word = 2;
		}
		//System.out.println("DEBUG- word: "+word);
		switch(word){
		case 1: setOutput("\n\nThe ogre blocks your path to the north.");
			break;
		case 2: getDefault();
			break;
		case 5: setOutput("\n\nYou are too scared to look around, you cover your eyes.");
			break;
		case 6: setOutput("\n\nYou strain your ears to listen for a sign of hope.");
			break;
		case 7: 
			if(!plyr.getItem(2) && secondWord.equals("mirror")){
				setOutput("\n\nYou struggle to take the huge mirror off of the wall.  You pull"+
				" a little bit too hard, but you duck as the mirror comes flying off the wall."+
				"  Unfortunately for the ogre, he is directly in the path of the heavy"+
				" projectile and is struck in the head.  The mirror shatters and as the ogre"+
				" falls to the ground, a large shard of glass peirces through his skull.  The"+
				" ogre lets out a painful groan as he dies on the floor.");
				ogre.changeState(3);
				state = 3;
				plyr.flagItem(false, 2);
				return 1;
			}
			else{
				setOutput("You cannot grab that!");
				whichDeath();
				return 3;
			}
		case 8: setOutput("\n\nDropping your "+secondWord+" does nothing to help.");
			break;
			
		case 9: setOutput("\n\nThe ogre is looking straight at you, you can't hide!");
			break;
			
		case 10: 
			if(plyr.getItem(2) && secondWord.equals("ogre")){
				setOutput("\n\nYou swing the mirror at the ogre with a fit of rage.  The mirror"+
			" shatters as it strikes the ogre's head.  As he falls to the ground, a long shard"+
			" of glass pierces through his head.  The ogre lets out a groan as he dies on the"+
			" floor.");
				ogre.changeState(3);
				state = 3;
				plyr.flagItem(false, 2);
				return 1;
			}
			else{
				setOutput("\n\nAs you try to attack something other than the ogre, you turn"+
			" your frustration to him.  Your fists beat wildly on the ogres chest, but have no effect."+
			"  The ogre reaches over you to pick up the mirror.  He slams it over your head"+
			" and you see your own frightened face as you are brutally murdered.");
				getDeath();
				return 3;
			}
		case 11:
			//System.out.println("DEBUG- reached case 11 of oneActions");
			setOutput("\n\nThe ogre guard covers his ears as a high pitched scream comes from your mouth."+
			" He is temporarily stunned giving you an opportunity to escape.");
			return 1;
		
		case 12:
			setOutput("\n\nThe ogre takes your nod as a threatening gesture.");
			break;
		case 13:
			setOutput("\n\nThe ogre takes your head shaking as a threatening gesture.");
			break;
		case 14:
			setOutput("\n\nAs you try to check your inventory, the ogre acts faster than you can.");
			break;
		}
		if((word >=1 && word <=6) || (word >= 8 && word <= 9) || (word >= 12 && word <= 14)){
			whichDeath();
			return 3;
		}
		return 1;
	}
	/**
	 * The actions to happen have you dropped the mirror
	 */
/*	public int twoActions(){
		if(word >= 2 || word <= 4){
			word = 2;
		}
		switch(word){
		case 1: setOutput("\n\nThe ogre blocks your path to the north.");
			break;
		case 2: getDefault();
			break;
		case 5: setOutput("\n\nYou are too scared to look around, you cover your eyes.");
			break;
		case 6: setOutput("\n\nYou strain your ears to listen for a sign of hope.");
			break;
		case 7: setOutput("You cannot grab that!");
			break;
			
		case 8: setOutput("\n\nDropping your "+secondWord+" does nothing to help.");
			break;
			
		case 9: setOutput("\n\nThe pieces of broken mirror reflect your image around the room,"+
			" you are no longer hidden.");
			break;
			
		case 10: getDefault();
			break;
			
		case 11:
			setOutput("\n\nThe ogre guard hears the high pitched scream come from your mouth."+
			"  He walks into the room and eats you.  You die a slow, agonizing death as you are"+
			" digested in the ogre's stomach.");
			getDeath();
			return 3;
		
		case 12:
			getDefault();
			break;
		case 13:
			getDefault();
			break;
		case 14:
			getDefault();
			break;
		}
		if(word != 11){
			appendOutput("  The ogre has a brilliant idea and walks into the room with you."+
			" He sees you.");
			whichDeath();
			return 3;
		}
		return 1;
	}*/
	/**
	 * The actions to happen when the ogre is dead
	 */
	public int threeActions(){
		switch(word){
		case 1: setOutput("\n\nYou go back into the hallway to the north.");
			return 2;
		case 5: 
			if(!plyr.getItem(3)){ //doesn't have shard yet
				setOutput("\n\nYou see the ogre laying on the ground with long, jagged shard of"+
				" glass through his head.  The light from the hallway to the north shines into"+
				" the dim room.");
			}
			else{
				setOutput("\n\nThe light from the hallway to the north shines into"+
				" the dim room.");
			}
			break;
		case 6:
			setOutput("\n\nYou hear a soft scratching noise each time the ogre's leg involuntarily"+
			" twitches from death.");
			break;
		case 7:
			if(!plyr.getItem(3) && (secondWord.equals("shard") || secondWord.equals("glass"))){
				setOutput("\n\nYou pull the long, jagged shard out of the ogre's head, but it cuts"+
				" you in the process.  You are bleeding.");
				plyr.flagItem(true, 3);
				plyr.changeState(1);
			}
			else{
				getDefault();
			}
			break;
		case 8:
			if(plyr.getItem(3) && (secondWord.equals("shard") || secondWord.equals("glass"))){
				setOutput("\n\nYou drop the shard and it falls back into the ogre's head.  You"+
				" are no longer bleeding.");
				plyr.flagItem(false, 3);
				plyr.changeState(0);
			}
			else{
				getDefault();
			}
			break;
		}
		/*if (plyr.getState() == 1){
			appendOutput("\nYou are bleeding.");
		}*/
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
	 * Used for testing
	 * @param args not used
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room4 room = new Room4(plyr, ogre, goblin, master);
		//ogre.changeState(3);
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
		
		//zero state n, should be death
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
		
//		zero state attack shouldn't work
		s = "attack ogre nothing";
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
		

//		zero state yell should be some crazy thing
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
		//zero state grab should be default error
		s = "grab mirror nothing";
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
		

			
		//one state drop coin
		s = "drop mirror nothing";
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


// ***************   Transition to state 3   ********************************		
		//one state grab
		s = "grab mirror nothing";
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
		
// ******************   State 3 - ogre dead ****************************** //
		

		
//		three state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state n
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state s
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state e
		s = "e nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state w
		s = "w nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state 
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state grab
		s = "grab shard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//three state drop coin
		s = "drop shard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state attack 
		s = "attack jail nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		three state attack 
		s = "attack ogre nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state yell 
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		three state standard agree
		s = "agree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		three state standard disagree
		s = "disagree nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		three state inventory check, should be empty
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
*/

	}

}
