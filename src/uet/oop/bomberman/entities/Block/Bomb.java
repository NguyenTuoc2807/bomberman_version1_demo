package uet.oop.bomberman.entities.Block;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ControlGame.CollisionManager;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.*;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private final int timeToExplode = 300;
    private int timeLeft = timeToExplode;
    private boolean explore = false;
    private final int flameRange;
    private boolean detonator = false;
    private boolean detonatorPassed = false;
    public boolean explosionUp = true, explosionDown = true, explosionRight = true, explosionLeft = true;
    CollisionManager collisionManager = new CollisionManager();

    public Bomb(int x, int y, Image img, int flameRange) {
        super(x, y, img);
        this.flameRange = flameRange;
    }

    private void explode() {
        int x = (int) Math.round((double) this.x / 32);
        int y = (int) Math.round((double) this.y / 32);
        Sound.playSfx(Sound.explosion);
        Explosion explosion = new Explosion(x, y, Sprite.bomb_exploded.getFxImage());
        BombermanGame.getStillObjects().add(explosion);
        for (int i = 1; i <= flameRange; i++) {
            if (i == flameRange) {
                if (explosionUp) {
                    if (collisionManager.canExplode(x, y - i)) {
                        Explosion explosionUp = new ExplosionUp(x, y - i, Sprite.explosion_vertical_top_last.getFxImage());
                        BombermanGame.getStillObjects().add(explosionUp);
                    } else if (collisionManager.canDestroy(x, y - i)) {
                        collisionManager.destroy(x, y - i);
                    }
                }

                if (explosionDown) {
                    if (collisionManager.canExplode(x, y + i)) {
                        Explosion explosionDown = new ExplosionDown(x, y + i, Sprite.explosion_vertical_down_last.getFxImage());
                        BombermanGame.getStillObjects().add(explosionDown);
                    } else if (collisionManager.canDestroy(x, y + i)) {
                        collisionManager.destroy(x, y + i);
                    }
                }

                if (explosionRight) {
                    if (collisionManager.canExplode(x + i, y)) {
                        Explosion explosionRight = new ExplosionRight(x + i, y, Sprite.explosion_horizontal_right_last.getFxImage());
                        BombermanGame.getStillObjects().add(explosionRight);
                    } else if (collisionManager.canDestroy(x + i, y)) {
                        collisionManager.destroy(x + i, y);
                    }
                }

                if (explosionLeft) {
                    if (collisionManager.canExplode(x - i, y)) {
                        Explosion explosionLeft = new ExplosionLeft(x - i, y, Sprite.explosion_horizontal_left_last.getFxImage());
                        BombermanGame.getStillObjects().add(explosionLeft);
                    } else if (collisionManager.canDestroy(x - i, y)) {
                        collisionManager.destroy(x - i, y);
                    }
                }
            } else {
                if (explosionUp) {
                    if (collisionManager.canExplode(x, y - i)) {
                        Explosion explosionVertical = new ExplosionVertical(x, y - i, Sprite.explosion_horizontal.getFxImage());
                        BombermanGame.getStillObjects().add(explosionVertical);
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
                        BombermanGame.getStillObjects().add(explosionVertical);
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
                        BombermanGame.getStillObjects().add(explosionHorizontal);
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
                        BombermanGame.getStillObjects().add(explosionHorizontal);
                    } else if (collisionManager.canDestroy(x - i, y)) {
                        collisionManager.destroy(x - i, y);
                        explosionLeft = false;
                    } else {
                        explosionLeft = false;
                    }
                }

            }
        }
    }

    public boolean isExplore() {
        return explore;
    }

    public void setExplore(boolean explore) {
        this.explore = explore;
    }

    public void animate() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, BombermanGame.currentTime/10, 60).getFxImage();
    }

    public void collisionHandling() {
        int bombLeft = this.x;
        int bombRight = this.x + 30;
        int bombTop = this.y;
        int bombBottom = this.y + 30;
        for (Entity obj : BombermanGame.getEntities()) {
            if (obj instanceof Bomber) {
                int objLeft = (int) obj.getX();
                int objRight = (int) (obj.getX() + 32);
                int objTop = (int) obj.getY();
                int objBottom = (int) (obj.getY() + 32);
                if (bombRight > objLeft && bombLeft < objRight && bombBottom > objTop && bombTop < objBottom) {
                    break;
                } else {
                    collisionManager.setMap(x / 32, y / 32, '%');
                }
            }

        }
        for (Entity obj : BombermanGame.getStillObjects()) {
            if (obj instanceof Explosion) {
                int objLeft = (int) obj.getX();
                int objRight = (int) (obj.getX() + 32);
                int objTop = (int) obj.getY();
                int objBottom = (int) (obj.getY() + 32);
                if (bombRight > objLeft && bombLeft < objRight && bombBottom > objTop && bombTop < objBottom) {
                    explore = true;
                }
            }
        }
    }

    public void setDetonatorPassed(boolean detonatorPassed) {
        this.detonatorPassed = detonatorPassed;
    }

    public void setDetonator(boolean detonator) {
        this.detonator = detonator;
    }

    @Override
    public void update() {
        animate();
        collisionHandling();
        if (detonatorPassed){
            if(detonator || explore){
                this.setExist(false);
                collisionManager.setMap(x / 32, y / 32, ' ');
                setExplore(true);
                explode();
            }
        } else if (timeLeft > 0 && !explore) {
            timeLeft--;
        } else {
            this.setExist(false);
            collisionManager.setMap(x / 32, y / 32, ' ');
            setExplore(true);
            explode();
        }
    }
}