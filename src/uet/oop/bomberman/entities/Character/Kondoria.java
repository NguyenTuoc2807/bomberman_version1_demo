package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Block.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Character{
    // Nhân vật có thể đi trườn qua tường
    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        lives = 2;
        speed = 1;
    }

    @Override
    public void changeAnimation(String direction) {
        switch (direction) {
            case "LEFT":
            case "UP": {
                img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "RIGHT":
            case "DOWN": {
                img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "DEAD": {
                img = Sprite.kondoria_dead.getFxImage();
                break;
            }
        }
    }

    @Override
    public void checkMove(int tileX, int tileY) {
        // Di chuyển qua brick
        double diff = 0.5;
        int xLeft = (int) ((tileX + diff) / Sprite.SCALED_SIZE);
        int xRight = (int) ((tileX + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        int yTop = (int) ((tileY + diff) / Sprite.SCALED_SIZE);
        int yBottom = (int) ((tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        //check move
        isMoving = map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' '
                && map[yTop][xLeft] == '*' && map[yTop][xRight] == '*' && map[yBottom][xLeft] == '*' && map[yBottom][xRight] == '*';
    }

    @Override
    public void collisionHandling() {
        int enemyLeft = this.x;
        int enemyRight = this.x + 32;
        int enemyTop = this.y;
        int enemyBottom = this.y + 32;
        // check collision
        for(Entity obj : BombermanGame.getStillObjects()) {
            int objLeft = (int) obj.getX();
            int objRight = (int) (obj.getX() + 32);
            int objTop = (int) obj.getY();
            int objBottom = (int) (obj.getY() + 32);
            if(objLeft < enemyRight && objRight > enemyLeft && objTop < enemyBottom && objBottom > enemyTop) {
                if(obj instanceof Explosion){
                    lives--;
                    break;
                }
            }
        }
    }
    @Override
    public void update() {

    }
}
