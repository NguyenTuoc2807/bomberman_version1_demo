package uet.oop.bomberman.entities.Block;

import javafx.scene.image.Image;
import uet.oop.bomberman.ControlGame.CollisionManager;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.*;
import uet.oop.bomberman.graphics.Sprite;


public class Bomb extends Entity {
    private final int timeToExplode = 300;
    private int timeLeft = timeToExplode;
    private boolean explore = false;
    private final int flameRange = 1;
    private final char[][] map = GamePlay.getMapData();

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    private void explode() {
        CollisionManager collisionManager = new CollisionManager();
        int x = (int) Math.round((double) this.x / 32);
        int y = (int) Math.round((double) this.y / 32);
        Explosion explosion = new Explosion(x, y, Sprite.bomb_exploded.getFxImage());
        GamePlay.getStillObjects().add(explosion);
        map[y][x] = 'o';
        for (int i = 1; i <= flameRange; i++) {
            if (collisionManager.objectRecognition(x, y - i) == ' ') {
                map[y - i][x] = 'o';
                Explosion explosionUp = new ExplosionUp(x, y - i, Sprite.explosion_vertical_top_last.getFxImage());
                GamePlay.getStillObjects().add(explosionUp);
            } else if (collisionManager.objectRecognition(x, y - i) == '*') {
                collisionManager.destroy(x, y - i);
            }

            if (collisionManager.objectRecognition(x, y + i) == ' ') {
                map[y + i][x] = 'o';
                Explosion explosionDown = new ExplosionDown(x, y + i, Sprite.explosion_vertical_down_last.getFxImage());
                GamePlay.getStillObjects().add(explosionDown);
            } else if (collisionManager.objectRecognition(x, y + i) == '*') {
                collisionManager.destroy(x, y + i);
            }

            if (collisionManager.objectRecognition(x + i, y) == ' ') {
                map[y][x + i] = 'o';
                Explosion explosionRight = new ExplosionRight(x + i, y, Sprite.explosion_horizontal_right_last.getFxImage());
                GamePlay.getStillObjects().add(explosionRight);
            } else if (collisionManager.objectRecognition(x + i, y) == '*') {
                collisionManager.destroy(x + i, y);
            }

            if (collisionManager.objectRecognition(x - i, y) == ' ') {
                map[y][x - i] = 'o';
                Explosion explosionLeft = new ExplosionLeft(x - i, y, Sprite.explosion_horizontal_left_last.getFxImage());
                GamePlay.getStillObjects().add(explosionLeft);
            } else if (collisionManager.objectRecognition(x - i, y) == '*') {
                collisionManager.destroy(x - i, y);
            }
        }
    }

    public boolean isExplore() {
        return explore;
    }

    public void setExplore(boolean explore) {
        this.explore = explore;
    }

    public void animate() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, GamePlay.currentTime, 120).getFxImage();
    }

    @Override
    public void update() {
        animate();
        if (timeLeft > 0) {
            if (timeLeft == 200) {
                map[this.y / 32][this.x / 32] = '#';
            }
            timeLeft--;
        } else {
            map[this.y / 32][this.x / 32] = ' ';
            this.setExist(false);
            setExplore(true);
            explode();
        }
    }
}