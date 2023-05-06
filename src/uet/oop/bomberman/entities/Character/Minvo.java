package uet.oop.bomberman.entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AI.Astar;
import uet.oop.bomberman.entities.AI.Node;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

import static uet.oop.bomberman.BombermanGame.*;


public class Minvo extends Enemy {
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        score = 800;
    }

    @Override
    public void changeAnimation(String direction) {
        switch (direction) {
            case "LEFT":
            case "UP": {
                img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "RIGHT":
            case "DOWN": {
                img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_right2, Sprite.minvo_left3, BombermanGame.currentTime / 10, 120).getFxImage();
                break;
            }
            case "DEAD": {
                img = Sprite.minvo_dead.getFxImage();
                break;
            }
        }
    }

    @Override
    public void checkMove(int tileX, int tileY) {
        double diff = 0.5;
        int xLeft = (int) ((tileX + diff) / Sprite.SCALED_SIZE);
        int xRight = (int) ((tileX + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        int yTop = (int) ((tileY + diff) / Sprite.SCALED_SIZE);
        int yBottom = (int) ((tileY + Sprite.SCALED_SIZE - diff) / Sprite.SCALED_SIZE);
        //check move
        isMoving = map[yTop][xLeft] == ' ' && map[yTop][xRight] == ' ' && map[yBottom][xLeft] == ' ' && map[yBottom][xRight] == ' ';
    }

    @Override
    public void collisionHandling() {
        int enemyLeft = this.x;
        int enemyRight = this.x + 32;
        int enemyTop = this.y;
        int enemyBottom = this.y + 32;
        // check collision
        for (Entity obj : BombermanGame.getStillObjects()) {
            int objLeft = (int) obj.getX();
            int objRight = (int) (obj.getX() + 32);
            int objTop = (int) obj.getY();
            int objBottom = (int) (obj.getY() + 32);
            if (objLeft < enemyRight && objRight > enemyLeft && objTop < enemyBottom && objBottom > enemyTop) {
                if (obj instanceof Explosion) {
                    isDead = true;
                    break;
                }
            }
        }
    }

    public void characterMove() {
        if (canBeRedirected(this.x, this.y)) {
            Node start = new Node(this.y / 32, this.x / 32);
            Node end = new Node((int) Math.round(bomber.getY() / 32) , (int) Math.round(bomber.getX() / 32) );
            Astar astar = new Astar(height, width, start, end);
            int[][] blocksArray = new int[width * height][2];
            int countBlock = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++)
                    if (getMapData()[i][j] != ' ') {
                        blocksArray[countBlock][0] = i;
                        blocksArray[countBlock][1] = j;
                        countBlock++;
                    }
            }
            astar.setBlocks(blocksArray, countBlock);
            List<Node> path = astar.findPath();
            if (path.size() > 1 && path.size() < 6) {
                timeMove = 1;
                int nxtX = path.get(1).getCol();
                int nxtY = path.get(1).getRow();
                if (this.y / 32 > nxtY) {
                    direction = "UP";
                }

                if (this.y / 32 < nxtY) {
                    direction = "DOWN";
                }

                if (this.x / 32 > nxtX) {
                    direction = "LEFT";
                }

                if (this.x / 32 < nxtX) {
                    direction = "RIGHT";
                }
            } else {
                timeMove = 5;
                Random r = new Random();
                randomNumber = r.nextInt(4) + 1;
                if (randomNumber == 1) {
                    direction = "LEFT";
                } else if (randomNumber == 2) {
                    direction = "RIGHT";
                } else if (randomNumber == 3) {
                    direction = "UP";
                } else {
                    direction = "DOWN";
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
    }

}
