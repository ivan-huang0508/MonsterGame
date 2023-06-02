package main;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui_item.ROLE;

public class GameWindow {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private static final int screenWidth = 1536;
	private static final int screenHeight = 768;
	private Stage menuStage;
	private ImageView role;
	
	public GameWindow() {
		init();
		createListener();
	}
	
	public void createListener() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					
				}
				if(event.getCode() == KeyCode.DOWN) {
					
				}
				if(event.getCode() == KeyCode.LEFT) {
					
				}
				if(event.getCode() == KeyCode.RIGHT) {
					
				}
				if(event.getCode() == KeyCode.Q) {
					
				}
				if(event.getCode() == KeyCode.W) {
					
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					
				}
				if(event.getCode() == KeyCode.DOWN) {
					
				}
				if(event.getCode() == KeyCode.LEFT) {
					
				}
				if(event.getCode() == KeyCode.RIGHT) {
					
				}
				if(event.getCode() == KeyCode.Q) {
					
				}
				if(event.getCode() == KeyCode.W) {
					
				}
			}
		});
	}
	
	private void init() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, screenWidth, screenHeight);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
	
	public void createNewGame(Stage menuStage, ROLE chooseRole ) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createRole(chooseRole);
		gameStage.show();
	}
	
	private void createRole(ROLE chooseRole) {
		role = new ImageView(chooseRole.getUrl());
		role.setScaleX(0.5);
		role.setScaleY(0.5);
		role.setLayoutX(300);
		role.setLayoutY(300);
		gamePane.getChildren().add(role);
	}
}
