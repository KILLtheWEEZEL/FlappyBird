import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class ScoreHandler {
	private String fileName = new String("scores.txt");
	
	private ArrayList<Score> scores;

	public ScoreHandler() {
		readFile();
		//sort Data before writing data
		writeFile();
	}
	
	private void readFile(){
		System.out.println("Reading file " + fileName);
		try{
			//Find file
			Scanner read = new Scanner (new File(fileName));
			scores = new ArrayList<Score>();
			
			//Until EOF
			while(read.hasNextLine()){
				//Temp values for input
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
		for(int i = 0; i < scores.size(); i++){
			System.out.println(scores.get(i).getScore() + scores.get(i).getName());
		}
	}
	
	public ArrayList<Score> getScores(){
		return scores;
	}
}
