/**
 * Error Checking class
 * Looks to see if there the input is valid
 * Returns true if invalid
 * @author Thomas Silloway
 *
 */
public class EChecker {
	String input;
	boolean notValid;
	
	/**
	 * null constructor
	 */
	public EChecker(){
		input = "";
		notValid = true;
	}
	/**
	 * Takes in the input and makes sure the first word is not notValid
	 * @param i the input string
	 * @return false if first word is valid, true otherwise
	 */
	public boolean notCommand(String i){
		notValid = true;
		String[] input2 = i.split("\\s");
		input = input2[0].trim();
		input = input.toLowerCase();
		//System.out.println(notValid+"");
		//run tests to see if input is true
		directions();
		senses();
		actions();
		chkInventory();
		return notValid;
	}
	
	//**** Private Methods for Checking validity of input*****
	/**
	 * Checks for cardinal directions
	 */
	private void directions(){
		if(input.equals("w") || input.equals("e") ||
		   input.equals("n") || input.equals("s")){
			notValid = false;
		}
	}
	/**
	 * Checks for look and listen
	 */
	private void senses(){
		if(input.equals("look") || input.equals("listen")){
			notValid = false;
		}
	}
	/**
	 * Checks for all of the actions possible in this game
	 */
	private void actions(){
		if(input.equals("grab") || input.equals("drop") ||
		   input.equals("hide") || input.equals("attack") ||
		   input.equals("yell") || input.equals("agree") || 
		   input.equals("disagree")){
			notValid = false;
		}
	}
	/**
	 * Checks for inventory
	 */
	private void chkInventory(){
		if(input.equals("inventory")){
			notValid = false;
		}
	}
	/**
	 * used for testing
	 * @param args not used
	 */
	public static void main(String[] args) {
		/*EChecker checker = new EChecker();
		
		
		//Test for valid commands
		String valids = "n e w s look listen grab drop hide "+
			 "attack yell agree disagree inventory N E W S Look ListeN"+
			 " Grab Drop Hide Attack Yell Agree Disagree";
		String[] valid = valids.split("\\s");
		for (int i = 0; i < valid.length; i++){
			//System.out.println(valid[i]);
 		  if(checker.notCommand(valid[i])){
			System.out.println("That command does not work here.");
		  }
		  else
			System.out.println("Works");
		}
		System.out.println("End working ones\n\n");
		
		
		
		//Test for non working commands
		String nonvalids = "North South Poop Run Walk atack"+
			"yel say drp grb atk kill";
		String[] nonvalid = nonvalids.split("\\s");
		for (int i = 0; i < nonvalid.length; i++){
				//System.out.println(nonvalid[i]);
	 		  if(checker.notCommand(nonvalid[i])){
				System.out.println("That command does not work here.");
			  }
			  else
				System.out.println("Works");
		}
		*/
	}

}
