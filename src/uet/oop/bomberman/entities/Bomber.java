package uet.oop.bomberman.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Block.Bomb;
import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends Entity {
    public int posX = x;
    public int posY = y;
    private int speed;
    private int bombLimit;
    private int bombRange;
    private int lives;
    public Bomb bomb;
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private final Scene scene;
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
            int x = (int) Math.round((double) this.x / 32);
            int y = (int) Math.round((double) this.y / 32);
            bomb = new Bomb(x,y, Sprite.bomb.getFxImage());
            GamePlay.getStillObjects().add(bomb);
            bombLimit--;
        }
        if(bomb != null) {
            if(bomb.isExplore()) {
                bombLimit++;
            }
        }
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
            case "DEAD": {
                img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, GamePlay.currentTime, 240).getFxImage();
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
        if (collisionHandling(newTileX, newTileY) == 1) {
            changeAnimation(direction);
            posX = newTileX;
            posY = newTileY;
        } else if (collisionHandling(newTileX, newTileY) == 2) {
            changeAnimation("DEAD");
            lives--;
        }
    }

    private int collisionHandling(double tileX, double tileY) {
        int x = (int) Math.round(tileX / 32);
        int y = (int) Math.round(tileY / 32);
        int diff = 5;
        int xLeft = (int) (tileX + diff) / Sprite.SCALED_SIZE;
        int xRight = (int) (tileX + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;
        int yTop = (int) (tileY + diff) / Sprite.SCALED_SIZE;
        int yBottom = (int) (tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;

        if(map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ' && map[y][x] == ' ') {
            return 1;
        } else if (map[yTop][xLeft] == 'o' || map[yTop][xRight] == 'o' || map[yBottom][xLeft] == 'o' || map[yBottom][xRight] == 'o' || map[y][x] == 'o') {
            return 2;
        }
        return 0;
    }

    public void control() {
        if(lives > 0) {
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
        System.out.println(lives);
        this.x = posX;
        this.y = posY;
        if(lives == 0) {
            changeAnimation("DEAD");
        }
    }
}