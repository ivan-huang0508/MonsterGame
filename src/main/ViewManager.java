package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import ui_item.CloseButton;
import ui_item.InfoLabel;
import ui_item.MenuButton;
import ui_item.MenuSubscene;
import ui_item.ROLE;
import ui_item.RolePicker;

public class ViewManager {
	
	//Screen Settings
	private final static int originalTileSize = 16;
	private final static int scale = 4;	
	private final static int Tile = originalTileSize * scale; // 16 * 4 = 64
	private final static int maxScreenCol = 24;
	private final static int maxScreenRow = 12;
	private final static int screenWidth = Tile * maxScreenCol;	// 64 * 24 = 1536
	private final static int screenHeight = Tile * maxScreenRow;	// 64 * 12 = 768
	private final static int MENU_BUTTONS_START_X = 680;
	private final static int MENU_BUTTONS_START_Y = 300;
	private final static int LOGO_START_X = 480;
	private final static int LOGO_START_Y = 150;
	private MenuSubscene playSubscene;
	private MenuSubscene helpSubscene;
	private CloseButton playSubsceneCloseButton;
	private CloseButton helpSubsceneCloseButton;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private List<MenuButton> menuButtonList;
	private List<RolePicker> roleList;
	private ROLE chooseRole;
	private MediaPlayer backgroundMusic;
	
	public ViewManager() {
		
		menuButtonList = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, screenWidth, screenHeight);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setTitle("Monster Game");
		createButtons();
		createBackground();
		createLogo();
		createSubscenes();
		createBackgroundMusic();
	}
	
	public Stage getMainStage() {
		
		return mainStage;
	}
	
	private void addMenuButton(MenuButton button) {
		
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtonList.size()* 100);
		menuButtonList.add(button);
		mainPane.getChildren().add(button);
	}
	
	private MenuSubscene createSubscene() {
		MenuSubscene subScene = new MenuSubscene();
		mainPane.getChildren().add(subScene);
		return subScene;
	}
	
	private void createButtons() {
		
		createPlayButton();
		createHelpButton();
		createExitButton();
	}
	
	private CloseButton createCloseButton(MenuSubscene subscene) {
		CloseButton button = new CloseButton(subscene);
		mainPane.getChildren().add(button);
		return button;
	}
	
	private void createSubscenes() {
		createPlaySubscene();
		createHelpSubscene();
	}
	
	private void createPlaySubscene() {
		playSubscene = createSubscene();
		playSubsceneCloseButton = createCloseButton(playSubscene);
		InfoLabel chooseRoleLabel = new InfoLabel("CHOOSE THE ROLE");
		chooseRoleLabel.setLayoutX(115);
		chooseRoleLabel.setLayoutY(40);
		playSubscene.getPane().getChildren().add(chooseRoleLabel);
		playSubscene.getPane().getChildren().add(createRoleToChoose());
		playSubscene.getPane().getChildren().add(createStartButton());
	}
	
	private MenuButton createStartButton() {
		MenuButton startButton = new MenuButton("Go");
		startButton.setLayoutX(213);
		startButton.setLayoutY(330);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (chooseRole != null) {
					GameWindow gameWindow = new GameWindow();
					gameWindow.createNewGame(mainStage, chooseRole);
					backgroundMusic.stop();
				}
			}
		});
		
		return startButton;
	}
	
	private HBox createRoleToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		roleList = new ArrayList<>();
		for(ROLE role: ROLE.values()) {
			RolePicker roleToPick = new RolePicker(role);
			roleList.add(roleToPick);
			box.getChildren().add(roleToPick);
			roleToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					for( RolePicker role : roleList) {
						role.setIsChoose(false);
					}
					roleToPick.setIsChoose(true);
					chooseRole = roleToPick.getRole();
				}
			});
		}
		box.setLayoutX(100);
		box.setLayoutY(115);
		return box;
	}
	
	private void createHelpSubscene() {
		helpSubscene = createSubscene();
		helpSubsceneCloseButton = createCloseButton(helpSubscene);
	}
	
	private void createPlayButton() {
		
		MenuButton button= new MenuButton("PLAY");
		addMenuButton(button);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playSubsceneCloseButton.setShow();
				
			}
		});
	}
	
	private void createHelpButton() {
		
		MenuButton button= new MenuButton("HELP");
		addMenuButton(button);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				helpSubsceneCloseButton.setShow();
			}
		});
	}
	
	private void createExitButton() {
		
		MenuButton button= new MenuButton("EXIT");
		addMenuButton(button);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
	}
	
	private void createBackground() {
		
		Image backgroundImage = new Image("resource/Background/background.png",screenWidth, screenHeight, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		//monstergame
		createLogoLetter(0, 60, 0);	//m
		createLogoLetter(40, 60, 67);	//o
		createLogoLetter(21, 60, 126);	//n
		createLogoLetter(120, 60, 175);	//s
		createLogoLetter(180, 100, 229);	//t
		createLogoLetter(140, 40, 281);	//e
		createLogoLetter(140, 100, 334);	//r
		createLogoLetter(220, 80, 389);	//g
		createLogoLetter(100, 80, 445);	//a
		createLogoLetter(0, 60, 512);	//m
		createLogoLetter(140, 40, 579);	//e
	}
	
	private void createLogoLetter(int letter_x, int letter_y, int letter_space) {
		
		ImageView letter = new ImageView("resource/Ui/font-20x20.png");
		letter.setViewport(new javafx.geometry.Rectangle2D( letter_x, letter_y, 21, 19));
		letter.setScaleX(4);
		letter.setScaleY(4);
		letter.setLayoutX(LOGO_START_X + letter_space);
		letter.setLayoutY(LOGO_START_Y);
		mainPane.getChildren().add(letter);
	}
	
	private void createBackgroundMusic() {
		
		String path = "src/resource/Music/BackgroundMusic.mp3";
		Media media = new Media(new File(path).toURI().toString());
		backgroundMusic = new MediaPlayer(media);
		backgroundMusic.setAutoPlay(true);
		backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
