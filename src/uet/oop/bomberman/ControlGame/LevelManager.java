package uet.oop.bomberman.ControlGame;

import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private static List<String> level = new ArrayList<>();
    private int currentLevel = 0;

    public LevelManager() {
        level.add("res/levels/level1.txt");
        level.add("res/levels/level2.txt");
        level.add("res/levels/level3.txt");
        level.add("res/levels/level4.txt");
        level.add("res/levels/level5.txt");
    }

    public String getLevel() {
        return level.get(currentLevel);
    }

    public void nextLevel() {
        BombermanGame.getStillObjects().clear();
        BombermanGame.getEntities().clear();
        currentLevel++;
    }

    public boolean victory() {
        return currentLevel == level.size() ;
    }

}
