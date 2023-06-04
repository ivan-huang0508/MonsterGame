package entity;

public class Giant extends Monster{
	
	public Giant (int x2, int y2){
		idleUrl = "resource/Monster/giant/idle.gif";
		hitUrl = "resource/Monster/giant/hit.gif";
		attackUrl = "resource/Monster/giant/attack.gif";
		skillUrl = "resource/Monster/giant/attack2.gif";
		attackSoundUrl = "src/resource/giant/katana1.mp3";
		skillSoundUrl = "src/resource/giant/flying_pan.mp3";
		hitSoundUrl = "src/resource/giant/kick1.mp3";
		
		hp = 100;
		speed = 0.8;
		attack = 30;
		
		attackcount = 70;
		skillcount = 120;
		
		hitcount = 40;
		attackSoundcount = 20;
		skillSoundcount = 25;
		
		fitWidth = 318;
		fitHeight = 285;
		idleRad = 70;
		moveCount =50;
		canBeAttackDis = 50;
		
		basicCount = 1000;
		
		canUseSkillCount = 0;
		canUseSkill =2;
		
		this.x = x2;
		this.y = y2;
	}
}
