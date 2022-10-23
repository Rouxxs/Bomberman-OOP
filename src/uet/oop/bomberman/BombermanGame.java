package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.oop.bomberman.UI.SmallInforLabel;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.AI.AStar;
import uet.oop.bomberman.entities.Enemy.Balloon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Point;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;
import uet.oop.bomberman.entities.bomb.Bomb;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Canvas c;
    private static List<Entity> enemy = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> grass = new ArrayList<>();
    private static List<Entity> brick = new ArrayList<>();
    private static List<Entity> wall = new ArrayList<>();
    private static List<Entity> items = new ArrayList<>();
    private static List<Entity> portals = new ArrayList<>();
    private static Keyboard inputHandler = new Keyboard();
    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), inputHandler);
    private SmallInforLabel pointsLabel;
    private SmallInforLabel timesLabel;
    private static int points = 0;
    private static int time = 300;
    private int index = 120;
    private int delay = 120;
    private static int sceneToShow = 0;
    private static boolean gameOver = false;
    private static boolean gameStop = false;
    private static int level = 1;
    ImageView level2;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    @Override
    public void start(Stage stage) {
        int test = 60;
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Const.canvasWidth, Sprite.SCALED_SIZE * Const.canvasHeight);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(48);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(inputHandler);
        scene.setOnKeyReleased(inputHandler);
        scene.setFill(Color.LIGHTGREEN);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        FileLevelLoad file = new FileLevelLoad();
        file.loadLevel(level);
        file.createEntity();

        pointsLabel = new SmallInforLabel("POINT: " , Pos.TOP_LEFT);
        timesLabel = new SmallInforLabel("TIME: " , Pos.TOP_LEFT);
        pointsLabel.setLayoutX(15*48 + 14);
        pointsLabel.setLayoutY(0);
        root.getChildren().add(pointsLabel);
        root.getChildren().add(timesLabel);
        Image l2 = new Image("Level2.png");
        level2 = new ImageView(l2);
        level2.setVisible(false);
        root.getChildren().add(level2);
        //System.out.println(AStar.aStarSearch(FileLevelLoad.map, new Pair<>(1, 1), new Pair<>(1, 4)));
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                GameLoop.oldGameTime = GameLoop.currentGameTime;
                GameLoop.currentGameTime = (currentNanoTime - GameLoop.startNanoTime) / 1000000000.0;
                GameLoop.deltaTime = GameLoop.currentGameTime - GameLoop.oldGameTime;
//                System.out.println(GameLoop.deltaTime);
                switch (sceneToShow) {
                    case 0:
                        if (!gameStop && !gameOver) {
                            if(index <= 0) {
                                time--;
                                index = 120;
                                if (time == 0) {
                                    gameOver = true;
                                }
                            } else index--;
                            render();
                            update();
                        }
                        break;
                    case 1:
                        level2.setVisible(true);
                        if (delay <= 0) {
                            level2.setVisible(false);
                            nextLevel();
                        } else delay--;
                        break;
                }

            }
        };
        gameLoop.start();
    }

    public void createMap() {

    }

    public void update() {
        if (inputHandler.getActiveKeys().contains(KeyCode.ESCAPE)) {
            enemy = new ArrayList<>();
        }
        List<Entity> removes = new ArrayList<>();
        if (bomberman != null){
            bomberman.update();
            if (bomberman.isRemoved()) {
                bomberman = null;
                return;
            }
        } else return;
        for (Entity e : enemy) {
            e.update();
            if (e.isRemoved()) {
                removes.add(e);
            }
        }
        enemy.removeAll(removes);
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
        for (Entity e : items) {
            e.update();
            if (e.isRemoved()) {
                removes.add(e);
            }
        }
        items.removeAll(removes);
        portals.forEach(g -> g.update());
    }

    public void render() {
        pointsLabel.setText("Point: " + points);
        timesLabel.setText("Time: " + time);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        wall.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
        brick.forEach(g -> g.render(gc));
        bombs.forEach(g-> g.render(gc));
        enemy.forEach(g -> g.render(gc));
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

    public static boolean bombCheck(float x, float y, float w, float h) {
        if (bombs.isEmpty()) return false;
        for (Entity e : bombs) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), 48, 48)) {
                if (e.getSolid()) {
                        return true;
                    }
                }
            }
        return false;
    }

    public static void doCamera(float x) {
        if (bomberman.getX() > Const.canvasWidth * Sprite.SCALED_SIZE / 2
                && bomberman.getX() < (Const.mapWidth - Const.canvasWidth/2) * Sprite.SCALED_SIZE) {
            gc.translate(-x, 0);
        }
    }

    public static Entity getEntity(float x, float y, float w, float h) {
        for (Entity e : enemy) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), e.getW(),e.getH())) {
                return e;
            }
        }
        if (Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            return bomberman;
        }
        for (Entity e : bombs) {
            if (Const.collision(x, y, w, h, e.getX(), e.getY(), e.getW(),e.getH())) {
                return e;
            }
        }
        return null;
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

    public static void addEnemy(Entity e) {
        enemy.add(e);
    }

    public static void setGameOver(boolean b) {
        gameOver = b;
    }

    public static boolean getGameOver() {
        return gameOver;
    }

    public static void addItem(Entity e) {
        items.add(e);
    }

    public static void addPortal(Entity e) {
        portals.add(e);
    }

    public static void doPoint(int p) {
        points += p;
    }

    public static boolean noEnemyLeft() {
        return enemy.isEmpty();
    }

    public static void setSceneToShow(int sceneToShow) {
        BombermanGame.sceneToShow = sceneToShow;
    }

    public static void setGameStop(boolean b) {
        gameStop = b;
    }

    public static void nextLevel() {
        level++;
        if (level > 2) return;
        gameStop = false;
        time = 300;
        reset();
        FileLevelLoad file = new FileLevelLoad();
        file.loadLevel(level);
        file.createEntity();
        sceneToShow = 0;
    }

    private static void reset() {
        wall = new ArrayList<>();
        brick = new ArrayList<>();
        grass = new ArrayList<>();
        enemy = new ArrayList<>();
        bombs = new ArrayList<>();
        items = new ArrayList<>();
        portals = new ArrayList<>();
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), inputHandler);
    }


}
