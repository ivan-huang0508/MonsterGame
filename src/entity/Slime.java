package entity;

public class Slime extends Monster{
	
	public Slime(int x2, int y2){
		idleUrl = "resource/Monster/slime/idle.gif";
		hitUrl = "resource/Monster/slime/hit.gif";
		attackUrl = "resource/Monster/slime/attack.gif";
		skillUrl = "resource/Monster/slime/attack2.gif";
		
		hp = 30;
		speed = 2;
		attack = 10;
		
		attackcount = 40;
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
		
		delayTimeCount = 0;
		attackTimeCount = 25;
		attackRad = 20;
		skillRad = 40;
		skillTimeCount = 40;
		delayStatus = 0;
		score = 100;
		this.x = x2;
		this.y = y2;
	}
}
