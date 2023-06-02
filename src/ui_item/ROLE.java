package ui_item;

import entity.Warrior;

public enum ROLE {

	
	WARRIOR{
		Warrior role = new Warrior();
	},
	WIZARD("resource/Player/Wizard/idle.gif");
	
	String urlRole;
	private ROLE(String urlRole) {
		this.urlRole = urlRole;
	}
	
	public String getUrl() {
		return this.urlRole;
	}
}
