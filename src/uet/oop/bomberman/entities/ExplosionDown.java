package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionDown extends Explosion{
    public ExplosionDown(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, GamePlay.currentTime, 120).getFxImage();
    }
}

