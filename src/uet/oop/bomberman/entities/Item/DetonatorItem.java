package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class DetonatorItem extends Item {
    public DetonatorItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "detonator";
    }

    @Override
    public void update() {
        super.update();
    }
}
