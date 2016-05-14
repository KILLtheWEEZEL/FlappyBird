
public class Score {

	private int score;
	private String name;
	
	public Score(int s, String n) {
		score = s;
		name = new String(n);
	}
	
	public int getScore(){
		return score;
	}
	
	public String getName(){
		return name;
	}
}
