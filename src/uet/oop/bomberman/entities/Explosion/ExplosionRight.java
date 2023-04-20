package uet.oop.bomberman.entities.Explosion;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionRight extends Explosion {
    public ExplosionRight(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void animate() {
        img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, GamePlay.currentTime, 120).getFxImage();
    }

    @Override
    public void update() {
        super.update();
    }
}
