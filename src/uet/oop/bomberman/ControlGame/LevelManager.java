package uet.oop.bomberman.ControlGame;

import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelManager {
    private List<String> level = new ArrayList<>();
    private int currentLevel = 0;
    private static boolean isEnd = false;
    public LevelManager() {
        level.add("res/levels/level0.txt");
        level.add("res/levels/level2.txt");
    }
    public String getLevel() {
        return level.get(currentLevel);
    }
    public void nextLevel() {
        BombermanGame.getStillObjects().clear();
        BombermanGame.getEntities().clear();
        for(int i = 0; i < BombermanGame.getMapData().length; i++) {
            Arrays.fill(BombermanGame.getMapData()[i], ' ');
        }
        currentLevel++;
    }
    public static boolean isIsEnd() {
        return isEnd;
    }
    public static void setIsEnd(boolean isEnd) {
        LevelManager.isEnd = isEnd;
    }
}
