package ui_item;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class RolePicker extends VBox{

	private ImageView circleImage;
	private ImageView roleImage;
	private String circleChoose = "resource/Ui/circleChoose.png";
	private String circleNotChoose = "resource/Ui/circleNotChoose.png";
	
	private ROLE role;
	private boolean isCircleChoose;
	
	public RolePicker(ROLE role) {
		
		circleImage = new ImageView(circleNotChoose);
		roleImage = new ImageView(role.getRole().idleUrl);
		this.role = role;
		isCircleChoose = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(roleImage);
	}

	public ROLE getRole(){
		return role;
	}
	
	public boolean getIsCircleChoose() {
		return isCircleChoose;
	}
	
	public void setIsChoose(boolean isCircleChoose) {
		this.isCircleChoose = isCircleChoose;
		String imageToSet = this.isCircleChoose ? circleChoose : circleNotChoose;
		String roleToset = this.isCircleChoose? this.role.getRole().attackUrl : this.role.getRole().idleUrl;
		circleImage.setImage(new Image(imageToSet));
		roleImage.setImage(new Image(roleToset));
	}
}
