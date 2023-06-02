package ui_item;

import entity.Player;
import entity.Warrior;
import entity.Wizard;

public enum ROLE {

	
	WARRIOR(new Warrior()),
	WIZARD(new Wizard());
	
	private Player role;
	
	private ROLE(Player role) {
		this.role  = role;
	}

	public Player getRole() {
		return role;
	}
}
