package uet.oop.bomberman.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends Entity {
    private int speed;
    private int bombLimit;
    private int bombRange;
    private int lives;
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private final Scene scene;
    private List<Entity> entities = GamePlay.getEntities();
    public char[][] map = GamePlay.getMapData();
    public Bomber(int x, int y, Image img, Scene scene) {
        super(x, y, img);
        this.scene = scene;
        this.speed = 1;
        this.bombLimit = 1;
        this.bombRange = 1;
        this.lives = 3;
    }

    public void placeBomb() {
        if (bombLimit > 0) {
            Bomb bomb = new Bomb(x / 32, y / 32, Sprite.bomb.getFxImage());
            entities.add(bomb);
            bombLimit--;
        }
    }

    public void setMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeAnimation(String direction) {
        switch (direction) {
            case "UP": {
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, GamePlay.currentTime, 120).getFxImage();
                break;
            }
            case "DOWN": {
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, GamePlay.currentTime, 120).getFxImage();
                break;
            }
            case "LEFT": {
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, GamePlay.currentTime, 120).getFxImage();
                break;
            }
            case "RIGHT": {
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, GamePlay.currentTime, 120).getFxImage();
                break;
            }
        }
    }

    public void move(String direction) {
        int newTileX = this.x;
        int newTileY = this.y;
        switch (direction) {
            case "LEFT":
                newTileX -= speed;
                break;
            case "RIGHT":
                newTileX += speed;
                break;
            case "UP":
                newTileY -= speed;
                break;
            case "DOWN":
                newTileY += speed;
                break;
        }
        if (isValidMove(newTileX, newTileY)) {
            changeAnimation(direction);
            setMove(newTileX, newTileY);
        }
    }

    private boolean isValidMove(double tileX, double tileY) {
        int diff = 3;
        int xLeft = (int) (tileX + diff) / Sprite.SCALED_SIZE;
        int xRight = (int) (tileX + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;
        int yTop = (int) (tileY + diff) / Sprite.SCALED_SIZE;
        int yBottom = (int) (tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;

        return map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ';
    }

    public void control() {
        setControl();
        if (wPressed.get()) {
            move("UP");
        }

        if (sPressed.get()) {
            move("DOWN");
        }

        if (aPressed.get()) {
            move("LEFT");
        }

        if (dPressed.get()) {
            move("RIGHT");
        }

        if (spacePressed.get()) {
            placeBomb();
        }
    }

    private void setControl() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }

            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }

            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            }
        });
    }

    @Override
    public void update() {
    }
}