package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Dino;
import entity.Dragon;
import entity.Fireball;
import entity.GameMap;
import entity.Monster;
import entity.Slime;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui_item.MenuButton;
import ui_item.ROLE;

public class GameWindow {
	private StackPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private static final int screenWidth = 1536;
	private static final int screenHeight = 768;
	private Stage menuStage;
	private ImageView role;
	private ROLE gameRole;
	private GraphicsContext gc;
	private int isDirectionLeft = -1; // -1 = left; 1 = right
	private int count;
	private int status = 1; //1 = idle , 2 = attack, 3 = skill, 4 = hit
	private Timeline loop;
	private Rectangle hpBar ;
	private Label timerLabel;
    private int remainingSeconds;
    private static final int gameTime = 60;
    private Label scoreLabel;
    private int nowScore = 0 ;
	private MediaPlayer backgroundMusic;
    private AnchorPane scorePane ;
	private int random_x;
	private int random_y;
	private Monster newMonster;
	private Random random = new Random();
	private int createSlimeCount  = 10000;
	
	private List<Monster> monster = new ArrayList<>();
	private List<ImageView> monsterView = new ArrayList<>();
	private List<Fireball> fireball = new ArrayList<>();
	private List<ImageView> fireballView = new ArrayList<>();
	private boolean gameOver = false ;
	//image
	private Image idleImage;
	private Image attackImage;
	private Image hitImage;
	private Image skillImage;
	private GameMap gameMap ;
	
	//key boolean
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean keyQPressed = false;
	private boolean keyWPressed = false;

	
	//test
//	private Circle roleborder;
//	private boolean keyEPressed = false;
//	private List<Circle> circles = new ArrayList<>();
//	private List<Circle> attacks = new ArrayList<>();
//	private List<Circle> fireballtest = new ArrayList<>();
//	private Circle tmpC;
	
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
//				if(event.getCode() == KeyCode.E) {
//					keyEPressed = true;
//				}
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
		gameMap = new GameMap();
		gamePane = gameMap.getRoot();
		
		hpBar();
		fight();
		scoreZero();
		startCountdown();
		gameScene = new Scene(gamePane, screenWidth, screenHeight);
		gameStage = new Stage();
		gameStage.setTitle("Monster Game");
		gameStage.setScene(gameScene);
		createListener();
	}
	
	private Label fightLabel = new Label("FIGHT!") ;
	private AnchorPane fightPane ;
	private void fight() {
		
	    fightLabel.getStyleClass().add("timer-label");
	    fightLabel.setStyle("-fx-font-family: Impact; -fx-font-size: 50px;");
	    fightPane = new AnchorPane() ;
        fightPane.setTopAnchor(fightLabel, 20.0);
        fightPane.setRightAnchor(fightLabel, 680.0);
        fightPane.getChildren().addAll(fightLabel);
        gamePane.getChildren().add(fightPane);
		Timeline fight = new Timeline() ;
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(10),new KeyValue(fightLabel.textProperty(), "Fight!")) ;
		fight.getKeyFrames().add(keyFrame);
		fight.play() ;
//        fight.setCycleCount(1);
//        fight.setDelay(Duration.ZERO);
        
	}
	
	public void createNewGame(Stage menuStage, ROLE chooseRole ) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		this.gameRole = chooseRole;
		createBackgroundMusic();
		createRole();
		gameStage.show();
		gameRole.getRole().hp = 100;
		gameRole.getRole().score = 0;
		loop = new Timeline(new KeyFrame(Duration.millis(1000.0/60), e -> update(gc)));
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
		
//		//人物邊界測試
//		roleborder = new Circle();
//		roleborder.setRadius(gameRole.getRole().idleRad);
//		roleborder.setFill(Color.TRANSPARENT);
//		roleborder.setStroke(Color.RED);
//		roleborder.setStrokeWidth(2);
//		
//		gamePane.getChildren().add(roleborder);
	}
	
	private void update(GraphicsContext gc) {
		if (remainingSeconds == 59) gamePane.getChildren().remove(fightPane) ;
		role.toFront();
		count++;
		createSlimeCount++;
		gameRole.getRole().invincibleCount++;
		
		if (gameRole.getRole().score>0) {
			gamePane.getChildren().remove(scorePane) ;
			System.out.println("remove") ;
		}
		
		scoreCount() ;
		createMonster(createSlimeCount);
		decreaseHp() ;
		
		for(int i = 0; i< monster.size(); i++) {
			monster.get(i).basicCount++;
			monster.get(i).delayTimeCount++;
			double distance = Math.sqrt(Math.pow(gameRole.getRole().x - monster.get(i).x, 2) + Math.pow(gameRole.getRole().y - monster.get(i).y, 2));
			
			if(monster.get(i).delayStatus == 1) {
				//attack
				if(monster.get(i).delayTimeCount > monster.get(i).attackTimeCount) {
					if(distance <= monster.get(i).attackRad + gameRole.getRole().idleRad && gameRole.getRole().invincibleCount>100) {
						gameRole.getRole().hp -= monster.get(i).attack;
						System.out.println("hp = " + gameRole.getRole().hp);
						gameRole.getRole().invincibleCount = 0;
						hitImage = new Image(gameRole.getRole().hitUrl);
						role.setImage(hitImage);
						status = 4;
						count = 0;
						//System.out.println("attack hit");
					}
					monster.get(i).delayTimeCount = 0;
					monster.get(i).delayStatus = 0;
				}
				
			}else if (monster.get(i).delayStatus ==2) {
				//skill
				if(monster.get(i).delayTimeCount > monster.get(i).skillTimeCount) {
					if(distance <= monster.get(i).skillRad + gameRole.getRole().idleRad && gameRole.getRole().invincibleCount>100) {
						gameRole.getRole().hp -= monster.get(i).attack * 2;
						System.out.println("hp = " + gameRole.getRole().hp);
						gameRole.getRole().invincibleCount = 0;
						hitImage = new Image(gameRole.getRole().hitUrl);
						role.setImage(hitImage);
						status = 4;
						count = 0;
						//System.out.println("skill hit");
					}
					monster.get(i).delayTimeCount = 0;
					monster.get(i).delayStatus = 0;
				}
			}
			
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
//				gamePane.getChildren().remove(tmpC);
			}
		}else if(status == 3) {
			createSkillSound(count);
			if(gameRole.getRole().fireSoundUrl != null) {
				createFireSound(count);
				createFireball(count);	///////////////////////////////////
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
//			tmpC = new Circle(gameRole.getRole().attackRad);
//			tmpC.setTranslateX(gameRole.getRole().x + isDirectionLeft * 35);
//			tmpC.setTranslateY(gameRole.getRole().y);
//			tmpC.setFill(Color.TRANSPARENT);
//			tmpC.setStroke(Color.BLACK);
//			tmpC.setStrokeWidth(2);
//			gamePane.getChildren().add(tmpC);
			
		}
		if(keyWPressed) {
			skillImage = new Image(gameRole.getRole().skillUrl);
			role.setImage(skillImage);
			status = 3;
			count = 0;
			gameRole.getRole().skill();
			keyWPressed = false;
		}
//		if(keyEPressed) {
//			hitImage = new Image(gameRole.getRole().hitUrl);
//			role.setImage(hitImage);
//			status = 4;
//			count = 0;
//			keyEPressed = false;
//		}
		
		
		role.setTranslateX(gameRole.getRole().x);
		role.setTranslateY(gameRole.getRole().y);
//		roleborder.setTranslateX(gameRole.getRole().x );
//		roleborder.setTranslateY(gameRole.getRole().y );
		
		if(gameRole.getRole().fireSoundUrl != null) {
			fireballmove();
		}
		
		for(int i = 0; i<monster.size(); i++) {
			monsterMove(monster.get(i), monsterView.get(i));//, circles.get(i), attacks.get(i);
			if(monster.get(i).hp<=0) {
				gameRole.getRole().score+= monster.get(i).score ;
				gamePane.getChildren().remove(monsterView.get(i));
//				gamePane.getChildren().remove(circles.get(i));
//				gamePane.getChildren().remove(attacks.get(i));
				monster.remove(i);
				monsterView.remove(i);
				
//				//test
//				attacks.remove(i);
//				circles.remove(i);
			}
		}
		
	}
	
	private void fireballmove() {
		for(int i = 0; i < fireball.size(); i++) {
			fireball.get(i).urlcount++;
			if(0<=fireball.get(i).urlcount && fireball.get(i).urlcount<=5) {
				fireballView.get(i).setViewport(new javafx.geometry.Rectangle2D(0, 0, 79, 46));
			}else if (6<=fireball.get(i).urlcount && fireball.get(i).urlcount<=10) {
				fireballView.get(i).setViewport(new javafx.geometry.Rectangle2D(79, 0, 79, 46));
			}else if (11<=fireball.get(i).urlcount && fireball.get(i).urlcount<=15) {
				fireballView.get(i).setViewport(new javafx.geometry.Rectangle2D(158, 0, 79, 46));
			}
			if(16<=fireball.get(i).urlcount) {
				fireball.get(i).urlcount = 0;
			}
			fireball.get(i).movecount++;
			fireball.get(i).lifecount++;
			if(fireball.get(i).movecount >= 3) {
				fireball.get(i).movecount = 0;
				fireball.get(i).x += fireball.get(i).speed * fireball.get(i).direction;
				fireballView.get(i).setTranslateX(fireball.get(i).x);
				fireballView.get(i).setTranslateY(fireball.get(i).y);
				
				//test
//				fireballtest.get(i).setTranslateX(fireball.get(i).x + fireball.get(i).direction * 15);
//				fireballtest.get(i).setTranslateY(fireball.get(i).y);
			}
			
			for(int j =0 ; j < monster.size(); j++) {
				double distance = Math.sqrt(Math.pow((fireball.get(i).x + (fireball.get(i).direction * 15)) - (monster.get(j).x + (monster.get(j).isDirectionLeft * monster.get(j).canBeAttackDis)), 2) + 
						Math.pow(fireball.get(i).y - monster.get(j).y, 2));
				if(distance <= fireball.get(i).rad) {
					System.out.println("hihi");
					monster.get(j).hp -=gameRole.getRole().attack * 5;
					monster.get(j).status = 4;
					monster.get(j).basicCount = 0;
					monsterView.get(j).setImage(new Image(monster.get(j).hitUrl));
				}
			}
			
			if(fireball.get(i).lifecount >= 300) {
				this.fireball.remove(fireball.get(i));
				gamePane.getChildren().remove(fireballView.get(i));
				this.fireballView.remove(fireballView.get(i));
				
				//test
//				gamePane.getChildren().remove(fireballtest.get(i));
//				this.fireballtest.remove(fireballtest.get(i));
			}
		}
	}
	
	
	private void createFireball(int count) { ///////////////////////////////////
		if(count == 115) {
			Fireball tmpFireball = new Fireball(gameRole.getRole().x + isDirectionLeft * 95, gameRole.getRole().y);
			tmpFireball.direction = isDirectionLeft;
			fireball.add(tmpFireball);
			Image tmp = new Image(tmpFireball.Fire);
			ImageView tmpV = new ImageView(tmp);
			if(isDirectionLeft == -1) {
				tmpV.setScaleX(-1);
			}
			tmpV.setViewport(new javafx.geometry.Rectangle2D(0, 0, 79, 46));
			tmpV.setFitHeight(tmpFireball.fitHeight);
			tmpV.setFitWidth(tmpFireball.fitWidth);
			tmpV.setTranslateX(tmpFireball.x);
			tmpV.setTranslateY(tmpFireball.y);
			this.fireballView.add(tmpV);
			gamePane.getChildren().add(tmpV);
			
			//test
//			Circle tmpR = new Circle(tmpFireball.rad);
//			tmpR.setTranslateX(tmpFireball.x + tmpFireball.direction * 15);
//			tmpR.setTranslateY(tmpFireball.y);
//			tmpR.setFill(Color.TRANSPARENT);
//			tmpR.setStroke(Color.RED);
//			tmpR.setStrokeWidth(2);
//			this.fireballtest.add(tmpR);
//			gamePane.getChildren().add(tmpR);
			
		}
	}
	
	private void createAttackSound(int c) {
		if (c == gameRole.getRole().attackSoundcount) {
			String path = gameRole.getRole().attackSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setVolume(0.1);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createSkillSound(int c) {
		if (c == gameRole.getRole().skillSoundcount) {
			String path = gameRole.getRole().skillSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setVolume(0.1);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createFireSound(int c) {
		if (c == 115) {
			String path = gameRole.getRole().fireSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setVolume(0.1);
			sound.setAutoPlay(true);
			sound.setCycleCount(1);
		}
	}
	
	private void createHitSound(int c) {
		if (c == 8) {
			String path = gameRole.getRole().hitSoundUrl;
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer sound = new MediaPlayer(media);
			sound.setVolume(0.1);
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
			newMonster = new Slime(random_x, random_y);
			
			int chooseMonster = random.nextInt(3);//0 slime 1 dino 2 dragon 3 giant
			if (remainingSeconds<gameTime &&chooseMonster==0 ) {
//				MonsterCage.add(new Slime(0, 0));
				newMonster = new Slime(random_x,random_y) ;
			}
			else if (remainingSeconds<gameTime-10&&chooseMonster==1) {
//				MonsterCage.add(new Dino(0, 0));
				newMonster = new Dino(random_x,random_y) ;
			}
			else if (remainingSeconds<gameTime-20&&chooseMonster==2) {
//				MonsterCage.add(new Dragon(0, 0));
				newMonster = new Dragon(random_x,random_y) ;
			}
			
			
			this.monster.add(newMonster);
			
			Image tmp = new Image(newMonster.idleUrl);
			ImageView tmpV = new ImageView(tmp);
			tmpV.setFitHeight(newMonster.fitHeight);
			tmpV.setFitWidth(newMonster.fitWidth);
			this.monsterView.add(tmpV);
			gamePane.getChildren().add(tmpV);
			
			//test
//			Circle tmpR = new Circle(newMonster.idleRad);
//			tmpR.setTranslateX(random_x);
//			tmpR.setTranslateY(random_y);
//			tmpR.setFill(Color.TRANSPARENT);
//			tmpR.setStroke(Color.RED);
//			tmpR.setStrokeWidth(2);
//			this.circles.add(tmpR);
//			gamePane.getChildren().add(tmpR);
//			Circle beAttack = new Circle(2);
//			beAttack.setTranslateX(random_x + newMonster.isDirectionLeft * newMonster.canBeAttackDis);
//			beAttack.setTranslateY(random_y);
//			this.attacks.add(beAttack);
//			gamePane.getChildren().add(beAttack);
	
		}
	}
	
	private void monsterMove(Monster tmp_monster, ImageView tmp_imageView) {
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
			if (distance <= (gameRole.getRole().idleRad + tmp_monster.idleRad) && gameRole.getRole().invincibleCount>100) {
				if(tmp_monster.canUseSkillCount >= tmp_monster.canUseSkill) {
					//monster skill
					tmp_monster.delayTimeCount = 0;
					tmp_imageView.setImage(new Image(tmp_monster.skillUrl));
					tmp_monster.canUseSkillCount = 0;
					tmp_monster.basicCount = 0;
					tmp_monster.status = 3;
					tmp_monster.delayStatus = 2;
						
				}else {
					//monster attack
					tmp_monster.delayTimeCount = 0;
					tmp_imageView.setImage(new Image(tmp_monster.attackUrl));
					tmp_monster.canUseSkillCount++;
					tmp_monster.basicCount = 0;
					tmp_monster.status = 2;
					tmp_monster.delayStatus = 1;
						
				}
			}else	{
				if(tmp_monster.moveCount >= 4) {
					double angle = Math.atan2(gameRole.getRole().y - tmp_monster.y, gameRole.getRole().x - tmp_monster.x);
					tmp_monster.x += (Math.cos(angle)* tmp_monster.speed);
					tmp_monster.y += (Math.sin(angle)* tmp_monster.speed);
					tmp_imageView.setTranslateX(tmp_monster.x);
					tmp_imageView.setTranslateY(tmp_monster.y);
					
//					tmp_circle.setTranslateX(tmp_monster.x);
//					tmp_circle.setTranslateY(tmp_monster.y);
//					tmp_beattack.setTranslateX(tmp_monster.x + tmp_monster.isDirectionLeft * tmp_monster.canBeAttackDis);
//					tmp_beattack.setTranslateY(tmp_monster.y);
					
					tmp_monster.moveCount = 0;
				}
				
			}
		}
	}
	private void roleAttack() {
		for(int i =0 ; i < monster.size(); i++) {
			double distance = Math.sqrt(Math.pow((gameRole.getRole().x + (isDirectionLeft * 35)) - (monster.get(i).x + (monster.get(i).isDirectionLeft * monster.get(i).canBeAttackDis)), 2) + 
					Math.pow(gameRole.getRole().y - monster.get(i).y, 2)); /////////////////////要改
			if(distance <= gameRole.getRole().attackRad) {
				monster.get(i).hp -=gameRole.getRole().attack;
				monster.get(i).status = 4;
				monster.get(i).basicCount = 0;
				monsterView.get(i).setImage(new Image(monster.get(i).hitUrl));
			}
		}
	}
	//以下血量
	private void hpBar () {
		
		
		Rectangle hpBackground = new Rectangle(500,50,Color.WHITE) ;
		hpBar = new Rectangle(500,50,Color.RED);
//		hpBar.setStyle("-fx-accent: red;");
		AnchorPane labelPane = new AnchorPane() ;
		AnchorPane hpPane = new AnchorPane() ;
//		if(gameRole != null) {
//			
////			hpBackground.setPrefWidth(gameRole.getRole().hp); // 設定初始血量 (範例值)
////			hpBar.setPrefWidth(gameRole.getRole().hp);
//		}
//		hpBackground.setPrefHeight(20);
//		hpBar.setPrefHeight(18);
		// 設定寬度
        Label hpLabel = new Label("H P");
        hpLabel.setStyle("-fx-font-family: Impact; -fx-font-size: 50px;");
//        pane.getChildren().addAll(hpLabel,hpBar);
//        gamePane.setMargin(pane,new Insets(10)) ;
        hpPane.setTopAnchor(hpBar, 25.0);
        hpPane.setLeftAnchor(hpBar, 20.0);  
        hpPane.setTopAnchor(hpBackground, 25.0);
        hpPane.setLeftAnchor(hpBackground, 20.0);  
        hpPane.setTopAnchor(hpLabel, 21.0);
        hpPane.setLeftAnchor(hpLabel, 240.0); 
        hpPane.getChildren().addAll(hpBackground,hpBar,hpLabel) ;
        gamePane.getChildren().addAll(hpPane);
        
        
	}
	private void decreaseHp() {
         if( gameRole != null ) {      	 
             if (gameRole.getRole().hp<=0) {
            	 hpBar.setWidth(0) ;
            	 GameOver() ;//角色死亡判定在這
            	 
             }
             else hpBar.setWidth(gameRole.getRole().hp*5);
         }
    }
	
	//以下倒數計時
	 private Timeline gameTimeCount ;
	 private void startCountdown() {
		    timerLabel = new Label();
	        timerLabel.getStyleClass().add("timer-label");
	        timerLabel.setStyle("-fx-font-family: Impact; -fx-font-size: 50px;");
	       
	        AnchorPane gameTimePane = new AnchorPane() ;
	        gameTimePane.setTopAnchor(timerLabel, 20.0);
	        gameTimePane.setRightAnchor(timerLabel, 700.0);
	        gameTimePane.getChildren().addAll(timerLabel);
	        gamePane.getChildren().add(gameTimePane);
	        
	        
	        remainingSeconds = gameTime;
	        gameTimeCount = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	            updateTimerLabel();

	            if (remainingSeconds < 0 ) {
	                stopCountdown();
	            }
	        }));
	        gameTimeCount.setCycleCount(Animation.INDEFINITE);
	        gameTimeCount.setDelay(Duration.ZERO);
	        gameTimeCount.play();
	    }
     
	  private void stopCountdown() {
	        // Perform any necessary actions when the countdown reaches 0
		    gameTimeCount.pause() ;
		    TimeUp() ;
	    }

	  private void updateTimerLabel() {
	        int minutes = remainingSeconds / 60;
	        int seconds = remainingSeconds % 60;

	        String minutesText = String.format("%02d", minutes);
	        String secondsText = String.format("%02d", seconds);

	        timerLabel.setText(minutesText + ":" + secondsText);
	        remainingSeconds--;
	  }
	  
	  private void TimeUp() {
		  Text TimeUpText = new Text("Time's Up!!!");
		  TimeUpText.setStyle("-fx-font-family: Impact; -fx-font-size: 300px;");
	      gamePane.getChildren().addAll(TimeUpText);
	      loop.pause();
	      createNewGameButton();

	  }
	  private void GameOver() {
		  Text GameOverText = new Text("-GAME OVER-");
		  GameOverText.setStyle("-fx-font-family: Impact; -fx-font-size: 300px;");
	      gamePane.getChildren().addAll(GameOverText);
	      gameOver = true ;
	      gameTimeCount.pause() ;
	      loop.pause();
	      createNewGameButton();
	      
	  }
	  private void scoreZero() {
		  scoreLabel = new Label("Score: "+ 0);
		  scorePane = new AnchorPane() ;
		  scorePane.getChildren().addAll(scoreLabel);
		  scorePane.setTopAnchor(scoreLabel, 20.0);
	      scorePane.setRightAnchor(scoreLabel, 30.0);
	     
	      gamePane.getChildren().add(scorePane);
	  }
	  private void scoreCount() {
	        
            int scoreCount = gameRole.getRole().score ; //動畫用
            int oldScore = nowScore ;
		    
		    scorePane = new AnchorPane() ;
	        scoreLabel.setStyle("-fx-font-family: Impact; -fx-font-size: 50px;");
	        Timeline timeline = new Timeline();
	        for (int i = oldScore; i <= scoreCount; i++) {
	            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01), new KeyValue(scoreLabel.textProperty(), "Score: "+i));
	            timeline.getKeyFrames().add(keyFrame);
	        }
	        timeline.play();
	        
	        nowScore = gameRole.getRole().score ;
	        scorePane.setTopAnchor(scoreLabel, 20.0);
	        scorePane.setRightAnchor(scoreLabel, 30.0);
	        
	        gamePane.getChildren().add(scorePane);

	  }
	  
	  private void createNewGame() {
		  Stage windown;
		  ViewManager viewManager = new ViewManager();
		  windown = viewManager.getMainStage();
		  windown.show();
	  }
	  
	  private void createBackgroundMusic() {
			
			String path = "src/resource/Music/BattleMusic.mp3";
			Media media = new Media(new File(path).toURI().toString());
			backgroundMusic = new MediaPlayer(media);
			backgroundMusic.setAutoPlay(true);
			backgroundMusic.setVolume(0.1);
			backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
	  }
	  
	  private void createNewGameButton() {
			
			MenuButton button= new MenuButton("NewGame");
			button.setTranslateX(20);
			button.setTranslateY(200);
			button.toFront();
			gamePane.getChildren().add(button);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					createNewGame();
					backgroundMusic.stop();
				    gameStage.close();
				    
				}
			});
		}
}

