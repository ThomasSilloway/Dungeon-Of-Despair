/**
 * This is the ogre.
 * States are for if he's chasing you, alive(not chasing) or dead
 * inherits changeState(int) and getState()
 * @author Thomas Silloway
 * @version 1.3
 */
public class Monster{
	//state 0 for alive but static
	//state 1 for in chase
	//state 2 for in combat
	//state 3 for dead/stunned
	//state 4 if player is dead
	private int state;
	
	/**
	 * null constructor
	 */
	public Monster(){
		state = 0;
	}
	
	/**
	 * Change the state of the monster
	 * @param s the state that the monster is in
	 */
	public void changeState(int s){
		state = s;
	}
	/**
	 * Gets the state that the monster is in
	 * @return the state
	 */
	public int getState(){
		return state;
	}
	/**
	 * Used for testing
	 * @param args not used
	 */
	public static void main(String[] args) {
		/*
		Monster ogre = new Monster();
		System.out.println("Should be 0: "+ogre.getState());
		ogre.changeState(1);
		System.out.println("Should be 1: "+ogre.getState());
		ogre.changeState(2);
		System.out.println("Should be 2: "+ogre.getState());
		ogre.changeState(3);
		System.out.println("Should be 3: "+ogre.getState());
		*/
	}

}
