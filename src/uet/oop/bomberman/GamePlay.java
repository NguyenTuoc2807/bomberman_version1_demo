package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.ControlGame.Move;
import uet.oop.bomberman.ControlGame.controlPlayer;
import uet.oop.bomberman.entities.Character.Ballom;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class GamePlay extends Application {
    public static final int WIDTH = 35;
    public static final int HEIGHT = 20;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // khởi tạo map
        Map map = new Map();
        map.createMap("res/levels/level0.txt",gc);
        // khởi tạo nhân vật
        Bomber bomber = new Bomber(1,1,Sprite.player_right.getFxImage());
        bomber.render(gc);

        Ballom bal1 = new Ballom(14, 1, Sprite.balloom_right1.getFxImage());
        bal1.render(gc);
        Ballom bal2 = new Ballom(9, 6, Sprite.balloom_right1.getFxImage());
        bal2.setSwapImg(1);
        bal2.render(gc);

        // khởi tạo hàm điều khiển
        controlPlayer player =new controlPlayer(scene,bomber,gc,map.getMap());

        // Khởi tạo hàm move
        Move m = new Move(map.getMap(),  bal2, gc, 0, 0, 0 , 0);
        // khởi tạo hàm đếm thời gian
        AnimationTimer timer = new AnimationTimer() {
            private long time = 0;
            private int timeNow = 0;
            @Override
            public void handle(long l) {
                // đếm thời gian 1 giây trả về timeNow
                long temp = l - time;
                int nextTime = timeNow;
                if (temp > 1e9) {
                    timeNow++;
                    time = l;
                }
                player.control();
                m.moving();
                System.out.println(m.up);
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        Application.launch(GamePlay.class);
    }
}
