package entity;

public class Monster extends Entity{
	public int isDirectionLeft = -1;
	public double fitWidth;
	public double fitHeight;
	public double idleRad;
	public int moveCount;
	public int canBeAttackDis;
	public int status = 1;
	public int canAttackCount;
	public int canSkillCount;
	public int canUseSkill;
	public int canUseSkillCount;
	public int basicCount = 100;
	
	public int delayTimeCount = 0;
	public int attackTimeCount;
	public int skillTimeCount;
	public int skillRad;
	public int attackRad;
	public int delayStatus = 0; //0 idle, 1 attack, 2 skill;
	public int score;
}
