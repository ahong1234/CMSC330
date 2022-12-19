
/*
 * Name: Alex Hong
 * Due: 9/13/22
 * Class: CMSC330
 * Instructor: Prof. Elizes
 * Description: A program for creating an array list from each token from the input file 
 * 				and then traversing through it to create and display a top level frame 
 * 				and the subsequent specified panels and widgets.
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class runClass4 {
	JFrame topFrame;
	private ArrayList<String> lot = new ArrayList<String>();
	BufferedReader br;
	static int index = 0;
	static boolean isTopFrame;
	JPanel panel;
	JButton button;
	ButtonGroup buttons;
	JRadioButton b;
	JTextField text;
	JLabel label;
	Boolean rad;
	int line = 1;
	int ct = 0;
	// constructor
	public runClass4(String readFile) {
		try {
			br = new BufferedReader(new FileReader(readFile));
			// make an array of each specified token 
			scanInput();
			
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "file not found");
			exit();
		}
	}
	
	// use buffered reader to scan the input file
	private void scanInput() {
		try {
			String line;
			String[] tokens;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("Window")> -1) {
					processWindowCreation(line);
				}
				
				else
				if(line.indexOf("Label") > -1) {
					processLabelCreation(line);
					
				}
					
				else 
				if (line.indexOf("Radio")>-1) {
					buttons = new ButtonGroup();
					processRadioButtons(line);
				}
				
				else
				if (line.indexOf("Button")> -1) {
					processButtonCreation(line);
				}
				else
				if (line.indexOf("Panel") > -1) {
					processPanelCreation(line);
				}
				else
				if (line.indexOf("Textfield") >-1) {
					processTextfieldCreation(line);
				}
				else
				if (line.indexOf("End.") >-1) {
					
					processShowDialog();
				}
				
				
				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	// create a panel, its layout and its widgets
	private boolean processPanelCreation(String line) {
		line = line.trim();
		String[] delimited = line.split(" ");
		//System.out.println(line);
//		try {
//			System.out.println(br.readLine());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.out.println(delimited.length);
		
		if (delimited.length == 6) {
			String grid1 = "";
			String[] grid1A;
			int dim1 =0;
			int dim2 =0;
			int dim3 =0;
			int dim4 = 0;
			delimited[2] = delimited[2].replace("(", " ");
			delimited[2] = delimited[2].replace(",", "");
			delimited[3] = delimited[3].replace(",", "");
			delimited[4] = delimited[4].replace(",", "");
			delimited[5] = delimited[5].replace(")", " ");
			delimited[5] = delimited[5].replace(":", " ");
			for (int i = 0; i < delimited.length; i++) {
				grid1 += delimited[i] + " ";
			}
			//System.out.println(grid1);
			grid1A = grid1.split(" ");
			
			if (panelCheck(grid1A)) {
				try {
					dim1 = Integer.parseInt(grid1A[3]);
					dim2 = Integer.parseInt(grid1A[4]);
					dim3 = Integer.parseInt(grid1A[5]);
					dim4 = Integer.parseInt(grid1A[6]);
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "syntax error panel creation: parameters must be integers");
					exit();
				}
				
				//panel = new JPanel();
				
				if (isTopFrame) {
					topFrame.add(panel = new JPanel());
				}
				else {
					panel.add(panel = new JPanel());
				}
				panel.setLayout(new GridLayout(dim1, dim2 , dim3, dim4));
				isTopFrame = false;
				//System.out.println(dim1 + " " + dim2 + " " + dim3 + " " + dim4);
				if(makeWidgets()) {
					isTopFrame = true;
					return true;
					
				}
			}
			
		}
		else if (delimited.length == 4) {
			String grid2 = "";
			String[] g2;
			//isTopFrame = false;
			int dim1 =0;
			int dim2 =0;
			delimited[2] = delimited[2].replace("(", " ");
			delimited[2] = delimited[2].replace(",", "");
			delimited[3] = delimited[3].replace(")", " ");
			delimited[3] = delimited[3].replace(":", "");
			for (int i = 0; i < delimited.length; i++) {
				grid2 += delimited[i] + " ";
			}
			//System.out.println(grid2);
			g2 = grid2.split(" ");
			if (panelCheck(g2)) {
				try {
					dim1 = Integer.parseInt(g2[3]);
					dim2 = Integer.parseInt(g2[4]);
					
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "syntax error panel creation: parameters must be integers");
					exit();
				}
			}
			//System.out.println(dim1 + " " + dim2);
			
			//panel = new JPanel();
			
			if (isTopFrame) {
				topFrame.add(panel = new JPanel());
			}
			else {
				panel.add(panel = new JPanel());
			}
			
			panel.setLayout(new GridLayout(dim1, dim2));
			isTopFrame = false;
			if(makeWidgets()) {
				isTopFrame = true;
				return true;
			}	
		}
		else if (delimited.length == 3) {
			String flow = "";
			String[] f1;
			delimited[2] = delimited[2].replace(";", "");
			//System.out.println(delimited[2]);
			if (delimited[2].toUpperCase().equals("FLOW")) {
			
			if (isTopFrame) {
				topFrame.add(panel = new JPanel());
			}
			else {
				panel.add(panel = new JPanel());
			}
			panel.setLayout(new FlowLayout());
			isTopFrame = false;
			if(makeWidgets()) {
				isTopFrame = true;
				return true;
			}
			}
		}
		return false;
	}
	
	// scan input for widgets to create 
	private boolean makeWidgets() {
		if (makeWidget()) {
			if (makeWidgets()) {
				return true;
			}
			return true;
		}
		return false;
	}
	private boolean makeWidget() {
		try {
			String line1;
			while ((line1 = br.readLine()) != null) {
				if (line1.indexOf("Button") > -1) {
					//System.out.println(line1);
					processButtonCreation(line1);
					return true;
				}
				
				else
				if(line1.indexOf("Label") > -1) {
					processLabelCreation(line1);
					return true;
				}
				
				else 
				if (line1.indexOf("Radio")>-1) {
					buttons = new ButtonGroup();
					processRadioButtons(line1);
					
					return true;
				}
				
				else
				if (line1.indexOf("Textfield") >-1) {
					processTextfieldCreation(line1);
					return true;
				}
				else
					if (line1.indexOf("Panel") > -1) {
						processPanelCreation(line1);
						return true;
					}
				else
				if (line1.indexOf("End;") > -1) {
					if (rad = false) {
						return true;
					}
					return false;
				}
				else
					if (line1.indexOf("End.") > -1) {
						JOptionPane.showMessageDialog(null, "panel not closed");
						exit();
					}
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	private boolean panelCheck(String[] a) {
		if (!a[0].toUpperCase().equals("PANEL")) {
			JOptionPane.showMessageDialog(null, "syntax error panel creation: line must start with \"panel\"");
			exit();
		}
		else if (!a[1].toUpperCase().equals("LAYOUT")){
			JOptionPane.showMessageDialog(null, "syntax error panel creation: line must containt \"layout\"");
			exit();
		}
		return true;
	}
	private void processWindowCreation(String line) {
		int height = 0;
		int width = 0;
		String[] delimited = line.split(" ");
		if (delimited.length != 6) {
			JOptionPane.showMessageDialog(null, "syntax error = first line must be 6 words");
			exit();
		}
		else if (!delimited[4].toUpperCase().equals("LAYOUT")) {
			JOptionPane.showMessageDialog(null, "syntax error line 1 = incorrect/missing placement of: \"layout\"");
			exit();
		}
		if (!delimited[0].toUpperCase().equals("WINDOW")) {
			JOptionPane.showMessageDialog(null, "syntax error window creation: first word must be \"window\"");
			exit();
		}
		String title = delimited[1];
		
		String widthcoordsStr = delimited[2];
			
		String heightcoordsStr = delimited[3];
			
		String layoutStr = delimited[5];
		// Get rid of \", (, ,, and ).
				title = title.replace('\"',' ');
				widthcoordsStr = widthcoordsStr.replace('(',' ');
				widthcoordsStr = widthcoordsStr.replace(',',' ');
				heightcoordsStr = heightcoordsStr.replace(')',' ');
					
				// Trim spaces.
				title = title.trim();
				widthcoordsStr = widthcoordsStr.trim();
				heightcoordsStr = heightcoordsStr.trim();
				
				try {
					width =Integer.parseInt(widthcoordsStr);
					height = Integer.parseInt(heightcoordsStr);
				}
				catch(Exception ex) {
					// TO-DO Output Error Message
				}
				topFrame = new JFrame(title);
				topFrame.setSize(width, height);
				topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//System.out.println(layoutStr);
				if (layoutStr.toUpperCase().equals("FLOW:")) {
					
					isTopFrame = true;
					topFrame.setLayout(new FlowLayout());
				}
			
				// Test string output function.
				//System.out.println("Title: "+ title + " Width: "+width+ " Height: "+height);			
	}
	
	private void processButtonCreation(String line) {
		int firstquoteIndex = line.indexOf("\"");
		String stringval = line.substring(firstquoteIndex);
		
		// Get rid of \" and ;.
		stringval = stringval.replace('\"',' ');
		stringval = stringval.replace(';',' ');
		
		// Trim spaces
		stringval = stringval.trim();
		button = new JButton(stringval);
		if (isTopFrame) {
			topFrame.add(button);
		}
		else {
			panel.add(button);
		}
		
		//System.out.println("String value: "+stringval);
	}
	
	 private boolean processRadioButtons(String line){
		 	rad = true;
	        if(processRadioButton(line)){
	            if(processRadioButtons(line)){
	                return true;
	            }
	            return true;
	        }
	        return false;
	    }
	
	private boolean processRadioButton(String line) {
		String r ="";
		String[] list;
		
		for(int i = line.indexOf("Radio"); i < line.length(); i++) {
			r += line.charAt(i);
		}
		//System.out.println(r);
		list = r.split(" ");
		if (list.length == 2) {
			list[1] = list[1].replace("\"", "");
			list[1] = list[1].replace(";", "");
			list[1] = list[1].trim();
			b = new JRadioButton(list[1]);
			buttons.add(b);
			if (isTopFrame) {
				topFrame.add(b);
			}
			else {
				panel.add(b);
			}
			rad = false;

		}
		return false;
	}
	
	private void processTextfieldCreation(String line) {
		int columns = 0;
		line = line.trim();
		String[] delimited = line.split(" ");
		for (int i = 0; i < delimited.length; i++) {
			delimited[i] = delimited[i].trim();
			//System.out.println(delimited[i]);
			//System.out.println(delimited.length);
		}
		if (delimited.length != 2) {
			JOptionPane.showMessageDialog(null, "syntax error for textfield creation: line must be 2 words");
			exit();
		}
		
		delimited[1] = delimited[1].replace(";", "");
		
		try {
			columns = Integer.parseInt(delimited[1]);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "syntax error for textfield creation: columns must be specified as integers");
			exit();
		}	
		text = new JTextField(columns);
		if(isTopFrame) {
			topFrame.add(text);
		}
		else {
			panel.add(text);
		}
	}
	
	private void processLabelCreation(String line) {
		line = line.trim();
		String[] delimited = line.split(" ");
		delimited[1] = delimited[1].replace("\"", "");
		delimited[1] = delimited[1].replace(";", "");
		label = new JLabel(delimited[1]);
		if (isTopFrame) {
			topFrame.add(label);
		}
		else {
			try {panel.add(label);
			}
			catch (NullPointerException e) {
				System.out.println("Panel cannot be made. Error with Frame.");
				JOptionPane.showMessageDialog(null, "Panel cannot be made. Error with Frame.");
				exit();
			}
		}
	}
	
	
	// display the gui
	private void processShowDialog() {
		
		topFrame.setVisible(true);
		System.out.println("GUI COMPLETE");
	}
	
	private void exit() {
			System.exit(0);
		
	}
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("The first argument should be a text file.");

			
		}
			else {
			String fileName = args[0];
			runClass4 run = new runClass4(fileName);
			}	
}
}