package main;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui_item.MenuButton;

public class ViewManager {
	
	//Screen Settings
	private final static int originalTileSize = 16;
	private final static int scale = 4;	
	private final static int Tile = originalTileSize * scale; // 16 * 4 = 64
	private final static int maxScreenCol = 24;
	private final static int maxScreenRow = 12;
	private final static int screenWidth = Tile * maxScreenCol;	// 64 * 24 = 1536
	private final static int screenHeight = Tile * maxScreenRow;	// 64 * 12 = 768
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private Canvas canvas;
	
	public ViewManager() {
		
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, screenWidth, screenHeight);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setTitle("Monster Game");
		createButton("Play");
	}
	
	public Stage getMainStage() {
		
		return mainStage;
	}
	
	private void addMenuButton(MenuButton button) {
		
	}
	
	private void createButton(String buttonName) {
		
		MenuButton button= new MenuButton(buttonName);
		mainPane.getChildren().add(button);
	}
	
	private void createBackground() {
	}
}
