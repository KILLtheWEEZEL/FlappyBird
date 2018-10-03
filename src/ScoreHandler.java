import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ScoreHandler {
	private String fileName = new String("scores.txt");
	
	private ArrayList<Score> scores;

	public ScoreHandler() {
		readFile();
		sortScores();
	}
	
	private void readFile(){
		System.out.println("Reading file " + fileName);
		scores = new ArrayList<Score>();
		
		try{
			//Find file
			Scanner read = new Scanner (new File(fileName));
			
			//Until EOF
			while(read.hasNextLine()){
				//Temporary values for input
				String[] lineValue = new String[2];
				int score;
				String name;
				
				//Take the read line and split it into two string separated by a comma
				lineValue = read.nextLine().split(",");
				score = Integer.parseInt(lineValue[0]);
				name = lineValue[1];
				
				//Place score and name into map
				scores.add(new Score(score, name));
			}
			
			//Close file
			read.close();
	        
		}catch(FileNotFoundException ex){
			System.out.println("Unable to open file " + fileName);
		}
		
		printScores();
	}
	
	public void writeFile(){
		System.out.println("writing file");
		
		sortScores();
		
		//Iterate through arraylist, format and print data to file
		try {
			PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName));
			
			for(int i = 0; i < scores.size(); i++){
				outputStream.println(scores.get(i).getScore() + "," + scores.get(i).getName());
			}
			
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("error writing file");
		}
	}
	
	private void printScores(){
		System.out.println("Sort scores");
		
		//Loop through all scores and print using toString
		for(Score str: scores){
			System.out.println(str);
		}
		
		System.out.println();
		
	}
	
	public ArrayList<Score> getScores(){
		return scores;
	}
	
	public int getHighScore() {
		sortScores();
		return scores.get(0).getScore();
	}
	
	private void sortScores()
	{
		//Sort scores using custom compareTo
		Collections.sort(scores);
		
		//Print scores to console for debugging
		printScores();
	
	}
}
