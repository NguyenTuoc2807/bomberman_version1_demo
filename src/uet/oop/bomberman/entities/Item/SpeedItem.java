package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public String getName() {
        return "speed";
    }

    @Override
    public void update() {
        super.update();
    }
}
