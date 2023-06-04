package entity;

public class Slime extends Monster{
	
	public Slime(int x2, int y2){
		idleUrl = "resource/Monster/slime/idle.gif";
		hitUrl = "resource/Monster/slime/hit.gif";
		attackUrl = "resource/Monster/slime/attack.gif";
		skillUrl = "resource/Monster/slime/attack2.gif";
		attackSoundUrl = "src/resource/Music/katana1.mp3";
		skillSoundUrl = "src/resource/Music/flying_pan.mp3";
		hitSoundUrl = "src/resource/Music/kick1.mp3";
		
		hp = 30;
		speed = 1;
		attack = 20;
		
		attackcount = 100;
		skillcount = 80;
		
		hitcount = 40;
		attackSoundcount = 20;
		skillSoundcount = 25;
		
		fitWidth = 106.5;
		fitHeight = 109.5;
		idleRad = 12;
		moveCount =50;
		canBeAttackDis = 30;
		
		basicCount = 1000;
		
		canUseSkillCount = 0;
		canUseSkill =1;
		
		this.x = x2;
		this.y = y2;
	}
}
