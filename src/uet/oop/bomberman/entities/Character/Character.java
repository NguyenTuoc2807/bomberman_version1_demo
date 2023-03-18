package uet.oop.bomberman.entities.Character;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Character extends Entity {
    protected boolean isLife; // Kiểm tra nhân vật sống hay đã chết
    protected int swapImg; // chuyen hoat canh nhan vat
    protected int count; // bieu thi buoc nhay hien tai cua nhan vat
    protected String direction; // bieu thi huong di chuyen cua nhan vat
    protected int countToRun; // bieu thi so frame de chuyen nhan vat chuyen trang thai chay
    protected int isMove; // bieu thi khoang cach ma doi tuong co the nhay

    public Character(){}

    public Character(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Character(boolean isLife, int swapImg, int count, String direction, int countToRun, int isMove) {
        this.direction = direction;
        this.count = count;
        this.isMove = isMove;
        this.isLife = isLife;
        this.swapImg = swapImg;
        this.countToRun = countToRun;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isLife() {
        return isLife;
    }

    public int getCount() {
        return count;
    }

    public int getCountToRun() {
        return countToRun;
    }

    public int getIsMove() {
        return isMove;
    }

    public int getSwapImg() {
        return swapImg;
    }

    public String getDirection() {
        return direction;
    }

    public void setCountToRun(int countToRun) {
        this.countToRun = countToRun;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setIsMove(int isMove) {
        this.isMove = isMove;
    }

    public void setLife(boolean life) {
        isLife = life;
    }

    public void setSwapImg(int swapImg) {
        this.swapImg = swapImg;
    }

    @Override
    public void update() {

    }
}
