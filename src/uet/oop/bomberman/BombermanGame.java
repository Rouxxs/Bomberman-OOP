package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;


import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private Keyboard inputHandler = new Keyboard();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), inputHandler);
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        int test = 60;
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Const.canvasWidth, Sprite.SCALED_SIZE * Const.canvasHeight);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(inputHandler);
        scene.setOnKeyReleased(inputHandler);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        FileLevelLoad file = new FileLevelLoad(entities, stillObjects);
        file.loadLevel(1);
        file.createEntity();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                GameLoop.oldGameTime = GameLoop.currentGameTime;
                GameLoop.currentGameTime = (currentNanoTime - GameLoop.startNanoTime) / 1000000000.0;
                GameLoop.deltaTime = GameLoop.currentGameTime - GameLoop.oldGameTime;
                render();
                update();
            }
        };
        gameLoop.start();

        entities.add(bomberman);
    }

    public void createMap() {

    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    /** Kiem tra xem co cham vao Wall hoac Brick hay khong. */
    public static boolean solidTouch(float x, float y, float w, float h) {
        for (Entity e : stillObjects) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48,48)) {
                if (e instanceof Wall || e instanceof Brick)
                    return true;
            }
        }
        return false;
    }
}
