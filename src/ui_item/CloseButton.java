package ui_item;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class CloseButton extends Button{
	
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('resource/Ui/buttonSquare_blue_pressed.png');";
	private final String BUTTON_RELEASED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('resource/Ui/buttonSquare_blue.png');";
	private String path;
	private boolean isHidden;
	private MenuSubscene menuSubscene;
	
	public CloseButton(MenuSubscene menuSubscene) {
		Image image = new Image("resource/Ui/iconCross_grey.png");
		ImageView imageview = new ImageView(image);
		imageview.setScaleX(2);
		imageview.setScaleY(2);
		setGraphic(imageview);
		setPrefWidth(45);
		setPrefHeight(49);
		setLayoutX(10000);
		setLayoutY(10000);
		setStyle(BUTTON_RELEASED_STYLE);
		this.menuSubscene = menuSubscene;
		buttonListeners();
	}
	
	private void setButtonPressedStyle() {
		
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);
	}
	
	private void setButtonReleasedStyle() {
		
		setStyle(BUTTON_RELEASED_STYLE);
		setPrefHeight(49);
		setLayoutY(getLayoutY() - 4);
	}
	
	private void buttonListeners() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
					path = "src/resource/Music/Click.mp3";
					createSound(path);
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				path = "src/resource/Music/Switch.mp3";
				createSound(path);
			}
			
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
			}
			
		});
		
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setHidden(true);
			}
		});
	}
	
	private void createSound(String path) {
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		player.play();
		player.setCycleCount(1);
	}
	
	public boolean getisHidden() {
		
		return isHidden;
	}
	
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		if(this.isHidden == true) {
			setLayoutX(10000);
			setLayoutY(10000);
			this.menuSubscene.setLayoutX(10000);
			this.menuSubscene.setLayoutY(10000);
		}
	}
	
	public void setShow() {
		setLayoutX(1043);
		setLayoutY(237);
		this.menuSubscene.setLayoutX(480);
		this.menuSubscene.setLayoutY(250);
	}
	
}
