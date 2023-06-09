package entity;

public abstract class Player extends Entity{
	public String facesetUrl;
	public int idleRad = 28;
	public int attackRad;
	public int invincibleCount;
	public int score;
	public abstract void skill();
}
