package uet.oop.bomberman.entities.Character;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Block.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Character {
    private int bombLimit;
    private int bombRange;
    public static List<Bomb> bombs = new ArrayList<>();
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private final Scene scene;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        this.scene = BombermanGame.getGameScene();
        this.speed = 1;
        this.bombLimit = 1;
        this.bombRange = 1;
        this.lives = 3;
    }

    public void placeBomb() {
        if (bombLimit > 0) {
            int x = (int) Math.round((double) this.x / 32);
            int y = (int) Math.round((double) this.y / 32);
            Bomb bomb = new Bomb(x, y, Sprite.bomb.getFxImage(), bombRange);
            bombs.add(bomb);
            BombermanGame.getStillObjects().add(bomb);
            bombLimit--;
        }
    }
    public void checkMove(int tileX, int tileY) {
        int diff = 4;
        int xLeft = (tileX + diff) / Sprite.SCALED_SIZE;
        int xRight = (tileX + Sprite.SCALED_SIZE - diff - 8) / Sprite.SCALED_SIZE;
        int yTop = (tileY + diff) / Sprite.SCALED_SIZE;
        int yBottom = (tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;
        //check move
        isMoving = map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ';
    }
    @Override
    public void changeAnimation(String direction) {
        switch (direction) {
            case "UP": {
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, BombermanGame.currentTime, 120).getFxImage();
                break;
            }
            case "DOWN": {
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, BombermanGame.currentTime, 120).getFxImage();
                break;
            }
            case "LEFT": {
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, BombermanGame.currentTime, 120).getFxImage();
                break;
            }
            case "RIGHT": {
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, BombermanGame.currentTime, 120).getFxImage();
                break;
            }
            case "DEAD": {
                img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, BombermanGame.currentTime/10, 240).getFxImage();
                break;
            }
        }
    }
    @Override
    public void collisionHandling() {
        //check bomb and enemy collision
        int bomberLeft = this.x;
        int bomberRight = this.x + 32;
        int bomberTop = this.y;
        int bomberBottom = this.y + 32;
        for (Character enemy : BombermanGame.getEntities()) {
            if (enemy instanceof Ballom) {
                int enemyLeft = (int) enemy.getX();
                int enemyRight = (int) (enemy.getX() + 32);
                int enemyTop = (int) enemy.getY();
                int enemyBottom = (int) (enemy.getY() + 32);

                if (bomberRight > enemyLeft && bomberLeft < enemyRight
                        && bomberBottom > enemyTop && bomberTop < enemyBottom) {
                    lives--;
                    break;
                }
            }
        }
        for (Explosion explosion : Bomb.getExplosions()) {
            int bombLeft = (int) explosion.getX();
            int bombRight = (int) (explosion.getX() + 32);
            int bombTop = (int) explosion.getY();
            int bombBottom = (int) (explosion.getY() + 32);

            if (bomberRight > bombLeft && bomberLeft < bombRight
                    && bomberBottom > bombTop && bomberTop < bombBottom) {
                lives--;
                break;
            }
        }
        //check item
        for(Entity item : BombermanGame.getItems()) {
            int itemLeft = (int) item.getX();
            int itemRight = (int) (item.getX() + 32);
            int itemTop = (int) item.getY();
            int itemBottom = (int) (item.getY() + 32);

            if (bomberRight > itemLeft && bomberLeft < itemRight
                    && bomberBottom > itemTop && bomberTop < itemBottom) {
                if(item instanceof SpeedItem) {
                    speed++;
                    item.setExist(false);
                    break;
                }
                if(item instanceof BombItem) {
                    bombLimit++;
                    item.setExist(false);
                    break;
                }
                if(item instanceof FlameItem) {
                    bombRange++;
                    item.setExist(false);
                    break;
                }
            }
        }

    }

    public void control() {
        if (lives > 0) {
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
                spacePressed.set(false);
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

    public static List<Bomb> getBombs() {
        return bombs;
    }

    @Override
    public void update() {
        if (lives > 0) {
            control();
        } else {
            changeAnimation("DEAD");
        }
        super.update();
        // update bombs
        List<Bomb> bombsCopy = new ArrayList<>(bombs);
        for (Bomb bomb : bombsCopy) {
            if (bomb.isExplore()) {
                bombs.remove(bomb);
                bombLimit++;
            }
        }
    }
}