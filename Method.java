package connectFour;

import javax.swing.JLabel;

public class Method {
	
	
	public static void announceTurn(JLabel label, int turn) // announces current turn
	{

		if(turn % 2 == 0)
		{
		label.setText("Player 1 Select Your Slot");
	    }
		else 
		{
			label.setText("Player 2 Select Your Slot");
		}
	}
	
	
	public static int playerTurn(int turn) // checks whose turn it is
	{
		
		if(turn % 2 == 1)
		{
			return 1;
		}
		
		else 
			
		return 2;
	}
		
	
}
