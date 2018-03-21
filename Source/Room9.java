
public class Room9 extends Rooms {

	private String secondWord;
	private int word; //the number used for FSM
	private Player plyr;
	private int state; //state of the room that you're in for the FSM
	private int playerStatus;
	private boolean isWizard; //true if you see wizard, false if you see goblin
	private boolean leftRoom; //true if you have left the room, false otherwise
	
	/**
	 * constructor initializes all variable
	 * @param plyr the character used in this game
	 */
	public Room9(Player plyr, Monster ogre, Monster goblin, Monster master){
		super(plyr, ogre, goblin, master);
		state = 0;
		this.plyr = getPlayer();
		playerStatus = 1; //1 if in room, 2 if next room, 3 if dead
		isWizard = false;
		leftRoom = false;
		
	}
	

	/**
	 * State 0 is initially without doing anything, goblin/wizard there 4 different looks
	 * 			before he catches up to you, attack goblin to state 1
	 * 			attack wizard = die
	 * State 1 you are talking to wizard, agree to goto state 2 or disagree and goto state3
	 * State 2 you agreed with the first statement agree/disagree to state 0
	 * State 3 you disagreed with first statement, agree/disagree to state 0
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
			System.out.println("ERROR:: Death reached in room 9, but not called properly");
			System.exit(1);
		}
		
		word = getWord();
		secondWord = getSecondWord();
		switch(state){
		case 0: playerStatus = state0(); return playerStatus; 
		case 1: playerStatus = state1(); return playerStatus; 
		case 2: playerStatus = state2(); return playerStatus;
		case 3: playerStatus = state3(); return playerStatus;
		}
		return 1;

	}
	
	/**
	 * State 0: atk wiz = death, attack gob to goto state 1 or 2
	 */
	public int state0(){
		if ((word >= 1 && word <=2) || word == 4 || word == 7 ||
			word == 9 || word == 11 || (word >= 12 && word <= 14)){
			this.getDefault();
		}
		else{
			return this.zeroActions();
		}
		return 1;
	}
	/**
	 * All you can do is agree or disagree, otherwise should say you are speaking
	 * @return
	 */
	public int state1(){
		oneActions();
		return 1;
	}
	/**
	 * state 2: you agreed with the first statement agree/disagree to state 0
	 */
	public int state2(){
		twoActions();
		return 1;
	}
	/**
	 * State 3: you disagreed with first statement, agree/disagree to state 0
	 */
	public int state3(){  // ogre death state
		threeActions();
		return 1;
	}
	
	/***********   PRIVATE METHODS FOR RESPONSES TO BE DISPLAYED ********************/
	
	

	
	/**
	 * State 0: atk wiz = death, attack gob to goto state 1 or 2
	 * Look can be for wiz or gobo and for invis or not
	 */
	private int zeroActions(){
		switch(word){
		
		case 3: setOutput("\n\nYou move back through the door to the east.  You see the door"+
				" to the north with familiar writing over it and the short hallway to the east"+
				" that leads to the goblins.");
				leftRoom = true;
				return 2;  //move east
				
		case 5:	
			if(!isWizard){//see goblin over by pot
				setOutput("\n\nYou see the distorted figure of a goblin behind the steaming"+
				" pot in the middle of the room.  Out of the corner of your eye, you think you see an"+
				" organ in a jar twitch.");
			}
			else{//you see wizard
				setOutput("\n\nYou see the tall, old man behind the pot in the middle of the"+
				" room.  He says, \'Get a move on now, the King is waiting!\'");
			}
			if(plyr.getState() == 2){//player is invis
				appendOutput("  You look down at yourself and see"+
				" nothing but the muddy floor underneath you, you're invisible!");
			}
			break;
				
		case 6: setOutput("\n\nYou hear the soft sound of water boiling coming from the pot"+
				" and a distant cheer from the goblins.");
			break;
		case 8: 
			if(secondWord.equals("shard") || secondWord.equals("buckler") || secondWord.equals("sword")){
				setOutput("\n\nYou don't want to drop your weapons, there may be more battles to come.");
			}
			else{
				getDefault();
			}
			break;
		case 10: 
			if(isWizard && (secondWord.equals("wizard") || secondWord.equals("man"))){//you have talked to wizard
				setOutput("\n\nYour morals are questionable as you move in to attack a man"+
				" that has tried to help you.");
				if(plyr.getItem(5)){
					appendOutput("  You attempt to bash the old man with your shield, but he"+
					" blocks you with his staff.");
				}
				else if(plyr.getItem(4)){
					appendOutput("  You attempt to strike the old man down with your sword,"+
					" but he blocks you with his staff.");
				}
				else if(plyr.getItem(3)){
					appendOutput("  You attempt to slash the old man's throat with your shard"+
					" of glass, but he blocks you with his staff.");
				}
				else{
					appendOutput("  You foolishly try to attack the old man without a weapon,"+
					" but the wizard blocks you with his staff.");
				}
				appendOutput("  He says, \' My dear Prince, I have always been much more powerful than you,"+
				" why do you think you were stuck down here and I was not? \'\nThe old man begins to cast a"+
				" spell.\nYou are struck down by a great bolt of lightning.");
				getDeath();
				return 3;
			}
			else if(!isWizard && secondWord.equals("goblin")){
				setOutput("\n\nYou rush over to attack the wise looking goblin, but he yells"+
				" \'STOP!\'  There is a blinding light in front of you.  Slowly the light"+
				" fades and standing where the little red goblin was now stands an old man."+
				"  At almost a foot taller than you, his long, dark blue"+
				" robe majestically covers him as he wields a short staff in his hand.  The"+
				" old man says, \' It's you!  How did you escape?  Well... that's of no matter"+
				" now, those ogres are not the brightest creatures around.  Thank the gods"+
				" that you are alive and free now.  Do you know why you are here? \'  The old"+
				" man studies you awkwardly as you remain silent.  \' Do you even remember me? \'");
				state = 1;
			}
			else{
				getDefault();
			}
			break;
				
		}
		return 1;
	}
	/**
	 * You can only agree or disagree to what he says or else it says be polite!
	 * 
	 */
	private void oneActions(){
		if((word >= 1 && word <= 4) || (word == 9)){
			word = 1;
		}
		if((word >= 5 && word <= 8) || word == 10 || word == 14){
			word = 5;
		}
		switch(word){
		case 1: setOutput("\n\nYou're speaking to the old man, be polite and don't move around!");
			break;
		case 5: setOutput("\n\nYou're speaking to the old man, be polite and don't fidget!");
			break;
		case 11: setOutput("\n\nThe old man gives you a rude stare as you scream in his face!");
			break;
		case 12: setOutput("\n\nYou nod your head in agreement.\n\n"+
			"The old man says, \' That's fortunate, I wouldn't have had much"+
			" time to explain.  The Goblin Master's ritual will soon be over and the guards"+
			" will regain their post at your prison cell, but when they find you are missing"+
			" they will alert the whole place! \'\n\' Do you remember where to find the potion,"+
			" my Prince? \'");
			state = 2;
			break;
		case 13: setOutput("\n\nYou shake your head in disagreement.\n\n"+
			"The old man says, \' That is unfortunate, I don't have much time"+
			" to explain.  The Goblin Master's ritual will soon be over and the guards will"+
			" regain their post at your prison cell, but when they find you are missing they"+
			" will alert the whole place!  I am your servant, the wizard called Crimsonward."+
			"  I shall help you with whatever I can. \'\n\' Do you remember who you are? \'");
			state = 3;
			break;
		}
	}
	/**
	 * The actions to happen when you agree to first statement
	 */
	public void twoActions(){
		if((word >= 1 && word <= 4) || (word == 9)){
			word = 1;
		}
		if((word >= 5 && word <= 8) || word == 10 || word == 14){
			word = 5;
		}
		switch(word){
		case 1: setOutput("\n\nYou're speaking to the old man, be polite and don't move around!");
			break;
		case 5: setOutput("\n\nYou're speaking to the old man, be polite and don't fidget!");
			break;
		case 11: setOutput("\n\nThe old man gives you a rude stare as you scream in his face!");
			break;
		case 12: setOutput("\n\nYou nod your head in agreement.\n\n"+
			"The old man says, \' That is good to hear my Prince.  You should"+
			" know your true powers then, unless you were lying to me.  Retrieve the king's"+
			" potion and bring it back to me, you should easily be able to handle the power of"+
			" just a few thousand drunk goblins. \'");
			state = 0;
			isWizard = true;  //you just failed the talking part
			break;
		case 13: setOutput("\n\nYou shake your head in disagreement.\n\n"+
			"The old man says, \' To the east you probably heard the goblins"+
			" celebrating in a large hall.  They are all drunk and with my invisibility spell"+
			" you may be able to sneak by them, get the potion and leave the cave.  The exit"+
			" is just behind where their master sits on the north end of their Great Hall."+
			"  Remember, King Temilan cannot survive without the goblin's secret potion! \'"+
			"\nThe old man begins to cast a spell.\nYour skin tingles and you look down, but"+
			" only see the muddy floor where your body should be.  The old man's spell has"+
			" rendered you completely invisible.\n\' Good luck and may the gods be with you,"+
			" Prince Zhane. \'");
			state = 0;
			isWizard = true;
			plyr.changeState(2); //you are invisible
			break;
		}
	}
	/**
	 * The actions to happen when you disagree to first statement
	 */
	public void threeActions(){
		if((word >= 1 && word <= 4) || (word == 9)){
			word = 1;
		}
		if((word >= 5 && word <= 8) || word == 10 || word == 14){
			word = 5;
		}
		switch(word){
		case 1: setOutput("\n\nYou're speaking to the old man, be polite and don't move around!");
			break;
		case 5: setOutput("\n\nYou're speaking to the old man, be polite and don't fidget!");
			break;
		case 11: setOutput("\n\nThe old man gives you a rude stare as you scream in his face!");
			break;
		case 12: setOutput("\n\nYou nod your head in agreement.\n\n"+
			"The old man says, \' That is good to hear my Prince.  You should know your true"+
			" powers then, unless you were lying to me.  Retrieve the king's potion and bring"+
			" it back to me, you should easily be able to handle the power of just a few"+
			" thousand drunk goblins. \'");
			state = 0;
			isWizard = true;  //you just failed the talking part
			break;
		case 13: setOutput("\n\nYou shake your head in disagreement.\n\n"+
			"The old man says, \' You are the son of the King, yes, a prince.  King Temilan sent"+
			" you to spy on the goblins and steal their secret potion.  The King cannot live"+
			" without this potion.  When you didn't return after three days, I was sent here"+
			" in a goblin guise to make sure you remained safe and to spy on the goblin"+
			" clan to make a clear strategy for your escape, but it seems like you have that"+
			" covered so far.  To the east you probably heard the goblins celebrating in a"+
			" large hall.  They are all drunk and with my invisibility spell you may be able"+
			" to sneak by them and leave the cave, but don't forget to steal the potion first."+
			"  The exit is just behind where their master"+
			" sits on the north end of their Great Hall. \'\nThe old man begins to cast a spell"+
			".\nYour skin tingles and you look down, but only see the muddy floor where your"+
			" body should be.  The old man's spell has rendered you completely invisible.\n"+
			"\' Good luck and may the gods be with you, Prince Zhane. \'");
			state = 0;
			isWizard = true;
			plyr.changeState(2); //you are invisible
			break;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		Player plyr = new Player();
		Monster ogre = new Monster();
		Monster goblin = new Monster();
		Monster master = new Monster();
		Room9 room = new Room9(plyr, ogre, goblin, master);
		//plyr.flagItem(true, 4);
		//plyr.flagItem(true, 5);
		
		//Check zero state!
		
		//zero state look around room
		String s = "look nothing";
		String[] input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state n, 
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state s, 
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state e, 
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
		
		
		//zero state grab 
		s = "grab wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab 
		s = "grab goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state drop 
		s = "drop sword nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		zero state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state attack 
		s = "attack wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		zero state yell
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
		
//		zero state attack shouldn't work
		s = "attack goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
// **********  STATE ONE ****************************************** //
		
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
		
		//first state listen 
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab
		s = "grab wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab coin
		s = "grab man nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	
//		first state drop pants
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		first state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state attack 
		s = "attack wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
	
//		first state yell 
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		first state inventory check
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		//System.out.println(""+plyr.getItem(1));
		
// ***********  Transition into state two or three*********** //
		
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


// ******************* State TWO or Three***************************** //

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
		
		//first state listen 
		s = "listen nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab
		s = "grab wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//first state grab coin
		s = "grab man nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
	
//		first state drop pants
		s = "drop pants nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		first state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		first state attack 
		s = "attack wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
	
//		first state yell 
		s = "yell nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());		
		
//		first state inventory check
		s = "inventory nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		//System.out.println(""+plyr.getItem(1));
		
// *********  Transition into state zero again ****************** //
		
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


// ****************  BACK TO STATE ZERO  ****************************** //
		//zero state look around room
		s = "look nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state n, 
		s = "n nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state s, 
		s = "s nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state e, 
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
		
		
		//zero state grab 
		s = "grab wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state grab 
		s = "grab goblin nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		//zero state drop 
		s = "drop sword nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
		
//		zero state hide 
		s = "hide nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());
		
//		zero state attack 
		s = "attack wizard nothing";
		input = s.split("\\s");
		room.setText(input);
		room.runFSM();
		System.out.println(room.getOutput());	
		
//		zero state yell
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
*/ 
	}

}
