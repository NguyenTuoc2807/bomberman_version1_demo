package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Map {
    public int level;
    public int col;
    public int row;

    public boolean[][] map;

    public void createMap(String path, GraphicsContext gc) {
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);

            String line = myReader.nextLine();
            StringTokenizer token = new StringTokenizer(line);
            level = Integer.parseInt(token.nextToken());
            row = Integer.parseInt(token.nextToken());
            col = Integer.parseInt(token.nextToken());
            map = new boolean[row][col];
            for (int u = 0; u < row; u++) {
                for (int v = 0; v < col; v++) {
                    map[u][v] = true;
                }
            }
            while (myReader.hasNextLine()) {
                for (int i = 0; i < row; i++) {
                    String data = myReader.nextLine();
                    for (int j = 0; j < col; j++) {
                        char characters = data.charAt(j);
                        switch (characters) {
                            case 'x': {
                                gc.drawImage(Sprite.portal.getFxImage(), j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE);
                                break;
                            }
                            case '#': {
                                gc.drawImage(Sprite.wall.getFxImage(), j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE);
                                map[i][j] = false;
                                break;
                            }

                            case '*': {
                                gc.drawImage(Sprite.brick.getFxImage(), j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE);
                                map[i][j] = false;
                                break;
                            }

                            default:
                                gc.drawImage(Sprite.grass.getFxImage(), j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE);
                                break;
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean[][] getMap() {
        return map;
    }
}
