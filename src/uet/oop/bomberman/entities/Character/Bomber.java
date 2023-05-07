package uet.oop.bomberman.entities.Character;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Block.Bomb;
import uet.oop.bomberman.entities.Block.Portal;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Player {
    private int bombLimit;
    private int bombRange;
    private boolean FlamePassed = false;
    private boolean WallPassed = false;
    private boolean BombPassed = false;
    private boolean Detonator = false;
    private final int timeImmortal = 500;
    private int time = 0;
    private boolean isDead = false;
    private int score = 0;
    private boolean isNextLevel = false;
    public static List<Bomb> bombs = new ArrayList<>();
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private final BooleanProperty bPressed = new SimpleBooleanProperty();
    private final Scene scene;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        this.scene = BombermanGame.getGameScene();
        this.speed = 1;
        this.bombLimit = 3;
        this.bombRange = 1;
        this.lives = 3;
    }

    public void placeBomb() {
        if (bombLimit > 0) {
            int x = (int) Math.round((double) this.x / 32);
            int y = (int) Math.round((double) this.y / 32);
            Bomb bomb = new Bomb(x, y, Sprite.bomb.getFxImage(), bombRange);
            if(Detonator) {
                bomb.setDetonatorPassed(true);
            }
            bombs.add(bomb);
            BombermanGame.getStillObjects().add(bomb);
            bombLimit--;
            Sound.playSfx(Sound.placeBomb);
        }
    }

    public void detonateBomb() {
        if(bombs.size() > 0){
            bombs.get(0).setDetonator(true);
        }
    }

    public void checkMove(int tileX, int tileY) {
        int diff = 4;
        int xLeft = (tileX + diff) / Sprite.SCALED_SIZE;
        int xRight = (tileX + Sprite.SCALED_SIZE - diff - 8) / Sprite.SCALED_SIZE;
        int yTop = (tileY + diff) / Sprite.SCALED_SIZE;
        int yBottom = (tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE;
        //check move
        if(WallPassed && !BombPassed){
            isMoving = (map[yTop][xLeft] == ' ' || map[yTop][xLeft] == '*') && (map[yTop][xRight] == ' ' || map[yTop][xRight] == '*')
                    && (map[yBottom][xLeft] == ' ' || map[yBottom][xLeft] == '*') && (map[yBottom][xRight] == ' ' || map[yBottom][xRight] == '*');
        }
        if(BombPassed && !WallPassed){
            isMoving = (map[yTop][xLeft] == ' ' || map[yTop][xLeft] == '%') && (map[yTop][xRight] == ' ' || map[yTop][xRight] == '%')
                    && (map[yBottom][xLeft] == ' ' || map[yBottom][xLeft] == '%') && (map[yBottom][xRight] == ' ' || map[yBottom][xRight] == '%');
        }
        if(BombPassed && WallPassed){
            isMoving = (map[yTop][xLeft] == ' ' || map[yTop][xLeft] == '%' || map[yTop][xLeft] == '*')
                    && (map[yTop][xRight] == ' ' || map[yTop][xRight] == '%' || map[yTop][xRight] == '*')
                    && (map[yBottom][xLeft] == ' ' || map[yBottom][xLeft] == '%' || map[yBottom][xLeft] == '*')
                    && (map[yBottom][xRight] == ' ' || map[yBottom][xRight] == '%' || map[yBottom][xRight] == '*');
        }
        if(!WallPassed && !BombPassed){
            isMoving = map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ';
        }
    }

    public void animateDead() {
        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, BombermanGame.currentTime / 10, 120).getFxImage();
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
        }
    }

    @Override
    public void collisionHandling() {
        int bomberLeft = this.x + 5;
        int bomberRight = this.x + 25;
        int bomberTop = this.y + 5;
        int bomberBottom = this.y + 25;
        //check collision
        for (Entity enemy : BombermanGame.getEntities()) {
            if (enemy instanceof Enemy) {
                if (((Enemy) enemy).isDead()) {
                    continue;
                }
                int enemyLeft = (int) enemy.getX();
                int enemyRight = (int) (enemy.getX() + 32);
                int enemyTop = (int) enemy.getY();
                int enemyBottom = (int) (enemy.getY() + 32);

                if (bomberRight > enemyLeft && bomberLeft < enemyRight && bomberBottom > enemyTop && bomberTop < enemyBottom) {
                    lives--;
                    isDead = true;
                    break;
                }
            }
        }

        for (Entity obj : BombermanGame.getStillObjects()) {
            int objLeft = (int) obj.getX() + 15;
            int objRight = (int) (obj.getX() + 25);
            int objTop = (int) obj.getY() + 15;
            int objBottom = (int) (obj.getY() + 25);

            if (bomberRight > objLeft && bomberLeft < objRight && bomberBottom > objTop && bomberTop < objBottom) {
                if (obj instanceof Portal && BombermanGame.getEntities().size() == 1) {
                    isNextLevel = true;
                    break;
                }
                if (obj instanceof Explosion && time == 0 && !FlamePassed) {
                    lives--;
                    isDead = true;
                    break;
                }
                if (obj instanceof Item) {
                    obj.setExist(false);
                    Sound.playSfx(Sound.takePower);
                    updateSkill(((Item) obj).getName());
                    break;
                }
            }
        }
        // check if bomber is dead
        if (isDead) {
            time = timeImmortal;
        }
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
            spacePressed.set(false);
        }

        if(Detonator){
            if(bPressed.get()){
                detonateBomb();
                bPressed.set(false);
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

            if(e.getCode() == KeyCode.B){
                bPressed.set(true);
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

        });
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isNextLevel() {
        return isNextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        isNextLevel = nextLevel;
    }

    public void resetLevel() {
        wPressed.set(false);
        aPressed.set(false);
        sPressed.set(false);
        dPressed.set(false);
        moveX = x;
        moveY = y;
    }
    public void resetDead() {
        bombLimit = 1;
        bombRange = 1;
        speed = 1;
        WallPassed = false;
        BombPassed = false;
        FlamePassed = false;
        Detonator = false;
    }

    public void updateSkill(String skill) {
        switch (skill) {
            case "speed":
                speed++;
                break;
            case "bomb":
                bombLimit++;
                break;
            case "bombRange":
                bombRange++;
                break;
            case "wallPass":
                WallPassed = true;
                break;
            case "bombPass":
                BombPassed = true;
                break;
            case "flamePass":
                FlamePassed = true;
                break;
            case "detonator":
                Detonator = true;
                break;
        }
    }

    @Override
    public void update() {
        // control bomber
        if (lives > 0 && !isDead) {
            control();
            super.update();
            collisionHandling();
        }
        // sound die
        if (time == timeImmortal) {
            Sound.playSfx(Sound.die);
        }
        // die
        if (time > 0) {
            time--;
            if (time > 300) {
                animateDead();
            } else if (time > 200) {
                img = Sprite.player_right.getFxImage();
            } else {
                control();
                super.update();
            }
        } else {
            isDead = false;
        }
        // update bombs
        List<Bomb> bombsCopy = new ArrayList<>(bombs);
        for (Bomb bomb : bombsCopy) {
            if (bomb.isExplore()) {
                bombs.remove(bomb);
                bombLimit++;
            }
        }
        if(isDead){
            resetDead();
        }
    }
}