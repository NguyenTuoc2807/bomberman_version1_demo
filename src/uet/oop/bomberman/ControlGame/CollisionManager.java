package uet.oop.bomberman.ControlGame;

import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Block.Brick;
import uet.oop.bomberman.entities.Block.Grass;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class CollisionManager {
    private char[][] map = GamePlay.getMapData();

    public CollisionManager() {
    }

    public char objectRecognition(int x, int y) {
        return map[y][x];
    }

    public void destroy(int x, int y) {
        map[y][x] = ' ';
        for (Entity entity : GamePlay.getStillObjects()) {
            if(entity instanceof Brick) {
                if (entity.getX() / 32 == x && entity.getY() / 32 == y) {
                    Brick brick = (Brick) entity;
                    brick.setDestroyed(true);
                    GamePlay.getStillObjects().add(0,new Grass(x, y, Sprite.grass.getFxImage()));
                    break;
                }
            }
        }
    }
}

