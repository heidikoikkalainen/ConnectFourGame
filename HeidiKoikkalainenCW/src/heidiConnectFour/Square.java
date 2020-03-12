package heidiConnectFour;

import javax.swing.JOptionPane;

public class Square {
	
	public Piece piece;
	public boolean empty;
	
	public Square() {
		empty = true;
	}
	
	public String toString() {
		String toString;
		boolean squareStatus;
		char pieceColour;
		
		squareStatus = isEmpty();
		
		if(squareStatus == true) {
			toString = " ";
		}else {
			pieceColour = getPiece().getColour();
			toString = Character.toString(pieceColour);
		}
		return toString;
	}
	
	public void displayDetails() {
		String output;
		boolean squareStatus;
		
		squareStatus = isEmpty();
		
		if(squareStatus == true) {
			output = "Square is empty";
		} else {
			output = "Square is occupied";
		}
	    JOptionPane.showMessageDialog(null, output, "Square", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean isEmpty() {
		return this.empty;
	}
	
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		this.empty = false;
	}

}//end class
