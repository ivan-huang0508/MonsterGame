package ui_item;

import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Text;

public class MenuSubscene extends SubScene {

	private static final double screenWidth = 600;
	private static final double screenHeight = 400;
	private final String FONT_PATH = "src/resource/Ui/font.ttf";
	private final String BACKGROUND_IMAGE = "resource/Ui/panel_brown.png";
	
	public MenuSubscene() {
		
		super(new AnchorPane(), screenWidth, screenHeight);
		prefWidth(screenWidth);
		prefHeight(screenHeight);
		Image backgroundImage = new Image(BACKGROUND_IMAGE, screenWidth, screenHeight, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(background));
		setLayoutX(10000);
		setLayoutY(10000);
		setEffect(new DropShadow());	
	}
	
	public MenuSubscene(Text text) {
		
		super(new AnchorPane(), screenWidth, screenHeight);
		prefWidth(screenWidth);
		prefHeight(screenHeight);
		Image backgroundImage = new Image(BACKGROUND_IMAGE, screenWidth, screenHeight, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
	    root2.setTopAnchor(text, 30.0);
	    root2.setLeftAnchor(text, 50.0);
		root2.getChildren().add(text);
		
		root2.setBackground(new Background(background));
		setLayoutX(10000);
		setLayoutY(10000);
		setEffect(new DropShadow());	
	}
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
}
