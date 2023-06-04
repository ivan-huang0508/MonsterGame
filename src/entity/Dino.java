package entity;

public class Dino extends Monster{
	
	public Dino (int x2, int y2){
		idleUrl = "resource/Monster/dino/idle.gif";
		hitUrl = "resource/Monster/dino/hit.gif";
		attackUrl = "resource/Monster/dino/attack.gif";
		skillUrl = "resource/Monster/dino/attack2.gif";
		attackSoundUrl = "src/resource/dino/katana1.mp3";
		skillSoundUrl = "src/resource/dino/flying_pan.mp3";
		hitSoundUrl = "src/resource/dino/kick1.mp3";
		
		hp = 30;
		speed = 2;
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
