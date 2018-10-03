import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FloatyBird extends JFrame{
	
	private static FloatyBird gameBoard;
	private ScoreHandler floatyScoreHandler;
	private Bird playerCharacter;
	
	//Keep a list of pipes and high scores
	private ArrayList<Pipe> pipes;
	private ArrayList<Score> scoreList;
	
	//Create a timer and set staring pace at .5 seconds per tick
	private Timer timer;
	private int timerSpeed = 17;
	private int gameTime;

	//Create main menu buttons
	private JButton newGameButton;
	private JButton scoreButton;
	private JButton mainMenuButton;
	
	//Number of pipes bird has passed
	private int score = 0;
	private int highScore = 0;
	
	//Create 3 panels used in game and a master panel to contain them all
	private JPanel masterPanel = new JPanel();
	private JPanel gameArea = new JPanel();
	private JPanel mainMenuScreen = new JPanel();
	private JPanel scoreMenu = new JPanel();
	
	private Font menuFont = new Font("Impact", Font.PLAIN, 72);
	private CompoundBorder menuBorder = new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 100, 0, Color.GREEN), new EmptyBorder(0, 100, 0, 100)); //add green bottom border for grass and padding
	
	//Create cardLayout so I may switch between 3 main Panels
	private CardLayout cards;
	
	//Physics Debugging
	private boolean collisionDetection = true;//collision detection
	private boolean endlessFalling = true;//when bird falls off bottom of screen he appears at top and vice-versa
	
	//Graphics/Console Debugging
	private boolean visiblePipes = true;
	private boolean visibleBird = true;
	private boolean showConsole = true;
	
	//Toggles when GUI is first built so it isn't built multiple times
	private boolean mainBuilt, gameBuilt, scoreBuilt;
	
	public static void main(String[] args) {
		//Build board and make it visible
		gameBoard = new FloatyBird();
		gameBoard.setVisible(true);
		gameBoard.setSize(1200,800);
		gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Center form on screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - gameBoard.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - gameBoard.getHeight()) / 2);
	    gameBoard.setLocation(x, y);
	}
	
	//Create Main Menu and all objects that live there
	private void buildMainMenu(){
		System.out.println("BUILD Main Menu");
		
		//Create container layout
		mainMenuScreen.setLayout(new GridLayout(2, 1));
		mainMenuScreen.setBackground(Color.CYAN);
		mainMenuScreen.setBorder(menuBorder); //add green bottom border for grass and padding
		
		//Create and add title to top
		JLabel title = new JLabel("Floaty Bird");
		title.setFont(menuFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		mainMenuScreen.add(title);
		
		//Create central content pane
		JPanel mainMenuContent = new JPanel();
		mainMenuContent.setLayout(new GridLayout(1, 2));
		mainMenuContent.setBackground(Color.CYAN);
		
		//Create Image of player on left side
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("bird.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel();
		picLabel.setIcon(new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(400, 280, Image.SCALE_DEFAULT)));

	
		//Create button pane
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));
		
		//Create new game button in center and add actionListener
		newGameButton = new JButton("Play");
		newGameButton.addActionListener(new Listen());
		newGameButton.setBackground(Color.CYAN);
		newGameButton.setOpaque(true);
		newGameButton.setBorderPainted(false);
		newGameButton.setFont(menuFont);
		newGameButton.setForeground(new Color(255, 170, 0));
		
		//Create high score button at bottom and add actionListener
		scoreButton = new JButton("Scores");
		scoreButton.addActionListener(new Listen());
		scoreButton.setBackground(Color.CYAN);
		scoreButton.setOpaque(true);
		scoreButton.setBorderPainted(false);
		scoreButton.setFont(menuFont);
		scoreButton.setForeground(new Color(255, 170, 0));
		
		//Add object in this order to button panel
		buttonPanel.add(newGameButton);
		buttonPanel.add(scoreButton);
		
		//Add objects to mainMenuContent Panel
		mainMenuContent.add(picLabel);
		mainMenuContent.add(buttonPanel);
		
		//Add menu content to screen
		mainMenuScreen.add(mainMenuContent);
		
		//Indicated menu has been built
		mainBuilt = true;
	}

	//Create game board including starting pipes and bird, starts timer
	private void buildGameBoard() {
		System.out.println("BUILD Game Board");
		
		pipes = new ArrayList<Pipe>();
		
		//Create 5 pipes every 300 pixels starting at 600
		for(int i = 0; i < 3; i++){			
			//Create a new pipe with a random gap
			if (i == 0)
				pipes.add(new Pipe(600 + (500*i) ));
			//Create other pipes within a threshold of first gap
			else
				pipes.add(new Pipe(600 +(500*i),pipes.get(i-1).getGapTop()));
			
		}

		//Create bird
		playerCharacter = new Bird();
		
    	//Add Key Binding for hopping bird
    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "hop");
    	gameArea.getActionMap().put("hop", new HopAction());
    	
    	//Add Key Binding for resetting game
    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("R"), "reset");
    	gameArea.getActionMap().put("reset", new ResetAction());
    	
    	//Add Key Binding for returning to main menu
    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "main");
    	gameArea.getActionMap().put("main", new MenuAction());
    	
    	//Allows panel to listen for keystrokes
    	gameArea.requestFocus();
		
		//Create and start timer
		timer = new Timer(timerSpeed, new Listen());
    	timer.start();
    	
    	score = 0;
    	highScore = floatyScoreHandler.getHighScore();
    	
    	gameBuilt = true;
	}

	//Create High Score Menu(IN PROGRESS...)
	private void buildScoreMenu(){
		System.out.println("BUILD Score Menu");
	
		//Create container layout for top and bottom halves of screen
		scoreMenu.setLayout(new GridLayout(2, 1));
		scoreMenu.setBackground(Color.CYAN);
		scoreMenu.setBorder(menuBorder);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(1,2));
		titlePanel.setBackground(Color.CYAN);
		
		//Create and add title to top
		JLabel title = new JLabel("High Scores");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(menuFont);
		titlePanel.add(title);
		
		//Create main menu button at bottom and add actionListener
		mainMenuButton = new JButton("Back to Menu");
		mainMenuButton.addActionListener(new Listen());
		titlePanel.add(mainMenuButton);
		mainMenuButton.setBackground(Color.CYAN);
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);
		mainMenuButton.setFont(menuFont);
		mainMenuButton.setForeground(new Color(255, 170, 0));
		
		scoreMenu.add(titlePanel);
		
		//Create a Panel for Position, Name, and Score
		JPanel scorePanel = new JPanel(new GridLayout(5 , 2));
		scorePanel.setBackground(Color.CYAN);
		JLabel name, score;
		
		//List top five scores 
		for(int i = 0; i < 5; i++){
			
			if(i >= scoreList.size()){
				name = new JLabel("Player " + (i+1));
				score = new JLabel("0");
			}
			else{
				name = new JLabel(scoreList.get(i).getName());
				score = new JLabel(String.valueOf(scoreList.get(i).getScore()));
			}
		
			score.setHorizontalAlignment(SwingConstants.RIGHT);
			
			name.setFont(menuFont);
			score.setFont(menuFont);
			
			//Add labels to scorePanel
			scorePanel.add(name);
			scorePanel.add(score);
			
			scorePanel.setBorder(new EmptyBorder(0,200, 0, 200));
		}
		
		//Add score Panel to scoreMenu
		scoreMenu.add(scorePanel);
		
		scoreBuilt = true;
	}
	
	//Chooses opening panel 
	public FloatyBird(){
		//Establish connection to scoreHandler and get all scores from database
		floatyScoreHandler = new ScoreHandler();
		scoreList = floatyScoreHandler.getScores();
		
		//Create card layout to switch between panels 
		cards = new CardLayout();
		
		//Add Panels to master panel
        masterPanel.setLayout(cards);
        masterPanel.add(mainMenuScreen, "main");
        masterPanel.add(gameArea, "game");
        masterPanel.add(scoreMenu, "score");
        masterPanel.setVisible(true);
        
		add(masterPanel);
		
		//Build then display main menu
		buildMainMenu();
	}
	
	//Creates and display graphics using a buffer strategy every tick
	private void render(){
		BufferStrategy bs = gameBoard.getBufferStrategy();
		
		//If buffer doesn't exist create a triple buffer
		if(bs == null){
			gameBoard.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();

		//Draw gameBoard and paint it yellow
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, gameBoard.getWidth(), gameBoard.getHeight());

		if(visiblePipes){	
			for(int i = 0; i < pipes.size(); i++){
				//Draw Pipe as tall as gameBoard
				g.setColor(Color.GREEN);
				g.fillRect(pipes.get(i).getX(), 0, pipes.get(i).getWidth(), gameBoard.getHeight());

				//Draw Border
				g.setColor(Color.BLACK);
				g.drawRect(pipes.get(i).getX(), 0, pipes.get(i).getWidth()-1, gameBoard.getHeight()-1);
			}
			
			//Build a gap for the bird to fly through
			g.setColor(Color.CYAN);
			for(int i = 0; i < pipes.size(); i++){
				g.fillRect(pipes.get(i).getX(), pipes.get(i).getGapTop(), pipes.get(i).getWidth(), pipes.get(i).getGapHeight());
			}
		}
		
		//Draw Bird
		if(visibleBird)
			g.drawImage(playerCharacter.getImage(), playerCharacter.getX(), playerCharacter.getY(), playerCharacter.getWidth(), playerCharacter.getHeigth(), null);

		//Draw Scores
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans", Font.PLAIN, 20));
		g.drawString("High Score: " + Integer.toString(highScore), gameBoard.getWidth() - 200, gameBoard.getHeight() - 50);
		g.drawString("Score: " + Integer.toString(score), gameBoard.getWidth() - 200, gameBoard.getHeight() - 30);
		
		
		
		//Dispose of old graphics and show display buffer
		g.dispose();
		bs.show();
	}
	
	//Shows user their final score. If they are in the top 5 they will get to enter their name 
	private void dead(){
		System.out.println("YOU DIED!");
		
		timer.stop();
		
		int rank = 1;
		
		//Find player rank
		for(Score s: scoreList) {
			if (score > s.getScore()) {
				break;
			}
			rank++;
		}
		
		String playerName = (String)JOptionPane.showInputDialog(gameBoard,
                "You are rank " + rank + " with a score of " + score, "Enter name here");
		
		//DONT ACCEPT NULL VALUE
		try {
			System.out.println("Score saved: " + score + ", " + playerName);
			scoreList.add(new Score(score, playerName));
			floatyScoreHandler.writeFile();
		}catch (Exception NullPointerException) {
			System.out.println("Score not saved");
		}
	}
	
	//Calculate if bird has collided with obstacle if not tick bird
	private void birdLogic(Pipe p){
	
		//If collision Detection has been turned on
		if(collisionDetection){
			
			//There is a pipe in collision range 
			if(p != null){
				//Check if bird hit pipe
	    		if(playerCharacter.getY() > (p.getGapTop() + p.getGapHeight()) || playerCharacter.getY() < p.getGapTop())
	    			dead();
	    		
	    		//Else tick bird
	    		else
	    			playerCharacter.tick(gameTime, endlessFalling);
			}
			
			//Bird is flying in open air
			else{
				//Code block unused unless endless falling is toggled off
				if(!endlessFalling){
		    		//Check if bird hit ground or ceiling
		    		if(playerCharacter.getY() > gameBoard.getHeight() + 25 || playerCharacter.getY() <= 25)
		    			dead();
		    		
		    		//Else tick bird
		    		else
		    			playerCharacter.tick(gameTime, endlessFalling);
				}
				//Else tick bird
				else
					playerCharacter.tick(gameTime, endlessFalling);
			}
		}
		//Else tick bird
		else
			playerCharacter.tick(gameTime, endlessFalling);
	}
	
	//Calculate if pipe is in a dangerous location or off screen the tick pipes
	private Pipe pipeLogic(){
		Pipe p = null;
		
		//Tick pipes
		for(int i = 0; i < pipes.size(); i++){
			//Index of previous pipe if first index wrap to end
			int previousPipe = i-1;
			if (previousPipe == -1) {
				previousPipe = pipes.size()-1;
			}
			pipes.get(i).tick();
			
			//If pipe has gone off screen to the left send it back to the right
			if(pipes.get(i).getX() < -100){
				pipes.get(i).sendToEnd(pipes.get(previousPipe).getGapTop());
			}
			//if bird is between left and right bounds of pipe flag pipe as a danger
			else if(pipes.get(i).getX() <= playerCharacter.getX() && (pipes.get(i).getX() + pipes.get(i).getWidth()) > playerCharacter.getX()){
				p = new Pipe(pipes.get(i));
			}
			//if bird has just passed pipe increment score
			else if((pipes.get(i).getX() + pipes.get(i).getWidth()) == playerCharacter.getX())
				score++;
    	}
		
		//Return either a null pipe or a dangerous pipe
		return p;
	}
	
	//Action Listener for buttons and timer
	private class Listen implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
	    {
	    	//If game hasn't been built build it then show game
	    	if(e.getSource() == newGameButton){
		    	System.out.println("newgamebutton");
		    	if(!gameBuilt)
		    		buildGameBoard();
		    	cards.show(masterPanel, "game");
		    	gameArea.requestFocus();
	    	}
	    	
	    	//If score menu hasn't been built build it then show score menu
	    	else if(e.getSource() == scoreButton){
		    	System.out.println("scorebutton");

		    	if(!scoreBuilt)
		    		buildScoreMenu();
		    	cards.show(masterPanel, "score");
	    	}
	    	
	    	//If menu hasn't been built build it then show menu
	    	else if(e.getSource() == mainMenuButton){
		    	System.out.println("mainMenubutton");
		    	if(!mainBuilt)
		    		buildMainMenu();
		    	cards.show(masterPanel, "main");
	    	}
	    	
	    	//On timer tick
	    	else if(e.getSource() == timer){
	    		//Paint objects 
	    		render();
	    		
	    		//Update pipes and bird
	    		Pipe dangerPipe = pipeLogic();
	    		birdLogic(dangerPipe);
	    		
	    		//Increment physics timer
	    		gameTime++;
	    		
	    		//Print console data
	    		if(showConsole)
	    			console();
	    	}
	    }

		private void console() {
			//Print Bird Position
			System.out.println(playerCharacter.toString());
			
			//Print Pipe Positions
			for(int i = 0; i < pipes.size(); i++){
				System.out.println(pipes.get(i).toString());
			}
			
			System.out.println();	
		}
	}

	//When user pressed a key bird hops
	public class HopAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(e.toString());
			gameTime = 1;
			playerCharacter.hop(gameTime);	
		}
	}
	
	//When user pressed a key game resets
	public class ResetAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("reset game");
			gameTime = 0;
			buildGameBoard();
		}
	}
	
	//When user presses a key return to menu
	public class MenuAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Main Menu");
			gameTime = 0;
			timer.restart();
			timer.stop();
			gameBuilt = false;
			cards.show(masterPanel, "main");
		}
	}
}

