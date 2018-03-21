# Dungeon-Of-Despair
My first original video game project - a text RPG with it's own GUI.

This project is a mess, but uploaded it so someone could use the dialogue for a new project :)

Not sure that these instructions work any more:

# Dungeon of Despair Installation Instructions for Windows XP

1.  Make sure you have Java on your computer, if you think you do skip to step 2.

If you don't have Java JRE on your computer, do the following:

1a. Connect to the internet and goto http://java.sun.com/j2se/1.5.0/download.jsp
1b. Find the Java 5.0 JRE download link and click it.
1c. Click accept agreement.
1d. Under Windows Platform - J2SE(TM) Runtime Environment 5.0 Update 6 click Windows
	Online Installation.
1e. Download and open file and follow on screen instructions.  This might take a while.

2. Find the path to the bin folder in your java folder it should have a directory location
	 like the following:
	
	c:\Program Files\Java\jdk1.5.0_<version number>\bin

3. Now, Open your windows Control Panel from the Start menu and double click System

4. Click the Advanced Tab and Click Environment Variables.

5. Under System Variables, click CLASSPATH and click the edit button.  Delete whatever 
	is in there and enter the following the path at the end should be the path that
	you found in step 2:

	=c:\Program Files\Java\jdk1.5.0_<version number>\bin
	
	Click OK.

6. Now scroll down in System Variables and find the variable PATH, click on it and click
	the edit button.  
	::::WARNING::::  DO NOT DELETE contents of this window.  
	Click on the contents of the window and press the End button on your keyboard to scroll
	to the end of the text area.
	At the end of this area, type in the following with no spaces after what is already
	there where the path is the same as the one in step 2 and 5:

	;C:\Program Files\Java\jdk1.5.0_<version number>\bin  

	where <version number> is 01 or 02 or 03, etc. 

7. Next, take the folder that I sent you and move it into C:\  so the path of the folder is:
	C:\Dungeon of Despair

8. Now click start, run and type in cmd.exe  The command prompt should open

9. Type in the following command to enter the Dungeon of Despair folder:

	cd C:\Dungeon of Despair

10. Type java DoDGUI and the game should start

11.  If it says "Exception in thread "main" java.lang.NoClassDefFoundError: Default" type the
	following into the console:
	
	set CLASSPATH=

12. Return to step 10
