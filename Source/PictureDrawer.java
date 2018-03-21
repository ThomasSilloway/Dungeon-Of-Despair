import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PictureDrawer extends JPanel{
	//	image settints
	private int x;
	private int y;
	private ImageIcon image;
	
	/**
	 * null constructor
	 * set the size of picture and position as well as what picture to put in
	 */
	public PictureDrawer(){
		//loads .gif file
		ClassLoader cl=this.getClass().getClassLoader();  // Gets a special loader...
		image = new ImageIcon (cl.getResource("DoDpic.jpg")); 
		
		x=0;
		y=0;
		setPreferredSize (new Dimension (598,86));
	}
	/**
	 *
	 * Paint icon at (x,y)
	 * 
	 * @param page graphics object used for updates
	 **/
	public void paintComponent (Graphics page) {
		super.paintComponent(page);
		image.paintIcon( this, page, x, y);
	}
	
}
