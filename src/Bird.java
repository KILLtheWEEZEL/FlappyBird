import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bird {
	private int x, y, height, width;
	private double initialVelocity;
	private final double GRAVITY = 9.8;
	private BufferedImage birdImage;
	private String filename = new String("Daniel_Sprite.png");
    
	public Bird() {
		x = 100;
		y = 400;
		height = 25;
		width = 25;
		
		initialVelocity = 0;
	
		try {                
			birdImage = ImageIO.read(new File(filename));
			System.out.println("FOUND file");
		} catch (IOException ex) {
			System.out.println("couldnt find file");
		}
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public BufferedImage getImage(){
		return birdImage;
	}
	
	public int getHeigth(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	public void tick(double t, boolean fall){
		//t is 1/30 of a second (30fps)
		t /=30;
		
		if(fall){
			if(y > 800){
				y = 0;
			}
			else if(y < 0){
				y = 800;
			}
		}
		
		//Position function relative to time
		y += -((int)(initialVelocity * t) - (.5*GRAVITY*t*t));
		
	}
	
	public void hop(double t){
		//Set starting velocity 
		initialVelocity = 6;
		
		//Position function relative to time
		y += -((int)(initialVelocity * t) - (.5*GRAVITY*t*t));
	}
	
	public String toString(){
		return "Bird X: " + getX() + " Y: " + getY();
	}
}
