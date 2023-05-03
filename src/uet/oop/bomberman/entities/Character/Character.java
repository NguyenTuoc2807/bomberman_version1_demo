package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

public abstract class Character extends Entity {
    public int moveX = x;
    public int moveY = y;
    public boolean isMoving = false;
    public int lives;
    public int speed;
    public char[][] map = BombermanGame.getMapData();

    public Character(int xUnit, int yUnit, Image img) {
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

    public void setMap(char[][] map) {
        this.map = map;
    }

    @Override
    public void update() {
        checkMove(x, y);
        // move
        x = moveX;
        y = moveY;
    }
}
