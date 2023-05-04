package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.ControlGame.LevelManager;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Block.Brick;
import uet.oop.bomberman.entities.Block.Grass;
import uet.oop.bomberman.entities.Block.Portal;
import uet.oop.bomberman.entities.Block.Wall;
import uet.oop.bomberman.entities.Character.Ballom;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Character.Character;
import uet.oop.bomberman.entities.Character.Oneal;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

//import static uet.oop.bomberman.Sound.media;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private Stage stage;
    private Scene menuScene;
    private Scene gameOverScene;
    private static Scene gameScene;
    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Character> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static char[][] mapData;
    private long startTime;

    public static long currentTime;
    private int time;
    public static Bomber bomber;
    private int level;
    public static int height;
    public static int width;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        createMenu();
        stage.setScene(menuScene);
        stage.show();
        // TODO:
        Sound.loadMedia();
        Sound.playBackground(Sound.heading);
    }

    private void createMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("res/Game/Menu.fxml").toURI().toURL());
        AnchorPane menuBox = fxmlLoader.load();

        Sound.playHeading();

        Button playButton = (Button) menuBox.lookup("#playButton");
        playButton.setOnAction(event -> {
            try {
                createGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Sound.stopHeading();
            stage.setScene(gameScene);
        });

        Button exitButton = (Button) menuBox.lookup("#Exit");
        exitButton.setOnAction(event -> stage.close());


        menuScene = new Scene(menuBox);
    }

    private void createGame() throws IOException {
        // setup game
        FXMLLoader fxmlLoader = new FXMLLoader(new File("res/Game/GamePlay.fxml").toURI().toURL());
        AnchorPane gameBox = fxmlLoader.load();
        canvas = (Canvas) gameBox.lookup("#gameplay");
        canvas.setWidth(WIDTH * Sprite.SCALED_SIZE);
        canvas.setHeight(HEIGHT * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();
        gameScene = new Scene(gameBox);
        Sound.playSfx(Sound.ingame);
        // game initialization
        stillObjects.clear();
        entities.clear();
        LevelManager levelManager = new LevelManager();
        createMap(levelManager.getLevel());
        // timer
        Timeline timeline = new Timeline();
        time = 60;
        timeline.setCycleCount(Timeline.INDEFINITE);
        Label timerLabel = (Label) gameBox.lookup("#timer");
        Label livesLabel = (Label) gameBox.lookup("#lives");
        Label scoreLabel = (Label) gameBox.lookup("#score");
        Label levelLabel = (Label) gameBox.lookup("#level");

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            time--;
            if (time >= 0) {
                timerLabel.setText(String.format("%02d:%02d", time / 60, time % 60));
                livesLabel.setText(String.format("Lives: %d", bomber.getLives()));
                scoreLabel.setText(String.format("Score: %d", bomber.getScore()));
                levelLabel.setText(String.format("Level: %d", level));
            } else {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        //Game play
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // is game over
                if (bomber.getLives() == 0 || time == 0) {
                    timeline.stop();
                    try {
                        GameOver();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(gameOverScene);
                    stop();
                }
                // next level
                if (bomber.isNextLevel()) {
                    time = 60;
                    bomber.setNextLevel(false);
                    levelManager.nextLevel();
                    createMap(levelManager.getLevel());
                }
                currentTime = (l - startTime) / 1000000;
                render();
                update();
            }

        };
        startTime = System.nanoTime();
        timer.start();
    }

    private void GameOver() throws IOException {
        stillObjects.clear();
        entities.clear();
        Sound.stopInGame();
        FXMLLoader fxmlLoader = new FXMLLoader(new File("res/Game/GameOver.fxml").toURI().toURL());
        AnchorPane gameOverBox = fxmlLoader.load();
        Button playButton = (Button) gameOverBox.lookup("#playAgain");
        playButton.setOnAction(event -> {
            try {
                createGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(gameScene);
        });

        Button exitButton = (Button) gameOverBox.lookup("#Menu");
        exitButton.setOnAction(event -> {
            try {
                createMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(menuScene);
        });

        gameOverScene = new Scene(gameOverBox);
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
            level = Integer.parseInt(token.nextToken());
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
                                if (bomber == null || bomber.getLives() == 0) {
                                    Bomber obj = new Bomber(j, i, Sprite.player_right.getFxImage());
                                    bomber = obj;
                                    entities.add(obj);
                                } else {
                                    bomber.setX(j * Sprite.SCALED_SIZE);
                                    bomber.setY(i * Sprite.SCALED_SIZE);
                                    entities.add(bomber);
                                }
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
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                stillObjects.add(obj);
                                break;
                            }

                            case 'f': {
                                Entity obj = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                stillObjects.add(obj);
                                break;
                            }

                            case 's': {
                                Entity obj = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                stillObjects.add(obj);
                                break;
                            }

                            case '1': {
                                Ballom obj = new Ballom(j, i, Sprite.balloom_right1.getFxImage());
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                entities.add(obj);
                                break;
                            }

                            case '2': {
                                Oneal oneal = new Oneal(j,i,Sprite.oneal_right1.getFxImage());
                                stillObjects.add(new Grass(j,i,Sprite.grass.getFxImage()));
                                entities.add((oneal));
                                break;
                            }

                            case 'x': {
                                Portal obj = new Portal(j, i, Sprite.portal.getFxImage());
                                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                stillObjects.add(obj);
                                stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                                mapData[i][j] = '*';
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
            bomber.setMap(mapData);
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

    public static Scene getGameScene() {
        return gameScene;
    }
}
