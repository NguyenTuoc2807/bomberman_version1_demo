package uet.oop.bomberman.entities.Explosion;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionHorizontal extends Explosion {
    public ExplosionHorizontal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void animate() {
        img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, GamePlay.currentTime, 120).getFxImage();
    }

    @Override
    public void update() {
        super.update();
    }
}
