package main;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui_item.ROLE;

public class GameWindow {
	private StackPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private static final int screenWidth = 1536;
	private static final int screenHeight = 768;
	private Stage menuStage;
	private ImageView role;
	private Image roletest;
	private Canvas canvas;
	private ROLE gameRole;
	private GraphicsContext gc;
	private boolean isDirectionLeft = true; // true = left; false = right
	private int count;
	private int status = 1; //1 = idle , 2 = attack, 3 = skill, 4 = hit
	//image
	private Image idleImage;
	private Image attackImage;
	private Image hitImage;
	private Image skillImage;
	
	//key boolean
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean keyQPressed = false;
	private boolean keyWPressed = false;
	
	
	//test
	private Rectangle roleborder;
	private boolean keyEPressed = false;
	
	public GameWindow() {
		init();
	}
	
	public void createListener() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					upPressed = true;
				}
				if(event.getCode() == KeyCode.DOWN) {
					downPressed = true;
				}
				if(event.getCode() == KeyCode.LEFT) {
					leftPressed = true;
					isDirectionLeft = true;
				}
				if(event.getCode() == KeyCode.RIGHT) {
					rightPressed = true;
					isDirectionLeft = false;
				}
				if(event.getCode() == KeyCode.Q) {
					keyQPressed = true;
				}
				if(event.getCode() == KeyCode.W) {
					keyWPressed = true;
				}
				if(event.getCode() == KeyCode.E) {
					keyEPressed = true;
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					upPressed = false;
				}
				if(event.getCode() == KeyCode.DOWN) {
					downPressed = false;
				}
				if(event.getCode() == KeyCode.LEFT) {
					leftPressed = false;
				}
				if(event.getCode() == KeyCode.RIGHT) {
					rightPressed = false;
				}
			}
		});
	}
	
	private void init() {
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gamePane = new StackPane(canvas);
		gameScene = new Scene(gamePane, screenWidth, screenHeight);
		gameStage = new Stage();
		gameStage.setTitle("Monster Game");
		gameStage.setScene(gameScene);
		createListener();
	}
	
	public void createNewGame(Stage menuStage, ROLE chooseRole ) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		this.gameRole = chooseRole;
		createRole();
		gameStage.show();
		
		Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60), e -> update(gc)));
		loop.setCycleCount(Animation.INDEFINITE);
		loop.play();
	}
	
	private void createRole() {
		idleImage = new Image(gameRole.getRole().idleUrl);
		role = new ImageView(idleImage);
		role.setFitHeight(140);
		role.setFitWidth(140);
		role.setTranslateX(0.0);
		role.setTranslateY(0.0);
		gamePane.getChildren().add(role);
		
		//人物邊界測試
		roleborder = new Rectangle(60,60);
		roleborder.setFill(Color.TRANSPARENT);
		roleborder.setStroke(Color.RED);
		roleborder.setStrokeWidth(2);
		
		gamePane.getChildren().add(roleborder);
	}
	
	private void update(GraphicsContext gc) {
		count++;
		
		if(status == 1) {
			if(upPressed) {
				roleMove(0, -1);
			}
			if(downPressed) {
				roleMove(0, 1);
			}
			if(leftPressed) {
				roleMove(-1, 0);
			}
			if(rightPressed) {
				roleMove(1, 0);
			}
			if(isDirectionLeft) {
				role.setScaleX(1);
			}else {
				role.setScaleX(-1);
			}
			
		}else if(status == 2){
			createAttackSound(count);
			if(count > gameRole.getRole().attackcount ) {
				role.setImage(idleImage);
				status = 1;
			}
		}else if(status == 3) {
			createSkillSound(count);
			if(gameRole.getRole().fireSoundUrl != null) {
				createFireSound(count);
			}
			if(count > gameRole.getRole().skillcount) {
				role.setImage(idleImage);
				status = 1;
			}
		}else if (status ==4 ) {
			createHitSound(count);
			if(count > gameRole.getRole().hitcount) {
				role.setImage(idleImage);
				status = 1;
			}
		}
		if(keyQPressed) {
			attackImage = new Image(gameRole.getRole().attackUrl);
			role.setImage(attackImage);
			status = 2;
			count = 0;
			keyQPressed = false;
		}
		if(keyWPressed) {
			skillImage = new Image(gameRole.getRole().skillUrl);
			role.setImage(skillImage);
			status = 3;
			count = 0;
			keyWPressed = false;
		}
		if(keyEPressed) {
			hitImage = new Image(gameRole.getRole().hitUrl);
			role.setImage(hitImage);
			status = 4;
			count = 0;
			keyEPressed = false;
		}
		
		
		role.setTranslateX(gameRole.getRole().x);
		role.setTranslateY(gameRole.getRole().y);
		roleborder.setTranslateX(gameRole.getRole().x );
		roleborder.setTranslateY(gameRole.getRole().y );
	}
	
	private void createAttackSound(int c) {
		if (c == gameRole.getRole().attackSoundcount) {
			String path = gameRole.getRole().attackSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createSkillSound(int c) {
		if (c == gameRole.getRole().skillSoundcount) {
			String path = gameRole.getRole().skillSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createFireSound(int c) {
		if (c == 115) {
			String path = gameRole.getRole().fireSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createHitSound(int c) {
		if (c == 10) {
			String path = gameRole.getRole().hitSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void roleMove(int x, int y) {
		if(gameRole.getRole().x + gameRole.getRole().speed * x + 30 > screenWidth/2 || 
				gameRole.getRole().x + gameRole.getRole().speed * x -30 < ((-screenWidth)/2)){
		}else {
			gameRole.getRole().x += gameRole.getRole().speed * x;
		}
		if(gameRole.getRole().y + gameRole.getRole().speed * y + 30 > screenHeight/2 || 
				gameRole.getRole().y + gameRole.getRole().speed * y -30 < ((-screenHeight)/2)){
		}else {
			gameRole.getRole().y += gameRole.getRole().speed * y;
		}
	}
}
