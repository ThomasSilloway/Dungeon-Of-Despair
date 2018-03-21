/**
 * This is the player class, holds the characters inventory and state as well as the counter
 * for randomizing deaths and other things
 * @author Thomas Silloway
 * @version 1.5
 */
public class Player {
	
	private boolean coin;
	private boolean mirror;
	private boolean gShard;
	private boolean sword;
	private boolean shield;
	private boolean vial; //at end of game
	private int swords; //swords and shields used in room 5
	private int shields;
	private int state;  //state of character 0=normal, 1=bleeding, 2=invis
	private int counter; //used to randomize events

	/**
	 * Null constructor
	 */
	public Player(){
		coin = false;
		mirror = false;
		gShard = false;
		sword = false;
		shield = false;
		vial = false;
		swords = 0;
		shields = 0;
		state = 0;
	}
	/**
	 * Increases the value of counter by 1 each time, used to randomize events
	 */
	public void increaseCounter(){
		counter++;
	}
	/**
	 * Gets the value of counter, which is used to randomize events
	 * @return the value of counter
	 */
	public int getCounter(){
		return counter;
	}
	/**
	 * Puts all of the items a character has into a list that can be returned by typing "inventory"
	 * @return the list of items
	 */
	public String getInventory(){
		String inv = "Inventory: \n";
		int i = 0; //used for counting
		if(getItem(1))
			inv += "A shiny silver coin\n";
		if(getItem(2))
			inv += "A dusty old mirror\n";
		if(getItem(3))
			inv += "A long, sharp piece of glass (It looks dangerous)\n";
		if(getItem(4))
			for(i = 1; i <= swords; i++){
				inv += "Short Sword\n";
			}
		if(getItem(5))
			for(i = 1; i <= shields; i++){
				inv += "Small Buckler\n";
			}
		if(getItem(6))
			inv += "A vial of liquid\n";
		if(inv.equals("Inventory: \n"))
			inv += "Your inventory is empty.";
		return inv;
	}
	
	
	
	/******  Change flag methods  *********/
	
	
	
	/**
	 * Changes the flag on items or states to input value
	 * @param b the new value of items or states, true if player has coin
	 * @param i the item or state that to change the flag for 
	 */
	public void flagItem(boolean b, int i){
		switch(i){
			case 1: coin = b; break;
			case 2: mirror = b; break;
			case 3: gShard = b; break;
			case 4:  
				if(b){
					swords++;
					sword = b;
				}
				if(!b){
					swords--;
					if(swords == 0){
						sword = false;
					}
				}
				break;
			case 5:
				if(b){
					shields++;
					shield = b;
				}
				if(!b){
					shields--;
					if(shields == 0){
						shield = false;
					}
				}
				break;
			case 6: vial = b; break;
		}
	}
	
	/**
	 * Changes the state
	 * @param s the state of the character
	 */
	public void changeState(int s){
		state = s;
	}
	
	
	
	/*****  Return flag methods  ******/
	
	/**
	 * Item return method
	 * @param item the item that you want to return flag for
	 * @return true if have item
	 */
	public boolean getItem(int item){
		//System.out.println("Value of coin in Player: "+coin);
		switch(item){
			case 1: return coin; 
			case 2: return mirror; 
			case 3: return gShard; 
			case 4: return sword; 
			case 5: return shield;
			case 6: return vial;
		}
		return false;
	}
	/**
	 * Return the state
	 * @return state that you are in 0 = good 1 = bleeding
	 */
	public int getState(){
		return state;
	}
	
	
	
	/**
	 * Use for testing only
	 * @param args not used
	 */
	public static void main(String[] args) {
	/*	
		Player plyr = new Player();
		//inventory test 1
		System.out.println("Empty Inventory: "+plyr.getInventory());
		plyr.flagItem(true, 1);
		System.out.println("Inventory (coin only): "+plyr.getInventory());
		plyr.flagItem(true, 2);
		System.out.println("Inventory (coin and mirror): "+plyr.getInventory());
		plyr.flagItem(false, 1);
		System.out.println("Inventory (mirror only): "+plyr.getInventory());
		plyr.flagItem(true, 3);
		System.out.println("Inventory (mirror, shard): "+plyr.getInventory());
		plyr.flagItem(true, 4);
		System.out.println("Inventory (mirror, shard, sword: "+plyr.getInventory());
		plyr.flagItem(true, 5);
		System.out.println("Inventory (mirror, shard, sword, shield): "+plyr.getInventory());
		plyr.flagItem(true, 6);
		System.out.println("Inventory (mirror, shard, sword, shield, spear): "+plyr.getInventory());
		plyr.flagItem(false, 6);
		System.out.println("Inventory (mirror, shard, sword, shield): "+plyr.getInventory());
		plyr.flagItem(false, 3);
		System.out.println("Inventory (mirror, sword, shield): "+plyr.getInventory());
		
		
		//inventory test 2
		plyr.flagItem(true, 4);
		System.out.println("Inventory (1 sword(s)): "+plyr.getInventory());
		plyr.flagItem(true, 4);
		System.out.println("Inventory (2 sword(s)): "+plyr.getInventory());
		plyr.flagItem(true, 4);
		System.out.println("Inventory (3 sword(s)): "+plyr.getInventory());
		plyr.flagItem(true, 4);
		System.out.println("Inventory (4 sword(s)): "+plyr.getInventory());
		plyr.flagItem(true, 4);
		System.out.println("Inventory (5 sword(s)): "+plyr.getInventory());
		
		plyr.flagItem(true, 5);
		System.out.println("Inventory (1 shield(s)): "+plyr.getInventory());
		plyr.flagItem(true, 5);
		System.out.println("Inventory (2 shield(s)): "+plyr.getInventory());
		plyr.flagItem(true, 5);
		System.out.println("Inventory (3 shield(s)): "+plyr.getInventory());
		plyr.flagItem(true, 5);
		System.out.println("Inventory (4 shield(s)): "+plyr.getInventory());
		plyr.flagItem(true, 5);
		System.out.println("Inventory (5 shield(s)): "+plyr.getInventory());
		
		plyr.flagItem(false, 5);
		System.out.println("Inventory (4 shields(s)): "+plyr.getInventory());
		plyr.flagItem(false, 5);
		System.out.println("Inventory (3 shields(s)): "+plyr.getInventory());
		plyr.flagItem(false, 5);
		System.out.println("Inventory (2 shields(s)): "+plyr.getInventory());
		plyr.flagItem(false, 5);
		System.out.println("Inventory (1 shields(s)): "+plyr.getInventory());
		plyr.flagItem(false, 5);
		System.out.println("Inventory (0 shields(s)): "+plyr.getInventory());
		
		plyr.flagItem(false, 4);
		System.out.println("Inventory (4 sword(s)): "+plyr.getInventory());
		plyr.flagItem(false, 4);
		System.out.println("Inventory (3 sword(s)): "+plyr.getInventory());
		plyr.flagItem(false, 4);
		System.out.println("Inventory (2 sword(s)): "+plyr.getInventory());
		plyr.flagItem(false, 4);
		System.out.println("Inventory (1 sword(s)): "+plyr.getInventory());
		plyr.flagItem(false, 4);
		System.out.println("Inventory (0 sword(s)): "+plyr.getInventory());
		
		
		
		
		//Done testing inventory
		System.out.println("Done testing inventory.");
		
		//test status
		System.out.println("State should be 0: "+plyr.getState());
		plyr.changeState(1);
		System.out.println("State should be 1: "+plyr.getState());
		plyr.changeState(2);
		System.out.println("State should be 2: "+plyr.getState());
		*/

	}

}
