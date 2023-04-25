package uet.oop.bomberman.entities.Block;

import javafx.scene.image.Image;
import uet.oop.bomberman.ControlGame.CollisionManager;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomb extends Entity {
    private final int timeToExplode = 300;
    private int timeLeft = timeToExplode;
    private boolean explore = false;
    private int flameRange;
    public boolean explosionUp = true, explosionDown = true, explosionRight = true, explosionLeft = true;
    public static List<Explosion> explosions = new ArrayList<>();

    public Bomb(int x, int y, Image img, int flameRange) {
        super(x, y, img);
        this.flameRange = flameRange;
    }

    private void explode() {
        CollisionManager collisionManager = new CollisionManager();
        int x = (int) Math.round((double) this.x / 32);
        int y = (int) Math.round((double) this.y / 32);
        Explosion explosion = new Explosion(x, y, Sprite.bomb_exploded.getFxImage());
        explosions.add(explosion);
        for (int i = 1; i <= flameRange; i++) {
            if (i == flameRange) {
                if (explosionUp) {
                    if (collisionManager.canExplode(x, y - i)) {
                        Explosion explosionUp = new ExplosionUp(x, y - i, Sprite.explosion_vertical_top_last.getFxImage());
                        explosions.add(explosionUp);
                    } else if (collisionManager.canDestroy(x, y - i)) {
                        collisionManager.destroy(x, y - i);
                    }
                }

                if (explosionDown) {
                    if (collisionManager.canExplode(x, y + i)) {
                        Explosion explosionDown = new ExplosionDown(x, y + i, Sprite.explosion_vertical_down_last.getFxImage());
                        explosions.add(explosionDown);
                    } else if (collisionManager.canDestroy(x, y + i)) {
                        collisionManager.destroy(x, y + i);
                    }
                }

                if (explosionRight) {
                    if (collisionManager.canExplode(x + i, y)) {
                        Explosion explosionRight = new ExplosionRight(x + i, y, Sprite.explosion_horizontal_right_last.getFxImage());
                        explosions.add(explosionRight);
                    } else if (collisionManager.canDestroy(x + i, y)) {
                        collisionManager.destroy(x + i, y);
                    }
                }

                if (explosionLeft) {
                    if (collisionManager.canExplode(x - i, y)) {
                        Explosion explosionLeft = new ExplosionLeft(x - i, y, Sprite.explosion_horizontal_left_last.getFxImage());
                        explosions.add(explosionLeft);
                    } else if (collisionManager.canDestroy(x - i, y)) {
                        collisionManager.destroy(x - i, y);
                    }
                }
            } else {
                if (explosionUp) {
                    if (collisionManager.canExplode(x, y - i)) {
                        Explosion explosionVertical = new ExplosionVertical(x, y - i, Sprite.explosion_horizontal.getFxImage());
                        explosions.add(explosionVertical);
                    } else if (collisionManager.canDestroy(x, y - i)) {
                        collisionManager.destroy(x, y - i);
                        explosionUp = false;
                    } else {
                        explosionUp = false;
                    }
                }

                if (explosionDown) {
                    if (collisionManager.canExplode(x, y + i)) {
                        Explosion explosionVertical = new ExplosionVertical(x, y + i, Sprite.explosion_horizontal.getFxImage());
                        explosions.add(explosionVertical);
                    } else if (collisionManager.canDestroy(x, y + i)) {
                        collisionManager.destroy(x, y + i);
                        explosionDown = false;
                    } else {
                        explosionDown = false;
                    }
                }

                if (explosionRight) {
                    if (collisionManager.canExplode(x + i, y)) {
                        Explosion explosionHorizontal = new ExplosionHorizontal(x + i, y, Sprite.explosion_vertical.getFxImage());
                        explosions.add(explosionHorizontal);
                    } else if (collisionManager.canDestroy(x + i, y)) {
                        collisionManager.destroy(x + i, y);
                        explosionRight = false;
                    } else {
                        explosionRight = false;
                    }
                }

                if (explosionLeft) {
                    if (collisionManager.canExplode(x - i, y)) {
                        Explosion explosionHorizontal = new ExplosionHorizontal(x - i, y, Sprite.explosion_vertical.getFxImage());
                        explosions.add(explosionHorizontal);
                    } else if (collisionManager.canDestroy(x - i, y)) {
                        collisionManager.destroy(x - i, y);
                        explosionLeft = false;
                    } else {
                        explosionLeft = false;
                    }
                }

            }
        }
        BombermanGame.getStillObjects().addAll(explosions);
    }

    public boolean isExplore() {
        return explore;
    }

    public void setExplore(boolean explore) {
        this.explore = explore;
    }

    public void setFlameRange(int flameRange) {
        this.flameRange = flameRange;
    }

    public static List<Explosion> getExplosions() {
        return explosions;
    }

    public void animate() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, BombermanGame.currentTime, 120).getFxImage();
    }

    @Override
    public void update() {
        animate();
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            this.setExist(false);
            setExplore(true);
            explode();
        }
    }
}