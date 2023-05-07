package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class WallPassItem extends Item {
    public WallPassItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "wallPass";
    }

    @Override
    public void update() {
        super.update();
    }
}