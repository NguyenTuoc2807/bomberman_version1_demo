package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private int level;
    private int col;
    private int row;

    public static char[][] map;
    public Map() {

    }
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public void createMap(String path) {
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);

            String line = myReader.nextLine();
            StringTokenizer token = new StringTokenizer(line);
            level = Integer.parseInt(token.nextToken());
            row = Integer.parseInt(token.nextToken());
            col = Integer.parseInt(token.nextToken());

            map = new char[row][col];
            while (myReader.hasNextLine()) {
                for (int i = 0; i < row; i++) {
                    String data = myReader.nextLine();
                    for (int j = 0; j < col; j++) {
                        char characters = data.charAt(j);
                        Entity obj;
                        switch (characters) {
                            case '#': {
                                obj = new Wall(j , i , Sprite.wall.getFxImage());
                                map[i][j] = '#';
                                break;
                            }

                            case '*': {
                                obj = new Brick(j , i , Sprite.brick.getFxImage());
                                map[i][j] = '*';
                                break;
                            }

                            default:
                                obj = new Grass(j , i , Sprite.grass.getFxImage());
                                map[i][j] = ' ';
                                break;
                        }
                        stillObjects.add(obj);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public char[][] getMap() {
        return map;
    }
    public List<Entity> getStillObjects() {
        return stillObjects;
    }
    public List<Entity> getEntities() {
        return entities;
    }

}
