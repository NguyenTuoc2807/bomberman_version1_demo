package uet.oop.bomberman.ControlGame;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Block.Brick;
import uet.oop.bomberman.entities.Block.Grass;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class CollisionManager {
    private char[][] map = BombermanGame.getMapData();

    public CollisionManager() {
    }

    public boolean canExplode(int x, int y) {
        return map[y][x] != '#' && map[y][x] != '*';
    }

    public boolean canDestroy(int x, int y) {
        return map[y][x] == '*';
    }

    public void destroy(int x, int y) {
        map[y][x] = ' ';
        for (Entity entity : BombermanGame.getStillObjects()) {
            if (entity instanceof Brick) {
                if (entity.getX() / 32 == x && entity.getY() / 32 == y) {
                    Brick brick = (Brick) entity;
                    brick.setDestroyed(true);
                    BombermanGame.getStillObjects().add(0, new Grass(x, y, Sprite.grass.getFxImage()));
                    break;
                }
            }
        }
    }
}

