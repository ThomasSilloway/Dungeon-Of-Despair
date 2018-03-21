
public class Room6 extends Rooms {
	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private Monster goblin;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	private String weapon;
	private int attackState;
	private boolean stunned; //true if goblin is stunned
	private boolean dead;
	private boolean wasInRoom;
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room6(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 1;
		this.plyr = getPlayer();
		this.goblin = getGoblin();
		weapon = "shard of glass";
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		attackState = 1;
		dead = false;
		wasInRoom = false;
	}
	

	/**
	 * State 0 is in combat with a sword or dagger
	 * State 1 is in combat with two shields
	 * State 2 is when goblin is either stunned or dead
	 */
	public int runFSM() {
		if(wasInRoom){
			playerStatus = 1;
			state = 0;
			if(goblin.getState() == 2)
				state = 1;
		}
		if((plyr.getItem(4) || plyr.getItem(3)) || 
		(!plyr.getItem(4) && !plyr.getItem(3) && !plyr.getItem(5))){//has no weapon, shard or sword
			state = 0;
		}
		//System.out.println("DEBUG - State: "+state+"\nDEBUG- have shield: "+plyr.getItem(5));
		
		if(plyr.getItem(4)){
			weapon = "short sword";
		}
		if(playerStatus == 2){
			System.out.println("ERROR:: Room 7 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 4){
			System.out.println("ERROR:: Room 5 reached but not called");
			System.exit(1);
		}
		if(playerStatus == 3){
			System.out.println("ERROR:: Death reached in room 2, but not called properly");
			System.exit(1);
		}
		if(goblin.getState() == 3){ // goblin dead
			playerStatus = 1;
			state = 2;
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
	 * State 0: combat with dagger or sword
	 */
	public int state0(){
		if (word != 10 || !secondWord.equals("goblin")){
			setOutput("\n\nYou're in combat, you really should try attacking the goblin!");
		}
		else{
			int i = this.zeroActions();
			//System.out.println("State0's return number: "+i);
			return i;
		}
		return 1;
	}
	/**
	 * state 1: aggro from Ogre and die unless move east or yell = stun
	 */
	public int state1(){
		if (word != 10 || !secondWord.equals("goblin")){
			setOutput("\n\nYou're in combat, you really should try attacking the goblin!");
		}
		else{
			return this.oneActions();
		}
		return 1;
	}
	public int state2(){  //goblin is dead
		int ran1 = (int)(Math.random()*100);
		//System.out.println("DEBUG- value of ran1 in twoActions for regain consciousness: "+ran1);
		if(goblin.getState() == 2 && ran1 <= 40){//20% chance for goblin to regain consciousness
			setOutput("\n\nBefore you have time to perform that action, the goblin rises from"+
			" his feet and strikes at you with little effort.  You easily block his weak punch.");
			state = 1;
			return 1;
		}
		//System.out.println("DEBUG- word number: "+word);
		if((word == 3 || word == 4) || word == 7 || ((word >= 9 && word <=10)) ||
			(word >= 12 && word <= 14)){
			this.getDefault();
		}
		else{
			//System.out.println("DEBUG- calling two actions from state2()");
			return twoActions();
		}
		return 1;
	}
	
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State zero's actions
	 * Combat with a sword or shard based on attack state
	 * max length of attack is 2 rounds, after that you die or goblin dies
	 * No weapon = instant death
	 */
	private int zeroActions(){
		if(!plyr.getItem(3) && !plyr.getItem(4) && !plyr.getItem(5)){
			setOutput("\n\nYou can't attack without a weapon!  The goblin strikes at you"+
			" again with his cloth covered fist."+
			"  You are unlucky as the goblin strikes at you.  His fist penetrates"+
			" your chest and turns your heart into mush.  As you look down to see the"+
			" goblins fist in your chest, your life fades away.");
			getDeath();
			return 3;
		}
		if(attackState >= 2){
			attackState = 2;
		}
		int i = 1; //return state
		int ran1 = (int)(Math.random()*100);
		int ran2 = (int)(Math.random()*100);
		//System.out.println("DEBUG- value of ran1: "+ran1+"\nDEBUG- value of ran2: "+ran2);
		//System.out.println("DEBUG- value of attackState: "+attackState);
		switch(attackState){
		//first round of attack, goblin 20% chance to be off balance
		//if he is offbalance, cut off head
		//if you are bleeding, 50% chance to kill, 50% chance to die from weapon slipping out of hands
		//if not bleeding, 70% chance to kill, 30% chance miss
		//if goblin isn't dead yet, he stabs you in the leg so you bleed
		case 1: 
			if(ran1 <= 20){
				setOutput("\n\nYou notice that the goblin is off balance from his last attack."+
				"  You take advantage of the situation and easily slit the goblin's throat"+
				" with the end of your "+weapon+".  The goblin grasps his gushing throat as"+
				" he falls to the ground and dies.");
				goblin.changeState(3);
				state = 2;
			}
			else if(plyr.getState() == 1){ //player is bleeding
				i = bleedingAtk();
			}
			else{//player not bleeding
				if(ran2 <= 70){//attack succeed
					setOutput("\n\nYou attack the goblin with your "+weapon+".  You successfully"+
					" stab the goblin through the heart with your "+weapon+".  Grasping his"+
					" chest, the goblin falls to the ground and squirms in agony as he dies.");
					goblin.changeState(3);
					state = 2;
				}
				else{
					setOutput("\n\nYou attack the goblin with your "+weapon+".  Your attack misses.");
				}
			}
			if(goblin.getState() != 3  && !dead){
				appendOutput("  The goblin strikes at you again with his cloth covered fist."+
				"  This time you aren't so lucky as you as he performs a devestating blow on"+
				" your right leg.  You begin to bleed heavily and you struggle to maintain"+
				" consciousness.");
				plyr.changeState(1);
			}
			attackState = 2;
			break;
		
		//second round of attack, you are bleeding
		//you are bleeding, 50% chance to kill, 50% chance to die from weapon slipping out of hands
		//if goblin not dead, you die
		case 2:	
			i = bleedingAtk();	
			break;
		}
		//System.out.println("state: 1 for stay in room, 3 for death: "+i);
		attackState++;
		return i;
	}
	/**
	 * Method for character bleeding 50% chance to die
	 */
	public int bleedingAtk(){
		int ran1 = (int)(Math.random()*100);
		//System.out.println("DEBUG- value of bleedingRan: "+ran1);
		if(ran1 <= 50){//attack succeed
			setOutput("\n\nYou attack the goblin with your "+weapon+".  You successfully stab the"+
			" goblin through the heart with your "+weapon+".  Grasping his chest, the goblin"+
			" falls to the ground and squirms in agony as he dies.");
			goblin.changeState(3);
			state = 2;
			return 1;
		}
		else{ //attack fail and you die from slippery hands
			setOutput("\n\nBlood from your wound has gotten on your hands.  During your attack,"+
			" your "+weapon+" slips out of your hand, straight into the air!  You look up to"+
			" see where it went as it lands straight into your eye and pierces through your"+
			" head.");
			getDeath();
			dead = true;
			return 3;
		}
	}
	/**
	 * State one's actions
	 * You have two shields.
	 * You bash the goblin with a shield and he's knocked out, goto state 2.
	 */
	private int oneActions(){
		setOutput("\n\nYou bash the goblin over the head with your shield!  He falls to the ground,"+
		" unconscious.  You'd better hurry, or he may wake up soon!");
		goblin.changeState(2);
		stunned = true;
		state = 2;
		return 1;
	}
	/**
	 * The actions to happen when goblin is dead
	 */
	public int twoActions(){
		//System.out.println("DEBUG- word number: "+word);
		switch(word){
		case 1: setOutput("\n\nAs you pass through the gate, it slams shut behind you.  You try"+
		" to open it again, but it appears to be magically locked.  To the east through a short"+
		" hallway, you hear the loud cheering and yelling of thousands of goblins as well as the"+
		" clanging of steins that sound mostly empty.  To the west"+
		" is a large wooden door with an unknown symbol drawn on it.  To the north is another"+
		" large wooden door with several different unknown symbols on it.  You can't read the"+
		" language, but you think you have seen symbols above the northern door before.");
				return 2;
		case 2: setOutput("\n\nYou move back into the fog to the south.");
				wasInRoom = true;
				return 4;
		case 5: 
			setOutput("\n\nThere is an open gate in front of you.  It must have been"+
					" used to block off the jail area from a yet unknown area of the dungeon.  To the"+
					" north through the gate, you see a room that has two doors in it.  To the"+
					" south is the foggy room that you left behind."); 
			if(!stunned){//goblin dead 
				appendOutput("  Your feet are covered in the blood of your fallen enemy.");
			}
			else{
				appendOutput("  The goblin lies unconscious at your feet, you should move quickly.");
			}
				break;
		case 6: setOutput("\n\nYou hear faint cheering in the distance.");
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
	 * @param args
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		plyr.flagItem(true, 5); //change these values for combat checks
		//plyr.flagItem(true, 5);
		//plyr.flagItem(true, 3);
		//plyr.changeState(1);
		plyr.flagItem(true, 4);
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room6 room = new Room6(plyr, ogre, goblin, master);
		//ogre.changeState(3);
		//System.out.println("Number should be 0: "+ogre.getState());
		
		//Check zero state!
	
//		zero state look around room
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
		
		//zero state drop coin shouldn't work
		s = "drop sword nothing";
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
				
		//**** Check combat **** / and transition into state 1

		System.out.println("\ntest combat");
		
		// Player has sword only
		//plyr.flagItem(true,4);
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
		// Player has shard only
		 
		//plyr.flagItem(true,3);
		//plyr.changeState(1);
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		// Player has no weapon
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		// Player has sword and shield
		//plyr.flagItem(true,4);
		//plyr.flagItem(true,5);
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//Player has shield only
		//plyr.flagItem(true, 5);
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		// Player has shard and shield
		//plyr.flagItem(true, 3);
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();	System.out.println(room.getOutput());
		// Player has two shields
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		

// ************   STATE 1  ************************************ //
//		zero state look around room
		s = "look nothing";
		input = s.split("\\s");
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
		
		//zero state grab should be default error
		s = "grab ogre nothing";
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
		
		//zero state drop coin shouldn't work
		s = "drop sword nothing";
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
		
		//zero state drop coin shouldn't work
		s = "drop goober nothing";
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
*/
	}

}
