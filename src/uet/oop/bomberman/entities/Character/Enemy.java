package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {
    public int moveX = x;
    public int moveY = y;
    public boolean isMoving = false;

    public int speed;
    public int score;
    public boolean isDead = false;
    public long timeMove = 5;
    public long time =timeMove;
    public long timeDead = 500;
    public int randomNumber = 1;
    public String direction = "RIGHT";

    public char[][] map = BombermanGame.getMapData();

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void changeAnimation(String direction);

    public void setMove(int x, int y) {
        moveX = x;
        moveY = y;
    }

    public abstract void checkMove(int tileX, int tileY);

    public void move(String direction) {
        int newPosX = this.x;
        int newPosY = this.y;
        switch (direction) {
            case "LEFT":
                newPosX -= speed;
                break;
            case "RIGHT":
                newPosX += speed;
                break;
            case "UP":
                newPosY -= speed;
                break;
            case "DOWN":
                newPosY += speed;
                break;
        }
        checkMove(newPosX, newPosY);
        if (isMoving) {
            changeAnimation(direction);
            setMove(newPosX, newPosY);
        }
    }

    public abstract void collisionHandling();

    public boolean isDead() {
        return isDead;
    }

    public void animateDead() {
        img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, BombermanGame.currentTime / 100, 60).getFxImage();
    }
    public boolean canBeRedirected(int x, int y) {
        return x % 32 == 0 && y % 32 == 0;
    }

    public abstract void characterMove();

    public int getScore() {
        return score;
    }

    @Override
    public void update() {
        characterMove();
        if (!isDead) {
            if (time > 0) {
                time--;
            } else {
                move(direction);
                changeAnimation(direction);
                time = timeMove;
            }
        } else {
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
        // move
        x = moveX;
        y = moveY;
        collisionHandling();
    }
}
