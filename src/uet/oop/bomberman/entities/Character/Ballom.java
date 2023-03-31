package uet.oop.bomberman.entities.Character;

import com.sun.deploy.appcontext.AppContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;

import static uet.oop.bomberman.BombermanGame.enemy;

public class Ballom extends Character {
    public static int swapKill = 1;

    public Ballom(){}

    public Ballom(boolean isLife, int swapImg, int count, String direction, int countToRun, int isMove) {
        super(true, 1,0,"down",0,4);
    }

    public Ballom(int x, int y, Image img) {
        super(x, y,img);
    }

    private void killBallom(Character cha) {
        if (!isLife) {
            if (swapKill == 1) {
                cha.setImg(Sprite.mob_dead1.getFxImage());
                swapKill = 2;
            } else if (swapKill == 2) {
                cha.setImg(Sprite.mob_dead2.getFxImage());
                swapKill = 3;
            } else if (swapKill == 3) {
                cha.setImg(Sprite.mob_dead3.getFxImage());
                swapKill = 4;
            } else {
                cha.setLife(false);
                enemy.remove(cha);
                swapKill = 1;
            }
        }
    }



    @Override
    public void update() {

    }



}
