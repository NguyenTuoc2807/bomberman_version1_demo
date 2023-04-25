package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

public class BombItem extends Entity {
    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(!isExist()){
            BombermanGame.getItems().remove(this);
            BombermanGame.getStillObjects().remove(this);
        }
    }
}
