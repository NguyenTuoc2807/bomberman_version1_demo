package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
//import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.Block.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;


public class Ballom extends Character {
    public long timeMove = 5;
    public long timeDead = 300;
    public int randomNumber = 1;
    public String direction = "RIGHT";

    public Ballom(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        lives = 3;
    }

    @Override
    public void changeAnimation(String direction) {
        switch (direction) {
            case "LEFT":
            case "UP": {
                img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "RIGHT":
            case "DOWN": {
                img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "DEAD": {
                img = Sprite.balloom_dead.getFxImage();
                break;
            }
        }
    }

    @Override
    public void checkMove(int tileX, int tileY) {
        double diff = 0.5;
        int xLeft = (int) ((tileX + diff) / Sprite.SCALED_SIZE);
        int xRight = (int) ((tileX + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        int yTop = (int) ((tileY + diff) / Sprite.SCALED_SIZE);
        int yBottom = (int) ((tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        //check move
        isMoving = map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ';
    }

    @Override
    public void collisionHandling() {
        int enemyLeft = this.x;
        int enemyRight = this.x + 32;
        int enemyTop = this.y;
        int enemyBottom = this.y + 32;
        // check collision
        for (Entity obj : BombermanGame.getStillObjects()) {
            int objLeft = (int) obj.getX();
            int objRight = (int) (obj.getX() + 32);
            int objTop = (int) obj.getY();
            int objBottom = (int) (obj.getY() + 32);
            if (objLeft < enemyRight && objRight > enemyLeft && objTop < enemyBottom && objBottom > enemyTop) {
                if (obj instanceof Explosion) {
                    lives--;
                    break;
                }
            }
        }
    }

    public boolean canBeRedirected(int x, int y) {
        return x % 32 == 0 && y % 32 == 0;
    }

    public void animateDead() {
        img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, BombermanGame.currentTime / 100, 60).getFxImage();
    }


    @Override
    public void update() {
        if (canBeRedirected(x, y)) {
            Random r = new Random();
            randomNumber = r.nextInt(4) + 1;
            if (randomNumber == 1) {
                direction = "LEFT";
            } else if (randomNumber == 2) {
                direction = "RIGHT";
            } else if (randomNumber == 3) {
                direction = "UP";
            } else {
                direction = "DOWN";
            }
        }
        if (lives > 0) {
            if (timeMove > 0) {
                timeMove--;
            } else {
                move(direction);
                changeAnimation(direction);
                timeMove = 5;
            }
        } else {
            isDead = true;
            if (timeDead > 0) {
                if (timeDead >= 200) {
                    changeAnimation("DEAD");
                }
                timeDead--;
                animateDead();
            } else {
                setExist(false);
            }
        }
        super.update();
        collisionHandling();
    }

}
