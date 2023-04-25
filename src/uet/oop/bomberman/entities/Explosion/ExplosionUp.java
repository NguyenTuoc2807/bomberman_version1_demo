package uet.oop.bomberman.entities.Explosion;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionUp extends Explosion {
    public ExplosionUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void animate() {
        img = Sprite.movingSprite(Sprite.explosion_vertical_top_last,Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, BombermanGame.currentTime, 120).getFxImage();
    }

    @Override
    public void update() {
        super.update();
    }
}
