package uet.oop.bomberman.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.ControlGame.CollisionManager;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomb extends Entity {
    private int timeToExplode = 20; // thời gian để bom nổ (tính bằng frame)
    private int timeAfterExplode = 5; // thời gian để đợi hết hiệu ứng nổ
    private int timeLeft = timeToExplode; // thời gian còn lại cho đến khi bom nổ
    private boolean exploded = false; // biến kiểm tra bom đã nổ hay chưa
    private int flameRange = 1; // khoảng cách tối đa mà bom có thể phá hủy
    private boolean explodedUp = false, explodedDown = false, explodedLeft = false, explodedRight = false;
    private List<Entity> stillObjects = GamePlay.getStillObjects();
    public char[][] map = GamePlay.getMapData();

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    private void explode() {
        CollisionManager collisionManager = new CollisionManager(map);
        int x = (int) Math.round((double) this.x / 32);
        int y = (int) Math.round((double) this.y / 32);
        Entity explosion = new Explosion(x, y, Sprite.bomb_exploded.getFxImage());
        stillObjects.add(explosion);

        for (int i = 1; i <= flameRange; i++) {
            if (!explodedUp) {
                if (collisionManager.canPassThrough(x, y - i)) {
                    Entity explosionUp = new ExplosionUp(x, y - i, Sprite.explosion_vertical_top_last.getFxImage());
                    stillObjects.add(explosionUp);
                } else {
                    explodedUp = true;
                }
                collisionManager.destroy(x, y - i);
            }
            if (!explodedDown) {
                if (collisionManager.canPassThrough(x, y + i)) {
                    Entity explosionDown = new ExplosionDown(x, y + i, Sprite.explosion_vertical_down_last.getFxImage());
                    stillObjects.add(explosionDown);
                } else {
                    explodedDown = true;
                }
                collisionManager.destroy(x, y + i);
            }
            if (!explodedRight) {
                if (collisionManager.canPassThrough(x + i, y)) {
                    Entity explosionRight = new ExplosionRight(x + i, y, Sprite.explosion_horizontal_right_last.getFxImage());
                    stillObjects.add(explosionRight);
                } else {
                    explodedRight = true;
                }
                collisionManager.destroy(x + i, y);
            }
            if (!explodedLeft) {
                if (collisionManager.canPassThrough(x - i, y)) {
                    Entity explosionLeft = new ExplosionLeft(x - i, y, Sprite.explosion_horizontal_left_last.getFxImage());
                    stillObjects.add(explosionLeft);
                } else {
                    explodedLeft = true;
                }
                collisionManager.destroy(x - i, y);
            }
        }

    }
    @Override
    public void update() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, GamePlay.currentTime, 120).getFxImage();
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            explode();
            timeAfterExplode--;
        }
        if (timeAfterExplode == 0) {
            for (int i = stillObjects.size() - 1; i >= 0; i--) {
                Entity entity = stillObjects.get(i);
                if (entity instanceof Explosion) {
                    stillObjects.remove(entity);
                }
            }
        }
    }
}