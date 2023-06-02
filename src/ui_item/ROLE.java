package ui_item;

public enum ROLE {
	
	
	WARRIOR("resource/Player/Warrior/idle.gif"),
	WIZARD("resource/Player/Wizard/idle.gif");
	
	String urlRole;
	
	private ROLE(String urlRole) {
		this.urlRole = urlRole;
	}
	
	public String getUrl() {
		return this.urlRole;
	}
}
