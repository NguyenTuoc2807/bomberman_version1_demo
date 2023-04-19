package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class GamePlay extends Application {
    public static final int WIDTH = 35;
    public static final int HEIGHT = 20;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static char[][] mapData;
    private long startTime;
    public static long currentTime;
    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // khởi tạo map
        Map map = new Map();
        map.createMap("res/levels/level0.txt");
        mapData = map.getMap();
        stillObjects = map.getStillObjects();
        entities = map.getEntities();
        // khởi tạo nhân vật
        Bomber bomber = new Bomber(1,1,Sprite.player_right.getFxImage(),scene);
        entities.add(bomber);
        //Game play
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                currentTime = (l - startTime) / 1000000;
                bomber.control();
                render();
                update();
            }

        };
        startTime = System.nanoTime();
        timer.start();
    }
    public void update() {
        entities.forEach(Entity::update);
    }
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    public static char[][] getMapData() {
        return mapData;
    }

    public static void main(String[] args) {
        Application.launch(GamePlay.class);
    }
}
