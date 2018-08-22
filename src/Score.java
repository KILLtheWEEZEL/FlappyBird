public class Score implements Comparable{

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
	
	@Override
	public int compareTo(Object compareScore) {
		int compareValue=((Score)compareScore).getScore();
		return compareValue-this.score;
    }
    
    public String toString() {
        return "[ Score: " + score + ", Name: " + name + "]";
    }

}
