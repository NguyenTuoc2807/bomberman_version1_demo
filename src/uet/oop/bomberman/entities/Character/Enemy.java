package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;

public abstract class Enemy extends Entity {
    public int lives;
    public int speed;
    public char[][] map = GamePlay.getMapData();
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    @Override
    public void update() {

    }
}
