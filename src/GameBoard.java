//import java.awt.CardLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.KeyStroke;
//import javax.swing.SwingConstants;
//import javax.swing.Timer;
//
//import FlappyBird.HopAction;
//import FlappyBird.Listen;
//import FlappyBird.MenuAction;
//import FlappyBird.ResetAction;
//
//
//public class GameBoard extends JFrame{
//
//	//Create cardLayout so I may switch between 3 main Panels
//	private CardLayout cards;
//	
//	//Create 3 panels used in game and a master panel to contain them all
//	private JPanel masterPanel = new JPanel();
//	private JPanel gameArea = new JPanel();
//	private JPanel mainMenu = new JPanel();
//	private JPanel scoreMenu = new JPanel();
//	
//	//Create main menu buttons
//	private JButton newGameButton;
//	private JButton scoreButton;
//	private JButton mainMenuButton;
//	
//	//Keep a list of pipes and high scores
//	private ArrayList<Pipe> pipes;
//	
//	//FIXING BUGS
//	private boolean mainBuilt;
//		
//	public GameBoard() {
//		//Establish connection to scoreHandler and get all scores from database
//		//flappyScoreHandler = new ScoreHandler();
//		//scoreList = flappyScoreHandler.getScores();
//		
//		//Create card layout to switch between panels 
//		cards = new CardLayout();
//		
//		//Add Panels to master panel
//        masterPanel.setLayout(cards);
//        masterPanel.add(mainMenu, "main");
//        masterPanel.add(gameArea, "game");
//        masterPanel.add(scoreMenu, "score");
//        masterPanel.setVisible(true);
//        
//		add(masterPanel);
//		
//		//Build then display main menu
//		buildMainMenu();
//	}
//
//	//Create Main Menu and all objects that live there
//	private void buildMainMenu(){
//		System.out.println("BUILD Main Menu");
//		
//		mainMenu.setLayout(new GridLayout(3, 1));
//		
//		//Create and add title to top
//		JLabel title = new JLabel("Flappy Bird");
//		title.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		//Create new game button in center and add actionListener
//		newGameButton = new JButton("New Game");
//		newGameButton.addActionListener(new Listen());
//		
//		//Create high score button at bottom and add actionListener
//		scoreButton = new JButton("High Scores");
//		scoreButton.addActionListener(new Listen());
//		
//		//Add object in this order to panel
//		mainMenu.add(title);
//		mainMenu.add(newGameButton);
//		mainMenu.add(scoreButton);
//		
//		//Indicated menu has been built
//		mainBuilt = true;
//	}
//
//	//Create game board including starting pipes and bird, starts timer
//	private void buildGameBoard() {
//		System.out.println("BUILD Game Board");
//		
//		pipes = new ArrayList<Pipe>();
//		
//		//Create 5 pipes every 300 pixels starting at 600
//		for(int i = 0; i < 5; i++){
//			pipes.add(new Pipe(600 + (300*i) ));
//		}
//
//		//Create bird
//		playerCharacter = new Bird();
//		
//    	//Add Key Binding for hopping bird
//    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("2"), "hop");
//    	gameArea.getActionMap().put("hop", new HopAction());
//    	
//    	//Add Key Binding for resetting game
//    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("3"), "reset");
//    	gameArea.getActionMap().put("reset", new ResetAction());
//    	
//    	//Add Key Binding for returning to main menu
//    	gameArea.getInputMap().put(KeyStroke.getKeyStroke("4"), "main");
//    	gameArea.getActionMap().put("main", new MenuAction());
//    	
//    	//Allows panel to listen for keystrokes
//    	gameArea.requestFocus();
//		
//		//Create and start timer
//		timer = new Timer(timerSpeed, new Listen());
//    	timer.start();
//    	
//    	score = 0;
//    	
//    	gameBuilt = true;
//	}
//
//	//Create High Score Menu(IN PROGRESS...)
//	private void buildScoreMenu(){
//	
//}
//
//	//Action Listener for buttons and timer
//		private class Listen implements ActionListener
//		{
//			public void actionPerformed(ActionEvent e)
//		    {
//		    	//If game hasnt been built build it then show game
//		    	if(e.getSource() == newGameButton){
//			    	System.out.println("newgamebutton");
//			    	if(!gameBuilt)
//			    		buildGameBoard();
//			    	cards.show(masterPanel, "game");
//			    	gameArea.requestFocus();
//		    	}
//		    	
//		    	//If score menu hasnt been built build it then show score menu
//		    	else if(e.getSource() == scoreButton){
//			    	System.out.println("scorebutton");
//
//			    	if(!scoreBuilt)
//			    		buildScoreMenu();
//			    	cards.show(masterPanel, "score");
//		    	}
//		    	
//		    	//If menu hasn't been built build it then show menu
//		    	else if(e.getSource() == mainMenuButton){
//			    	System.out.println("mainMenubutton");
//			    	if(!mainBuilt)
//			    		buildMainMenu();
//			    	cards.show(masterPanel, "main");
//		    	}
//		    	
//		    	//On timer tick
//		    	else if(e.getSource() == timer){
//		    		//Paint objects 
//		    		render();
//		    		
//		    		//Update pipes and bird
//		    		Pipe dangerPipe = pipeLogic();
//		    		birdLogic(dangerPipe);
//		    		
//		    		//Increment physics timer
//		    		gameTime++;
//		    		
//		    		//Print console data
//		    		if(showConsole)
//		    			console();
//		    	}
//		    }
//
//			private void console() {
//				//Print Bird Position
//				System.out.println(playerCharacter.toString());
//				
//				//Print Pipe Positions
//				for(int i = 0; i < pipes.size(); i++){
//					System.out.println(pipes.get(i).toString());
//				}
//				
//				System.out.println();	
//			}
//		}
//
//
//}