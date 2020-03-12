package heidiConnectFour;

import javax.swing.JOptionPane;

public class Player {

	private int playerColour;

	public Player() {}

	public Player(int playerColour) {
		this.setPlayerColour(playerColour);
	}

	public String toString() {
		String output;

		output = "Piece colour: " +this.playerColour;

		return output;
	}

	public void displayDetails() {
		String output;

		output = toString();

		JOptionPane.showMessageDialog(null, output, "Player", JOptionPane.INFORMATION_MESSAGE);
	}

	public int getPlayerColour() {
		return playerColour;
	}

	public void setPlayerColour(int playerColour) {
		this.playerColour = playerColour;
	}

}//end class
