package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class BombPassItem extends Item {
    public BombPassItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "bombPass";
    }

    @Override
    public void update() {
        super.update();
    }
}
