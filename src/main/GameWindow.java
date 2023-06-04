package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Dino;
import entity.Dragon;
import entity.Giant;
import entity.Monster;
import entity.Slime;
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
import javafx.scene.shape.Circle;
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
	private Canvas canvas;
	private ROLE gameRole;
	private GraphicsContext gc;
	private int isDirectionLeft = -1; // -1 = left; 1 = right
	private int count;
	private int status = 1; //1 = idle , 2 = attack, 3 = skill, 4 = hit

	private int random_x;
	private int random_y;
	private Monster newMonster;
	private Random random = new Random();
	private int createSlimeCount  = 10000;
	
	private List<Monster> monster = new ArrayList<>();
	private List<ImageView> monsterView = new ArrayList<>();
	
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
	private Circle roleborder;
	private boolean keyEPressed = false;
	private List<Circle> circles = new ArrayList<>();
	private List<Circle> attacks = new ArrayList<>();
	private Circle tmpC;
	
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
					isDirectionLeft = -1;
				}
				if(event.getCode() == KeyCode.RIGHT) {
					rightPressed = true;
					isDirectionLeft = 1;
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
		roleborder = new Circle();
		roleborder.setRadius(gameRole.getRole().idleRad);
		roleborder.setFill(Color.TRANSPARENT);
		roleborder.setStroke(Color.RED);
		roleborder.setStrokeWidth(2);
		
		gamePane.getChildren().add(roleborder);
	}
	
	private void update(GraphicsContext gc) {
		role.toFront();
		count++;
		createSlimeCount++;
		gameRole.getRole().invincibleCount++;
		
		createMonster(createSlimeCount);
		
		for(int i = 0; i< monster.size(); i++) {
			monster.get(i).basicCount++;
			if(monster.get(i).status == 1) {
				//attack
				continue;
			}else if (monster.get(i).status == 2) {
				if(monster.get(i).basicCount > monster.get(i).attackcount) {
					monsterView.get(i).setImage(new Image(monster.get(i).idleUrl));
					monster.get(i).status = 1;
				}
			}else if (monster.get(i).status == 3) {
				if(monster.get(i).basicCount > monster.get(i).skillcount) {
					monsterView.get(i).setImage(new Image(monster.get(i).idleUrl));
					monster.get(i).status = 1;
				}
				
			}else if (monster.get(i).status == 4) {
				if(monster.get(i).basicCount > monster.get(i).hitcount) {
					monsterView.get(i).setImage(new Image(monster.get(i).idleUrl));
					monster.get(i).status = 1;
				}
			}
		}
		
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
			if(isDirectionLeft == -1) {
				role.setScaleX(1);
			}else {
				role.setScaleX(-1);
			}
			
		}else if(status == 2){
			createAttackSound(count);
			if(count > gameRole.getRole().attackcount ) {
				role.setImage(idleImage);
				status = 1;
				
				//test
				gamePane.getChildren().remove(tmpC);
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
		}else if (status == 4 ) {
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
			roleAttack();
			
			//test
			tmpC = new Circle(gameRole.getRole().attackRad);
			tmpC.setTranslateX(gameRole.getRole().x + isDirectionLeft * 35);
			tmpC.setTranslateY(gameRole.getRole().y);
			tmpC.setFill(Color.TRANSPARENT);
			tmpC.setStroke(Color.BLACK);
			tmpC.setStrokeWidth(2);
			gamePane.getChildren().add(tmpC);
			
		}
		if(keyWPressed) {
			skillImage = new Image(gameRole.getRole().skillUrl);
			role.setImage(skillImage);
			status = 3;
			count = 0;
			gameRole.getRole().skill();
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
		
		
		
		for(int i = 0; i<monster.size(); i++) {
			monsterMove(monster.get(i), monsterView.get(i), circles.get(i), attacks.get(i));
			if(monster.get(i).hp<=0) {
				gamePane.getChildren().remove(monsterView.get(i));
				gamePane.getChildren().remove(circles.get(i));
				gamePane.getChildren().remove(attacks.get(i));
				monster.remove(i);
				monsterView.remove(i);
				
				
				//test
				attacks.remove(i);
				circles.remove(i);
			}
		}
		
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
		if(gameRole.getRole().x + gameRole.getRole().speed * x + gameRole.getRole().idleRad > screenWidth/2 || 
				gameRole.getRole().x + gameRole.getRole().speed * x - gameRole.getRole().idleRad < ((-screenWidth)/2)){
			gameRole.getRole().x = gameRole.getRole().x;
		}else {
			gameRole.getRole().x += gameRole.getRole().speed * x;
		}
		if(gameRole.getRole().y + gameRole.getRole().speed * y + gameRole.getRole().idleRad > screenHeight/2 || 
				gameRole.getRole().y + gameRole.getRole().speed * y - gameRole.getRole().idleRad < ((-screenHeight)/2)){
			gameRole.getRole().y = gameRole.getRole().y;
		}else {
			gameRole.getRole().y += gameRole.getRole().speed * y;
		}
	}
	
	private void createMonster(int count) {
		if(count > 100) {
			createSlimeCount = 0;
			random_x = random.nextInt(1536) - 768;
			random_y = random.nextInt(768) - 384;
			newMonster = new Giant(random_x, random_y);
			this.monster.add(newMonster);
			
			Image tmp = new Image(newMonster.idleUrl);
			ImageView tmpV = new ImageView(tmp);
			tmpV.setFitHeight(newMonster.fitHeight);
			tmpV.setFitWidth(newMonster.fitWidth);
			this.monsterView.add(tmpV);
			gamePane.getChildren().add(tmpV);
			
			//
			Circle tmpR = new Circle(newMonster.idleRad);
			tmpR.setTranslateX(random_x);
			tmpR.setTranslateY(random_y);
			tmpR.setFill(Color.TRANSPARENT);
			tmpR.setStroke(Color.RED);
			tmpR.setStrokeWidth(2);
			this.circles.add(tmpR);
			gamePane.getChildren().add(tmpR);
			Circle beAttack = new Circle(2);
			beAttack.setTranslateX(random_x + newMonster.isDirectionLeft * newMonster.canBeAttackDis);
			beAttack.setTranslateY(random_y);
			this.attacks.add(beAttack);
			gamePane.getChildren().add(beAttack);
		}
	}
	
	private void monsterMove(Monster tmp_monster, ImageView tmp_imageView, Circle tmp_circle, Circle tmp_beattack) {
		if(tmp_monster.status == 1) {
			tmp_monster.moveCount++;
			double distance = Math.sqrt(Math.pow(gameRole.getRole().x - tmp_monster.x, 2) + Math.pow(gameRole.getRole().y - tmp_monster.y, 2));
			if (tmp_monster.x > gameRole.getRole().x) {
				tmp_monster.isDirectionLeft = -1;
			}else {
				tmp_monster.isDirectionLeft = 1;
			}
			if (tmp_monster.isDirectionLeft == -1) {
				tmp_imageView.setScaleX(-1);
			}else {
				tmp_imageView.setScaleX(1);
			}
			if (distance <= (gameRole.getRole().idleRad + tmp_monster.idleRad)) {
				if(gameRole.getRole().invincibleCount>100) {
					if(tmp_monster.canUseSkillCount >= tmp_monster.canUseSkill) {
						//monster skill
						tmp_imageView.setImage(new Image(tmp_monster.skillUrl));
						gameRole.getRole().hp -= tmp_monster.attack * 2;
						gameRole.getRole().invincibleCount = 0;
						hitImage = new Image(gameRole.getRole().hitUrl);
						role.setImage(hitImage);
						status = 4;
						count = 0;
						tmp_monster.canUseSkillCount = 0;
						System.out.println("skill");
						tmp_monster.basicCount = 0;
						tmp_monster.status = 3;
					}else {
						//monster attack
						tmp_imageView.setImage(new Image(tmp_monster.attackUrl));
						gameRole.getRole().hp -= tmp_monster.attack;
						gameRole.getRole().invincibleCount = 0;
						hitImage = new Image(gameRole.getRole().hitUrl);
						role.setImage(hitImage);
						status = 4;
						count = 0;
						tmp_monster.canUseSkillCount++;
						System.out.println("attack");
						tmp_monster.basicCount = 0;
						tmp_monster.status = 2;
					}
					
				}
			}else	{
				if(tmp_monster.moveCount >= 4) {
					double angle = Math.atan2(gameRole.getRole().y - tmp_monster.y, gameRole.getRole().x - tmp_monster.x);
					tmp_monster.x += (Math.cos(angle)* tmp_monster.speed);
					tmp_monster.y += (Math.sin(angle)* tmp_monster.speed);
					tmp_imageView.setTranslateX(tmp_monster.x);
					tmp_imageView.setTranslateY(tmp_monster.y);
					
					tmp_circle.setTranslateX(tmp_monster.x);
					tmp_circle.setTranslateY(tmp_monster.y);
					tmp_beattack.setTranslateX(tmp_monster.x + tmp_monster.isDirectionLeft * tmp_monster.canBeAttackDis);
					tmp_beattack.setTranslateY(tmp_monster.y);
					
					tmp_monster.moveCount = 0;
				}
				
			}
		}
	}
	private void roleAttack() {
		for(int i =0 ; i < monster.size(); i++) {
			double distance = Math.sqrt(Math.pow((gameRole.getRole().x + (isDirectionLeft * 35)) - (monster.get(i).x + (monster.get(i).isDirectionLeft * monster.get(i).canBeAttackDis)), 2) + Math.pow(i, 2));
			if(distance <= gameRole.getRole().attackRad) {
				monster.get(i).hp -=gameRole.getRole().attack;
				monster.get(i).status = 4;
				monster.get(i).basicCount = 0;
				monsterView.get(i).setImage(new Image(monster.get(i).hitUrl));
			}
		}
	}
}
