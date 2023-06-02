package entity;

public class Wizard extends Player{
	public Wizard() {
		idleUrl = "resource/Player/Wizard/idle.gif";
		hitUrl = "resource/Player/Wizard/hit.gif";
		attackUrl = "resource/Player/Wizard/attack.gif";
		skillUrl = "resource/Player/Wizard/attack2.gif";
		facesetUrl = "resource/Player/Wizard/faceset.png";
		attackSoundUrl = "src/resource/Music/swing1.mp3";
		skillSoundUrl = "src/resource/Music/fireball.mp3";
		fireSoundUrl = "src/resource/Music/launcher.mp3";
		hitSoundUrl = "src/resource/Music/kick1.mp3";
		attackSoundcount = 10;
		skillSoundcount = 5;
		hp = 100;
		speed = 6;
		attackcount = 53;
		skillcount = 135;
		hitcount = 20;
		attack = 10;
	}
}
