package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class FlameItem extends Item {
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "bombRange";
    }

    @Override
    public void update() {
        super.update();
    }
}
