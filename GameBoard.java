package connectFour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

public class GameBoard extends JFrame implements ActionListener , ItemListener{

	final int ROW = 6;
	final int COL = 7;
	int turnNum = 0; // number of turns passed
	JPanel mainPanel,gamePanel; // two panels for card layout
	CardLayout cardLayout = new CardLayout(); 
	Color color1,color2; // player 1 and 2 colors
	Color colorb = Color.CYAN; // background color
	Color colorTurn; //color of the player who just went
	int count;
	// possible color options
	String[] comArr1 = {"Select","Red","Green","Blue","Yellow","Pink"	
			,"Black"};
	
	JComboBox select1 = new JComboBox(comArr1); // comboboxes for color choices
	JComboBox select2 = new JComboBox(comArr1);
	
	boolean check1 = false; // error checks for panel 1
	boolean check2 = false;
	
	// MainPanel ----------------------
	
			// text in panel 1:
	JLabel title;
	JLabel choose;
	JLabel players;
	JLabel spacing1,spacing2,spacing3;
	
	JPanel north,center,south; // border panels
	
	JLabel check;
	
	JButton cont; // continue button
	
	//GamePanel -----------------------
	JPanel[][] pane;
	JPanel north2,east2,south2,west2,center2;
	JButton b1,b2,b3,b4,b5,b6,b7;
			// menu bar
	JMenuBar bar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenuItem exit = new JMenuItem("Quit");  // quit "button"
	JMenuItem reset = new JMenuItem("Reset"); // reset "button"
	
	JLabel turn; // announces player turn
	JLabel isFull; // announces if column is full
	
	public GameBoard()
	{
		super("Connect 4");
		setLayout(cardLayout);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,250);
		count = 0;
	//  mainPanel ------------------------------------------
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

			// border layout panels and more----------------
		north = new JPanel();
		north.setLayout(new FlowLayout());
		center = new JPanel();
		center.setLayout(new FlowLayout());
		south = new JPanel();
		south.setLayout(new FlowLayout());
		
		mainPanel.add(north,BorderLayout.NORTH);
		mainPanel.add(center,BorderLayout.CENTER);
		mainPanel.add(south,BorderLayout.SOUTH);
	
			//------------------------------------------------
		title = new JLabel("Welcome To Connect Four!");
		title.setFont(new Font("Arial",Font.BOLD,25));
		choose = new JLabel("Please Choose Your Colors:");
		choose.setFont(new Font("Arial",Font.PLAIN,15));
		cont = new JButton("Continue");
		players = new JLabel("     Player 1                 Player 2      ");		
		players.setFont(new Font("Arial",Font.BOLD,20));

		
		spacing1 = new JLabel("");
		spacing2 = new JLabel("                                   ");
		spacing3 = new JLabel("                                    "
				+ "                                                    "
				+ "                 ");
		
		check = new JLabel("");
		
		// add to panels (border)
		north.add(title);
		center.add(choose);
		south.add(cont);
		center.add(spacing1);
		center.add(players);
		center.add(spacing3);
		center.add(select1);
		center.add(spacing2);
		center.add(select2);
		center.add(check);
		
		add(mainPanel,"mainPanel");
		
		cont.addActionListener(this);
		select1.addItemListener(this);
		select2.addItemListener(this);
		
		//GamePanel --------------------------------
		
		gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		pane = new JPanel[ROW][COL];
		
		center2 = new JPanel();
		center2.setLayout(new GridLayout(ROW,COL,2,2));
		
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
			{
				pane[i][j] = new JPanel();
				pane[i][j].setBackground(colorb);
				center2.add(pane[i][j]);
			}
		}
		
		north2 = new JPanel();
		north2.setLayout(new GridLayout(1,COL,2,2));
		
		// slot buttons
		b1 = new JButton("X");
		b2 = new JButton("X");
		b3 = new JButton("X");
		b4 = new JButton("X");
		b5 = new JButton("X");
		b6 = new JButton("X");
		b7 = new JButton("X");

		north2.add(b1);
		north2.add(b2);
		north2.add(b3);
		north2.add(b4);
		north2.add(b5);
		north2.add(b6);
		north2.add(b7);
		
		south2 = new JPanel();
		
		// text announcements
		turn = new JLabel("Player 1 Select Your Slot");
		isFull = new JLabel("");
		south2.add(turn);
		south2.add(isFull);
		
		gamePanel.add(center2, BorderLayout.CENTER);
		gamePanel.add(north2,BorderLayout.NORTH);
		gamePanel.add(south2,BorderLayout.SOUTH);
		
		// jmenubar (will be added in later)
		bar.add(file);
		file.add(exit);
		file.add(reset);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		exit.addActionListener(this);
		reset.addActionListener(this);
		
		add(gamePanel,"gamePanel");
		
		
		
		
	}
	
	
	public void checkForSpot(int col) // checks if the column is full, if not place turn
	{
		boolean full = true; // column default set to full
		for(int i = ROW -1; i >= 0; i-- )
		{
			if(pane[i][col].getBackground() == colorb && full == true)
			{
				full = false; // if there is a spot, it is no longer full
				if(Method.playerTurn(turnNum) == 1)
				{
					pane[i][col].setBackground(color1);
					colorTurn = color1;
					
				}
				else if(Method.playerTurn(turnNum) == 2)
				{
					pane[i][col].setBackground(color2);
					colorTurn = color2;
					
				}

			}
			
		}
		
		checkForWinner(colorTurn);
		
		if(full == true) // if the column is full
		{
			turn.setText("");
			isFull.setText("Column is full. Select another.");
			turnNum --;
		}

	}
	
	public boolean boardIsFull() // checks if the board is full
	{
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
			{
				if(pane[i][j].getBackground() == colorb)
				{
					return false;
				}
			}
		}
		
		
		return true;
	}

	
	public void checkForWinner(Color colorTurn) // checks for a winner
	{	
		boolean isWon = false;
		
		
		// Horizontal Win
		for (int i=0; i<ROW; i++){
	        for (int j=0; j<COL-3; j++)
	        {
	            if (pane[i][j].getBackground() == colorTurn && pane[i][j+1].getBackground()
	            		== colorTurn && pane[i][j+2].getBackground() == 
	            		colorTurn && pane[i][j+3].getBackground() == colorTurn)
	            { 
	               isWon = true;;
	            }
		
	        	}
	        }
		
		// Vertical Win
		for (int i=0; i<ROW-3; i++){
	        for (int j=0; j<COL; j++)
	        {
	            if (pane[i][j].getBackground() == colorTurn && pane[i+1][j].getBackground()
	            		== colorTurn && pane[i+2][j].getBackground()
	            		== colorTurn && pane[i+3][j].getBackground() == colorTurn)
	            { 
	               isWon = true;
	            }
		
	        	}
	        }
		
		// Diagonal Win - top left to bottom right 
		for (int i=3; i<ROW; i++){
	        for (int j=0; j<COL - 3; j++)
	        {
	            if (pane[i][j].getBackground() == colorTurn && pane[i-1][j+1].getBackground()
	            		== colorTurn && pane[i-2][j+2].getBackground()
	            		== colorTurn && pane[i-3][j+3].getBackground() == colorTurn)
	            { 
	              isWon = true;
	            }
		
	        	}
	        }
		// Diagonal Win - top right to bottom left
		for (int i=3; i<ROW; i++){
	        for (int j=3; j<COL; j++)
	        {
	            if (pane[i][j].getBackground() == colorTurn && pane[i-1][j-1].getBackground()
	            		== colorTurn && pane[i-2][j-2].getBackground() ==
	            		colorTurn && pane[i-3][j-3].getBackground() == colorTurn)
	            { 
	              	isWon = true;
	            }
		
	        	}
	        }
		
		
		if(isWon == true)
		{
			turn.setFont(new Font("Arial",Font.BOLD,12));
			
			b1.removeActionListener(this);   // removes button functionality
			b2.removeActionListener(this);
			b3.removeActionListener(this);
			b4.removeActionListener(this);
			b5.removeActionListener(this);
			b6.removeActionListener(this);
			b7.removeActionListener(this);
			reset.removeActionListener(this);
			
			
			if(turnNum % 2 == 1)	// announces winner
			{
			turn.setText("PLAYER ONE WINS!");
			}
			else
			{
			turn.setText("PLAYER TWO WINS!");
			}

		}
		
		if(boardIsFull() == true && isWon == false)	// if no winner and full board
		{
			b1.removeActionListener(this);
			b2.removeActionListener(this);
			b3.removeActionListener(this);
			b4.removeActionListener(this);
			b5.removeActionListener(this);
			b6.removeActionListener(this);
			b7.removeActionListener(this);
			reset.removeActionListener(this);
			
			turn.setText("Game Board Is Full. Game Over");
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if(source == cont) // if the colors are different, continue to next panel
		{
			if(check1 != true || check2 != true)
			{
				check.setText("Please Select a color for BOTH players");
			}
			
			else if(color1 == color2)
			{
				
				check.setText("Colors for each play may NOT be identical");
				
			}
			
			else
			{
				cardLayout.show(getContentPane(), "gamePanel");
				setSize(400,400);
				setJMenuBar(bar);
			}
		}
		else if(source == exit) // exits the program
		{
			System.exit(0);
		}
		
		
		else if(source == reset) // Can reset the board IF the game has not ended
		{
			turnNum = 0;
			for(int i = 0; i < ROW; i++)
			{
				for(int j = 0; j < COL; j++)
				{
					pane[i][j].setBackground(colorb);
				}
			}
			
			Method.announceTurn(turn,turnNum);
			
		}
		
		else if(source == b1)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);			
			checkForSpot(0);
		
		}
		else if(source == b2)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(1);
		}
		else if(source == b3)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(2);
		}
		else if(source == b4)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(3);
		}
		else if(source == b5)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(4);
		}
		else if(source == b6)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(5);
		}
		else if(source == b7)
		{
			isFull.setText("");
			turnNum ++;
			Method.announceTurn(turn,turnNum);
			checkForSpot(6);
		}
		
	}


	@Override
	public void itemStateChanged(ItemEvent ie) {
		
		int stateChange = ie.getStateChange();
		
		Object source2 = ie.getSource();
		
		if(source2 == select1)
		{
			if(stateChange == ItemEvent.SELECTED)
			{
				
				if(select1.getSelectedIndex() == 1)
				{
					color1 = Color.RED;
					check1 = true;
					
				}
				else if(select1.getSelectedIndex() == 2)
				{
					color1 = Color.GREEN;	
					check1 = true;
				}
				
				else if(select1.getSelectedIndex() == 3)
				{
					color1 = Color.BLUE;
					check1 = true;
				}
				else if(select1.getSelectedIndex() == 4)
				{
					color1 = Color.YELLOW;
					check1 = true;
				}
				else if(select1.getSelectedIndex() == 5)
				{
					color1 = Color.PINK;
					check1 = true;
				}
				else if(select1.getSelectedIndex() == 6)
				{
					color1 = Color.BLACK;
					check1 = true;
				}
				else if(select1.getSelectedIndex() == 0) // "select" is chosen
				{
					check1 = false; 
				}
				
			}
			
			
		}
		
		else if(source2 == select2)
		{
			if(stateChange == ItemEvent.SELECTED)
			{
				
				if(select2.getSelectedIndex() == 1)
				{
					color2 = Color.RED;
					check2 = true;
					
				}
				else if(select2.getSelectedIndex() == 2)
				{
					color2 = Color.GREEN;		
					check2 = true;
				}
				
				else if(select2.getSelectedIndex() == 3)
				{
					color2 = Color.BLUE;
					check2 = true;
				}
				else if(select2.getSelectedIndex() == 4)
				{
					color2 = Color.YELLOW;
					check2 = true;
				}
				else if(select2.getSelectedIndex() == 5)
				{
					color2 = Color.PINK;
					check2 = true;
				}
				else if(select2.getSelectedIndex() == 6)
				{
					color2 = Color.BLACK;
					check2 = true;
				}
				else if(select2.getSelectedIndex() == 0) // "select" is chosen
				{
					check2 = false;
				}
				
			}
			
			
		}
		
		
		
	}
	
}


