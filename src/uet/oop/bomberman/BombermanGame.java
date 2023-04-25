package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Block.Brick;
import uet.oop.bomberman.entities.Block.Grass;
import uet.oop.bomberman.entities.Block.Wall;
import uet.oop.bomberman.entities.Character.Ballom;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Character.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BombermanGame extends Application {
    public static final int WIDTH = 35;
    public static final int HEIGHT = 20;

    private Stage stage;
    private Scene menuScene;
    private static Scene gameScene;
    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Character> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> items = new ArrayList<>();
    private static char[][] mapData;
    private long startTime;
    public static long currentTime;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        // create menu scene
        createMenu();
        // show menu
        stage.setScene(menuScene);
        stage.show();
    }

    private void createMenu() {
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(20);

        Text titleLabel = new Text("Bomberman");

        Button playButton = new Button("Play");
        playButton.setOnAction(event -> {
            createGame();
            stage.setScene(gameScene);
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> stage.close());

        menuBox.getChildren().addAll(titleLabel, playButton, exitButton);

        menuScene = new Scene(menuBox, 640, 480);
    }

    private void createGame() {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gameScene = new Scene(new Group(canvas));
        // game initialization
        createMap("res/levels/level0.txt");
        //Game play
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                currentTime = (l - startTime) / 1000000;
                render();
                update();
            }

        };
        startTime = System.nanoTime();
        timer.start();

    }

    public void update() {
        List<Entity> stillObjectsCopy = new ArrayList<>(stillObjects);
        List<Character> entitiesCopy = new ArrayList<>(entities);
        for (Entity obj : stillObjectsCopy) {
            if (obj.isExist()) {
                obj.update();
            } else {
                stillObjects.remove(obj);
            }
        }
        for (Character obj : entitiesCopy) {
            if (obj.isExist()) {
                obj.update();
            } else {
                entities.remove(obj);
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void createMap(String path) {
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            String line = myReader.nextLine();
            StringTokenizer token = new StringTokenizer(line);
            int level = Integer.parseInt(token.nextToken());
            int row = Integer.parseInt(token.nextToken());
            int col = Integer.parseInt(token.nextToken());

            mapData = new char[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    mapData[i][j] = ' ';
                }
            }
            while (myReader.hasNextLine()) {
                for (int i = 0; i < row; i++) {
                    String data = myReader.nextLine();
                    for (int j = 0; j < col; j++) {
                        char characters = data.charAt(j);
                        switch (characters) {
                            case 'p': {
                                Bomber obj = new Bomber(j, i, Sprite.player_right.getFxImage());
                                entities.add(obj);
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            }

                            case '#': {
                                Entity obj = new Wall(j, i, Sprite.wall.getFxImage());
                                stillObjects.add(obj);
                                mapData[i][j] = '#';
                                break;
                            }

                            case '*': {
                                Entity obj = new Brick(j, i, Sprite.brick.getFxImage());
                                stillObjects.add(obj);
                                mapData[i][j] = '*';
                                break;
                            }

                            case 'b': {
                                Entity obj = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                                items.add(obj);
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            }

                            case 'f': {
                                Entity obj = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                                items.add(obj);
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            }

                            case 's': {
                                Entity obj = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                                items.add(obj);
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            }

                            case '1': {
                                Ballom obj = new Ballom(j, i, Sprite.balloom_right1.getFxImage());
                                entities.add(obj);
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            }

                            default:
                                Entity obj = new Grass(j, i, Sprite.grass.getFxImage());
                                stillObjects.add(obj);
                                break;
                        }
                    }
                }
            }
            stillObjects.addAll(items);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static char[][] getMapData() {
        return mapData;
    }

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static List<Character> getEntities() {
        return entities;
    }

    public static List<Entity> getItems() {
        return items;
    }

    public static Scene getGameScene() {
        return gameScene;
    }
}
