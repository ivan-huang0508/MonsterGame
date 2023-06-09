package entity;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.util.Random;
import javafx.scene.layout.StackPane;

public class GameMap extends Application {

    private static final int CELL_SIZE = 64;
    private static final int WIDTH = 24;
    private static final int HEIGHT = 12;
    private static final int MapLength = 5 ;

    private Image[] mapElements;
    private Image[] mapItems ;
    private int[][] map;
    private StackPane root ;
    private Scene scene;
    public GameMap() {
    	// 載入地圖元素圖片
        loadMapElements();

        // 創建地圖
        createMap();

        // 創建畫布
        Canvas canvas = new Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        // 繪製地圖
        drawMap(gc);

        root = new StackPane();
        root.getChildren().add(canvas);
       
      
    }
    public StackPane getRoot() {
    	return root ;
    }
    
    @Override
    public void start(Stage primaryStage) {
        

        // 載入地圖元素圖片
        loadMapElements();

        // 創建地圖
        createMap();

        // 創建畫布
        Canvas canvas = new Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        // 繪製地圖
        drawMap(gc);

        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        primaryStage.setScene(scene);
    }

    private void loadMapElements() {
        mapElements = new Image[5];
        mapElements[0] = new Image("resource\\Background\\GameMap\\tile_0000.png");
        mapElements[1] = new Image("resource\\Background\\GameMap\\tile_0001.png");
        mapElements[2] = new Image("resource\\Background\\GameMap\\tile_0001.png");
        mapElements[3] = new Image("resource\\Background\\GameMap\\tile_0043.png");
        mapElements[4] = new Image("resource\\Background\\GameMap\\tile_0043.png");
        
        mapItems = new Image[3] ;
        mapItems[0] = new Image("resource\\Background\\GameMap\\tile_0000.png");
        mapItems[1] = new Image("resource\\Background\\GameMap\\tile_0001.png");
        mapItems[2] = new Image("resource\\Background\\GameMap\\tile_0001.png");
        
        //var imageFiles = folder.GetFiles("*.jpg");
//        for (int i = 0; i < MapLength; i++) {
//            mapElements[i] = folder.GetFiles("*.jpg");
//        }
    }

    private void createMap() {
        map = new int[HEIGHT][WIDTH];
        Random random = new Random();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                // 隨機選擇地圖元素
                int elementIndex = random.nextInt(MapLength);
                map[i][j] = elementIndex;
            }
        }
    }

    private void drawMap(GraphicsContext gc) {
    	int elementIndex ;
       
        Image elementImage ;
        Random random = new Random() ;
//        Image lucb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0044.png");
//        Image rucb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0046.png");
//        Image llcb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0068.png");
//        Image rlcb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0070.png");
//        Image ub= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0053.png");
//        Image lb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0061.png");
//        Image woodub= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0045.png");
//        Image woodlb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0069.png");
//        Image woodb= new Image("C:\\CSIE_HW\\MonsterGame\\64pixel tile map\\tile_0059.png");
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                // 繪製地圖元素
            	elementIndex = map[i][j];
            	elementImage = mapElements[elementIndex];
                int ram = random.nextInt(10) ;
                gc.drawImage(elementImage, j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (ram<1&&elementIndex <= 2 ) gc.drawImage(mapItems[elementIndex], j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                
            }
            
        }
        

    }

    public static void main(String[] args) {
        launch(args);
    }
}