package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Ballom extends Enemy {
    public long timeMove = 20;
    public Ballom(int x, int y, Image img) {
        super(x, y,img);
        lives = 3;
    }
    public void changeAnimation(int direction) {
        switch (direction) {
            case 1 :
            case 2: {
                img = Sprite.movingSprite(Sprite.balloom_right1,Sprite.balloom_right2,Sprite.balloom_right3,GamePlay.currentTime,120).getFxImage();
                break;
            }
            case 4:
            case 3: {
                img = Sprite.movingSprite(Sprite.balloom_left1,Sprite.balloom_left2,Sprite.balloom_left3,GamePlay.currentTime,120).getFxImage();
                break;
            }
            case 0: {
                img = Sprite.balloom_dead.getFxImage();
                break;
            }
        }
    }
    private boolean isValidMove(double tileX, double tileY) {
        // Calculate the tile coordinates of the destination position
        int destTileX = (int) tileX / Sprite.SCALED_SIZE;
        int destTileY = (int) tileY / Sprite.SCALED_SIZE;
        // Check if the destination tile is walkable
        char[][] mapData = GamePlay.getMapData();
        return mapData[destTileY][destTileX] != '#' && mapData[destTileY][destTileX] != '*';
    }

    public void setMove(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int direction) {
        int newTileX = this.x;
        int newTileY = this.y;
        switch (direction) {
            case 1:
                newTileX -= speed;
                break;
            case 2:
                newTileX += speed;
                break;
            case 3:
                newTileY -= speed;
                break;
            case 4:
                newTileY += speed;
                break;
        }
        if (isValidMove(newTileX, newTileY)) {
            changeAnimation(direction);
            setMove(newTileX, newTileY);
        }
    }
    @Override
    public void update() {
        if(lives > 0 && GamePlay.currentTime % timeMove == 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(4) + 1;
            move(randomNumber);
        }else {
            move(0);
            GamePlay.getEntities().remove(this);
        }
    }



}
