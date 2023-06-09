package entity;

public class Fireball extends Entity{
	public String Url = "resource/Player/Wizard/fireball.gif";
	public String Fire = "resource/Player/Wizard/fire.png";
	public double speed = 5.0;
	public int rad = 50;
	public int movecount = 0;
	public int urlcount = 0;
	public int fitWidth = 79;
	public int fitHeight = 46;
	public int direction;
	public int lifecount = 0;
	public Fireball(int x, int y){
		this.x = x;
		this.y = y;
	}
}
