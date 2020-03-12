package heidiConnectFour;

import javax.swing.JOptionPane;

public class Piece {

	private char colour;
	
	public Piece() {}
	
	public Piece(char colour) {
		this.colour = colour;
	}
	
	public String toString() {
		String output;
		
		output = "Piece colour is " + this.colour;
		
		return output;
	}
	
	public void displayDetails() {
		String output;

	    output = toString();
	    
	    JOptionPane.showMessageDialog(null, output, "Piece", JOptionPane.INFORMATION_MESSAGE);  
	}
	
	public char getColour() {
		return this.colour;
	}	
	
}//end class
