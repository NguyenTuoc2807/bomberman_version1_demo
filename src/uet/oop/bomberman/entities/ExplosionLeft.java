package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class ExplosionLeft extends Explosion{
    int t = 0;
    public ExplosionLeft(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        switch (t) {
            case 2:
                img = Sprite.explosion_horizontal_left_last.getFxImage();
                break;
            case 6:
                img = Sprite.explosion_horizontal_left_last1.getFxImage();
                break;
            case 10:
                img = Sprite.explosion_horizontal_left_last2.getFxImage();
                break;
            default:
                if (t > 12) t = 0;
        }
        t++;
    }
}

