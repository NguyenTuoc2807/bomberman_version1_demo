package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class BombItem extends Item {
    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "bomb";
    }

    @Override
    public void update() {
        super.update();
    }
}
