package uet.oop.bomberman.ControlGame;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Character.Ballom;
import uet.oop.bomberman.entities.Character.Character;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

public class Move {
    public Character character;
    public final boolean[][] map;
    public int up;
    public int down;
    public int left;
    public int right;
    public GraphicsContext gc;



    public Move(boolean[][] map, Character character, GraphicsContext gc, int up, int down, int left, int right) {
        this.map = map;
        this.character = character;
        this.gc = gc;
        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
    }

    public void Direction(String direction, double IsMove) {
        switch (direction) {
            case "UP" : {
                up_step();
                up++;
                character.setY(character.getY() + IsMove);
            }
            case "DOWN" : {
                down_step();
                down++;
                character.setY(character.getY() - IsMove);
            }
            case "LEFT" : {
                left_step();
                left++;
                character.setX(character.getX() + IsMove);
            }
            case "RIGHT" : {
                right_step();
                right++;
                character.setX(character.getX() - IsMove);
            }
        }
    }

    public boolean checkVail(String direction) {
        int i = (int) character.getX() / Sprite.SCALED_SIZE;
        int j = (int) character.getY() / Sprite.SCALED_SIZE;
        switch (direction) {
            case "UP": {
                if (character.getX() / Sprite.SCALED_SIZE == i) {
                    if (character.getY() / Sprite.SCALED_SIZE > j) return map[j + 1][i];
                    return map[j - 1][i];
                } else return false;
            }
            case "DOWN": {
                if (character.getX() / Sprite.SCALED_SIZE == i) return map[j + 1][i];
                else return false;
            }
            case "LEFT": {
                if (character.getY() / Sprite.SCALED_SIZE == j) {
                    if (character.getX() / Sprite.SCALED_SIZE > i) return map[j][i + 1];
                    return map[j][i - 1];
                } else return false;
            }
            case "RIGHT": {
                if (character.getY() / Sprite.SCALED_SIZE == j) return map[j][i + 1];
                else return false;
            }
        }
        return true;
    }
    public void moving() {
        if (character instanceof Ballom) {
            if (checkVail("UP") ) {
                character.setDirection("UP");
                gc.drawImage(Sprite.grass.getFxImage(), character.getX(), character.getY());
                Direction("UP",0.5);
                character.render(gc);
            }

            if (checkVail("DOWN")) {
                character.setDirection("DOWN");
                gc.drawImage(Sprite.grass.getFxImage(), character.getX(), character.getY());
                Direction("DOWN",0.5);
                character.render(gc);

            }

            if (checkVail("LEFT")) {
                character.setDirection("LEFT");
                gc.drawImage(Sprite.grass.getFxImage(), character.getX(), character.getY());
                Direction("LEFT",0.5);
                character.render(gc);
            }

            if (checkVail("RIGHT")) {
                character.setDirection("RIGHT");
                gc.drawImage(Sprite.grass.getFxImage(), character.getX(), character.getY());
                Direction("RIGHT",0.5);
                character.render(gc);
            }



        }
    }
    public void down_step() {
        if (character instanceof Ballom) {
            switch (down) {
                case 15 : {
                    character.setImg(Sprite.balloom_right1.getFxImage());
                    break;
                }
                case 30: {
                    character.setImg(Sprite.balloom_right2.getFxImage());
                    break;
                }
                case 45: {
                    character.setImg(Sprite.balloom_right3.getFxImage());
                    break;
                }
                default:{
                    if(down > 45) down = 0;
                    break;
                }
            }

        }
    }

    public void up_step() {
        if (character instanceof Ballom) {
            switch (up) {
                case 15 : {
                    character.setImg(Sprite.balloom_left1.getFxImage());
                    break;
                }
                case 30: {
                    character.setImg(Sprite.balloom_left2.getFxImage());
                    break;
                }
                case 45: {
                    character.setImg(Sprite.balloom_left3.getFxImage());
                    break;
                }
                default:{
                    if (up > 45) up = 0;
                }
            }
        }
    }

    public void right_step() {
        if (character instanceof Ballom) {
            switch (right) {
                case 15 : {
                    character.setImg(Sprite.balloom_right1.getFxImage());
                    break;
                }
                case 30: {
                    character.setImg(Sprite.balloom_right2.getFxImage());
                    break;
                }
                case 45: {
                    character.setImg(Sprite.balloom_right3.getFxImage());
                    break;
                }
                default:{
                    if (right > 45) right = 0;
                }
            }

        }
    }

    public void left_step() {
        if (character instanceof Ballom) {
            switch (left) {
                case 15 : {
                    character.setImg(Sprite.balloom_right1.getFxImage());
                    break;
                }
                case 30: {
                    character.setImg(Sprite.balloom_right2.getFxImage());;
                    break;
                }
                case 45: {
                    character.setImg(Sprite.balloom_right3.getFxImage());
                    break;
                }
                default:{
                    if (left > 45) left = 0;
                    break;
                }
            }

        }
    }
}
