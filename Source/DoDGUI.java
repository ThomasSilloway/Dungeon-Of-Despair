import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * This is the main GUI window that will the user will interface with to play Dungeon of Despair.
 * The Enter button submits a command from the user to the backend model.
 * The Help button pops up a new GUI screen, the Help window
 * The Text Area in the middle is where all of the text will go from the backend model.
 * 
 * 
 * Finished: 2/4/2006
 *
 * @author Thomas
 * @version 1.10
 */
public class DoDGUI extends JFrame implements ActionListener, KeyListener{
	private JTextArea display;
	private JTextField TFinput;
	private JButton enter;
	private JButton help;
	private HelpWindow helpWindow;
	private JScrollPane scrollPane;
	//String that holds all of the content so far
	private String output;
	private EChecker checker;
	private Backend backend;
	
	
	/**
	 * Null constructor
	 * initializes the GUI
	 */
	public DoDGUI(EChecker chk, Backend b){
		//where GUI is initialized
		super("The Dungeon of Despair");
		//set default gui parameters
		this.checker = chk;
		this.backend = b;
		setSize(600,600);
		setLocation(100,100);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//color for buttons
		Color purple = new Color(128,128,255);
		
		//initialize output
		output = "";
		//create objects
		enter = new JButton("Enter");
		  enter.setBackground(purple);
		help = new JButton("Help");
		  help.setBackground(purple);
		display = new JTextArea(2,10);
		  display.setEditable(false);
		  display.setText("Type any valid command to begin.  Press the help button to see a"+
				  " list of valid commands.");
		  display.setLineWrap(true);
		  display.setWrapStyleWord(true);
		TFinput = new JTextField("Your input goes here.",40);
		scrollPane = new JScrollPane(display);
		PictureDrawer pd = new PictureDrawer();
			
		
		//create content pane and add objects
		Container c = getContentPane();
		
		JPanel inputsAndButtons = new JPanel();
		  inputsAndButtons.add(help);
		  inputsAndButtons.add(TFinput);
		  inputsAndButtons.add(enter);
		  
		help.addActionListener(this);
		enter.addActionListener(this);
		TFinput.addKeyListener(this);
		
		c.add(pd,"North");
		c.add(scrollPane, "Center");
		c.add(inputsAndButtons,"South");
		setVisible(true);
		
		
		//initialize help window
		helpWindow = new HelpWindow();
	}

	/**
	 * Listens for actions
	 * @param e the event that was performed
	 */
	public void actionPerformed (ActionEvent e) {
		if(e.getSource() == enter){
			enterCommand();

		}
		if(e.getSource() == help){
			//open help window
			helpWindow.setVisible(true);
			repaint();

		}
	}
	/**
	 * Needed for implements KeyListener - not used
	 */
	public void keyTyped(KeyEvent k){
	}
	/**
	 * Needed for implements KeyListener - not used
	 */
	public void keyReleased(KeyEvent k){
	}
	/**
	 * If you press enter, it does the same thing as if you click the enter button
	 */
	public void keyPressed(KeyEvent k){
		if(k.getKeyCode() == 10){//if you press enter
			enterCommand();
		}
	}
	/**
	 * The actions that take places when you press the Enter key on keyboard
	 * Or press the button enter
	 * Checks input to make sure the first word is valid
	 * sends input to backend, runs the FSM, and gets the output
	 */
	public void enterCommand(){
		//Get input and error check
		String input = TFinput.getText();
		input = input.toLowerCase();
		if(checker.notCommand(input)){
			if(output.equals("")){
				output+= "That command does not work here.";
			}
			else{
				output+= "\n\nThat command does not work here.";
			}
			//System.out.println("Error: wrong input");  //used for testing
			display.setText(output);
		}
		else{
			//send input to backend model
			backend.setText(input);
			backend.runFSM();
			String s = "";
			//set output to what to new string
			s = backend.getOutput();
			//System.out.println(s); //used for testing
			if(output.equals("")){
				output += s.substring(2,s.length());
			}
			else{
				output += s;
			}
			display.setText(output);
		}
		TFinput.setText("");		
	}
	/**
	 * Starts up the GUI
	 * @param args
	 */
	public static void main(String[] args) {
		
		EChecker checker = new EChecker();
		Backend backend = new Backend();
		DoDGUI dodgui = new DoDGUI(checker, backend);
		dodgui.show();
		

	}

}
