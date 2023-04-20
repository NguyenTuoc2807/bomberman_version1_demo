package uet.oop.bomberman.entities.Explosion;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Entity {
    public int timeToExplode = 100;
    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }

    public void animate() {
        img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, GamePlay.currentTime, 120).getFxImage();
    }
    @Override
    public void update() {
        animate();
        if(timeToExplode > 0) {
            timeToExplode--;
        }else {
            GamePlay.getMapData()[this.y / 32][this.x / 32] = ' ';
            setExist(false);
        }
    }
}