package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionVertical extends Explosion {
    int t = 0;
    public ExplosionVertical(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        switch (t) {
            case 2:
                img = Sprite.explosion_vertical.getFxImage();
                break;
            case 6:
                img = Sprite.explosion_vertical1.getFxImage();
                break;
            case 10:
                img = Sprite.explosion_vertical2.getFxImage();
                break;
            default:
                if (t > 12) t = 0;
        }
        t++;
    }
}
