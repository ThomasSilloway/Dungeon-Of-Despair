import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays a window with all commands that may be used with Dungeon of Despair.
 * @author Thomas Silloway
 *
 */
public class HelpWindow extends JFrame{
	JTextArea helpWindow;
	/**
	 * Null constructor for initializing GUI
	 */
	public HelpWindow(){
        //where GUI is initialized
		super("Help Window");
		//set default gui parameters
		setSize(340,370);
		setLocation(150,150);	
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create object
		helpWindow = new JTextArea(14, 10);
		helpWindow.setEditable(false);
		helpWindow.setText(this.getText());
		helpWindow.setLineWrap(true);
		helpWindow.setWrapStyleWord(true);
		//create content pane and add objects
		Container c = getContentPane();
		
		c.add(helpWindow,"North");
		setVisible(false);
	}
	/**
	 * The text that should be displayed in the Help window
	 * @return the text
	 */
	public String getText(){
		String s = "You must either type either a command by itself or a command followed"+
		" by any word.  The commands that work by themselves are:\n\n"+
		"n \tto move north\n"+
		"s \tto move south\n"+
		"e \tto move east\n"+
		"w \tto move west\n"+
		"look \tto look around the room\n"+
		"listen \tto listen to things going on around you\n"+
		"hide \tto hide in the corner of the room\n"+
		"yell \tto send out a cry for help\n"+
		"agree \tto make a gesture of agreement\n"+
		"disagree \tto make a gesture of disagreement\n"+
		"inventory \tto see a list of all the things that you have\n\n"+
		"The following commands require a noun to come after them:\n\n"+
		"grab \tto grab an object\n"+
		"drop \tto drop an object\n"+
		"attack \tto attack an object";
		
		return s;
	}
	/**
	 * Use for testing, starts up the help window
	 * @param args not used
	 */
	public static void main(String[] args){
		HelpWindow hw = new HelpWindow();
		hw.show();
	}
}
