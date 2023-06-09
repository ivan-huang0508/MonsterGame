package entity;

public class Warrior extends Player{
	public Warrior() {
		idleUrl = "resource/Player/Warrior/idle.gif";
		hitUrl = "resource/Player/Warrior/hit.gif";
		attackUrl = "resource/Player/Warrior/attack.gif";
		skillUrl = "resource/Player/Warrior/guard.gif";
		facesetUrl = "resource/Player/Warrior/faceset.png";
		attackSoundUrl = "src/resource/Music/katana1.mp3";
		skillSoundUrl = "src/resource/Music/flying_pan.mp3";
		hitSoundUrl = "src/resource/Music/kick1.mp3";
		hp = 100;
		speed = 5;
		attackcount = 57;
		skillcount = 60;
		hitcount = 20;
		attack = 20;
		attackSoundcount = 20;
		skillSoundcount = 25;
		attackRad = 55;
		score =0;
	}

	@Override
	public void skill() {
		invincibleCount = 0;
		
	}
}
