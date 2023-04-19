package uet.oop.bomberman.ControlGame;

public class CollisionManager {
    private char[][] map;

    public CollisionManager(char[][] map) {
        this.map = map;
    }

    public boolean canPassThrough(int x, int y) {
        char block = map[y][x];
        // Kiểm tra xem khối có thể vượt qua được không
        return block == '*' || block == ' ';
    }
    public void destroy(int x, int y) {
        map[y][x] = ' ';
    }
}

