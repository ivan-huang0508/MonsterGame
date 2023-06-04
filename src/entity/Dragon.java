package entity;

public class Dragon extends Monster{
	
	public Dragon (int x2, int y2){
		idleUrl = "resource/Monster/dragon/idle.gif";
		hitUrl = "resource/Monster/dragon/hit.gif";
		attackUrl = "resource/Monster/dragon/attack.gif";
		skillUrl = "resource/Monster/dragon/attack2.gif";
		attackSoundUrl = "src/resource/dragon/katana1.mp3";
		skillSoundUrl = "src/resource/dragon/flying_pan.mp3";
		hitSoundUrl = "src/resource/dragon/kick1.mp3";
		
		hp = 100;
		speed = 20;
		attack = 30;
		
		attackcount = 100;
		skillcount = 80;
		
		hitcount = 40;
		attackSoundcount = 20;
		skillSoundcount = 25;
		
		fitWidth = 344;
		fitHeight = 341;
		idleRad = 70;
		moveCount =50;
		canBeAttackDis = 100;
		
		basicCount = 1000;
		
		canUseSkillCount = 0;
		canUseSkill =1;
		
		this.x = x2;
		this.y = y2;
	}
}
