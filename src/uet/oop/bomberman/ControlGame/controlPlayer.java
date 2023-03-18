package uet.oop.bomberman.ControlGame;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class controlPlayer {
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private final Scene scene;
    private final GraphicsContext gc;
    private final Bomber bomber;
    private final boolean[][] map;
    public int speed = 1;

    /**
     * khởi tạo điều khiển bomberman
     * @param scene input
     * @param bomber input
     * @param gc input
     * @param map input
     */
    public controlPlayer(Scene scene, Bomber bomber, GraphicsContext gc, boolean[][] map) {
        this.scene = scene;
        this.gc = gc;
        this.bomber = bomber;
        this.map = map;
    }

    /**
     * di chuyển nhân vật
     */
    public void control() {
        setControl();
        if (wPressed.get()) {
            if (checkVail("UP")) {
                bomber.animation("UP");
                gc.drawImage(Sprite.grass.getFxImage(), bomber.getX(), bomber.getY());
                bomber.setY(bomber.getY() - speed);
                bomber.render(gc);
            }
        }

        if (sPressed.get()) {
            if (checkVail("DOWN")) {
                bomber.animation("DOWN");
                gc.drawImage(Sprite.grass.getFxImage(), bomber.getX(), bomber.getY());
                bomber.setY(bomber.getY() + speed);
                bomber.render(gc);
            }
        }

        if (aPressed.get()) {
            if (checkVail("LEFT")) {
                bomber.animation("LEFT");
                gc.drawImage(Sprite.grass.getFxImage(), bomber.getX(), bomber.getY());
                bomber.setX(bomber.getX() - speed);
                bomber.render(gc);
            }
        }

        if (dPressed.get()) {
            if (checkVail("RIGHT")) {
                bomber.animation("RIGHT");
                gc.drawImage(Sprite.grass.getFxImage(), bomber.getX(), bomber.getY());
                bomber.setX(bomber.getX() + speed);
                bomber.render(gc);
            }
        }
    }

    /**
     * nhận input từ bàn phím
     */
    private void setControl() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }

            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if (e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }

            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            }
        });
    }

    /**
     * kiểm tra di chuyển
     * @param direction hướng đi
     * @return yes or no
     */
    public boolean checkVail(String direction) {
        int i = (int) bomber.getX() / Sprite.SCALED_SIZE;
        int j = (int) bomber.getY() / Sprite.SCALED_SIZE;
        switch (direction) {
            case "UP": {
                if (bomber.getX() / Sprite.SCALED_SIZE == i) {
                    if (bomber.getY() / Sprite.SCALED_SIZE > j) return map[j + 1][i];
                    return map[j - 1][i];
                } else return false;
            }
            case "DOWN": {
                if (bomber.getX() / Sprite.SCALED_SIZE == i) return map[j + 1][i];
                else return false;
            }
            case "LEFT": {
                if (bomber.getY() / Sprite.SCALED_SIZE == j) {
                    if (bomber.getX() / Sprite.SCALED_SIZE > i) return map[j][i + 1];
                    return map[j][i - 1];
                } else return false;
            }
            case "RIGHT": {
                if (bomber.getY() / Sprite.SCALED_SIZE == j) return map[j][i + 1];
                else return false;
            }
        }
        return true;
    }
}
