package uet.oop.bomberman.entities.Block;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean Destroyed = false;
    private int timeToDestroy = 100;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public void animate() {
        img = Sprite.movingSprite(Sprite.brick_exploded1, Sprite.brick_exploded2, GamePlay.currentTime, 120).getFxImage();
    }

    public void setDestroyed(boolean destroyed) {
        Destroyed = destroyed;
    }

    @Override
    public void update() {
        if (Destroyed) {
            animate();
            timeToDestroy--;
            if (timeToDestroy <= 0) {
                setExist(false);
            }
        }
    }
}