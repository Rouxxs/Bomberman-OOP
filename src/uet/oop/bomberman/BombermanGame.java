package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;


import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Bomb> bombs = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private static Keyboard inputHandler = new Keyboard();
    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), inputHandler);
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
//                System.out.println(GameLoop.deltaTime);
                render();
                update();
            }
        };
        gameLoop.start();

    }

    public void createMap() {

    }

    public void update() {
        bomberman.update();
        entities.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        //System.out.println(i--);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }

    /** Kiem tra xem co cham vao Wall hoac Brick hay khong. */
    public static boolean solidTouch(float x, float y, float w, float h) {
        for (Entity e : stillObjects) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48,48)) {
                if (e.getSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Entity getEntity(float x, float y) {
        return null;
    }

    public static void doCamera(float x) {
        if (bomberman.getX() > Const.canvasWidth * Sprite.SCALED_SIZE / 2
                && bomberman.getX() < (Const.mapWidth - Const.canvasWidth/2) * Sprite.SCALED_SIZE) {
            gc.translate(-x, 0);
        }
    }
}
