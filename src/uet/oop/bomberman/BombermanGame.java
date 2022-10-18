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
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;
import uet.oop.bomberman.entities.bomb.Bomb;


import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> grass = new ArrayList<>();
    private static List<Entity> brick = new ArrayList<>();
    private static List<Entity> wall = new ArrayList<>();
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
        List<Entity> removes = new ArrayList<>();
        bomberman.update();
        for (Entity e : entities) {
            e.update();
            if (e.isRemoved()) {
                removes.add(e);
            }
        }
        entities.removeAll(removes);
        for (Entity e : brick) {
            e.update();
            if (e.isRemoved()) {
                removes.add(e);
            }
        }
        brick.removeAll(removes);
        //System.out.println(i--);
        for (Entity e : bombs) {
            e.update();
            if (e.isRemoved()) {
                removes.add(e);
            }
        }
        bombs.removeAll(removes);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        wall.forEach(g -> g.render(gc));
        brick.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombs.forEach(g-> g.render(gc));
        bomberman.render(gc);
    }

    /** Kiem tra xem co cham vao Wall hoac Brick hay khong. */

    public static boolean wallCheck(float x, float y, float w, float h) {
        for (Entity e : wall) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48,48)) {
                return true;
            }
        }
        return false;
    }

    public static boolean brickCheck(float x, float y, float w, float h, boolean flame) {
        for (Entity e : brick) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48,48)) {
                if (flame) e.setTouchFlame();
                return true;
            }
        }
        return false;
    }

    public static boolean touchBrick(float x, float y, float w, float h) {
        for (Entity e : stillObjects) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48, 48)) {
                if (e.getSolid()) {
                    if (e instanceof Brick) {
                        e.remove();
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static void doCamera(float x) {
        if (bomberman.getX() > Const.canvasWidth * Sprite.SCALED_SIZE / 2
                && bomberman.getX() < (Const.mapWidth - Const.canvasWidth/2) * Sprite.SCALED_SIZE) {
            gc.translate(-x, 0);
        }
    }

    public static void addGrass(Entity e) {
        grass.add(e);
    }

    public static void addBrick(Entity e) {
        brick.add(e);
    }

    public static void addWall(Entity e) {
        wall.add(e);
    }

    public static void addBomb(Bomb b) {
        bombs.add(b);
    }
}
