package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Entity {
    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, GamePlay.currentTime, 120).getFxImage();
    }
}