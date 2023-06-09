package entity;

public class Dino extends Monster{
	
	public Dino (int x2, int y2){
		idleUrl = "resource/Monster/dino/idle.gif";
		hitUrl = "resource/Monster/dino/hit.gif";
		attackUrl = "resource/Monster/dino/attack.gif";
		skillUrl = "resource/Monster/dino/attack2.gif";
		
		hp = 50;
		speed = 5;
		attack = 15;
		
		attackcount = 80;
		skillcount = 40;
		
		hitcount = 40;
		attackSoundcount = 20;
		skillSoundcount = 25;
		
		fitWidth = 106.5;
		fitHeight = 109.5;
		idleRad = 20;
		moveCount =50;
		canBeAttackDis = 30;
		
		basicCount = 1000;
		
		canUseSkillCount = 0;
		canUseSkill =1;
		
		delayTimeCount = 0;
		attackTimeCount = 50;
		attackRad = 20;
		skillRad = 40;
		skillTimeCount = 28;
		delayStatus = 0;
		score = 200;
		this.x = x2;
		this.y = y2;
	}
}
