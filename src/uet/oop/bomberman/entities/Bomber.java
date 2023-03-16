package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public int r = 0;
    public int l = 0;
    public int d = 0;
    public int u = 0;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    /**
     * hiệu ứng khi di chuyển
     * @param direction hướng đi
     */
    public void animation(String direction) {
        switch (direction) {
            case "UP": {
                switch (u) {
                    case 2:
                        img = Sprite.player_up.getFxImage();
                        break;
                    case 6:
                        img = Sprite.player_up_1.getFxImage();
                        break;
                    case 10:
                        img = Sprite.player_up_2.getFxImage();
                        break;
                    default:
                        if (u > 12) u = 0;
                }
                u++;
                break;
            }
            case "DOWN": {
                switch (d) {
                    case 2:
                        img = Sprite.player_down.getFxImage();
                        break;
                    case 6:
                        img = Sprite.player_down_1.getFxImage();
                        break;
                    case 10:
                        img = Sprite.player_down_2.getFxImage();
                        break;
                    default:
                        if (d > 12) d = 0;
                }
                d++;
                break;
            }
            case "LEFT": {
                switch (l) {
                    case 2:
                        img = Sprite.player_left.getFxImage();
                        break;
                    case 6:
                        img = Sprite.player_left_1.getFxImage();
                        break;
                    case 10:
                        img = Sprite.player_left_2.getFxImage();
                        break;
                    default:
                        if (l > 12) l = 0;
                }
                l++;
                break;
            }
            case "RIGHT": {
                switch (r) {
                    case 2:
                        img = Sprite.player_right.getFxImage();
                        break;
                    case 6:
                        img = Sprite.player_right_1.getFxImage();
                        break;
                    case 10:
                        img = Sprite.player_right_2.getFxImage();
                        break;
                    default:
                        if (r > 12) r = 0;
                }
                r++;
                break;
            }
        }
    }

    @Override
    public void update() {

    }
}