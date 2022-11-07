package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.UI.SmallInforLabel;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static Scene Gamescene;
    private static GraphicsContext gc;
    private static Scene Menuscene;

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
    private static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), inputHandler);
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
    ImageView gameOverImage;
    ImageView victoryImage;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    @Override
    public void start(Stage stage) throws IOException {
        int test = 60;
       // System.out.println(getClass().getClassLoader().getResource("ui/menu.fxml").toExternalForm());
        Parent roots = FXMLLoader.load(getClass().getClassLoader().getResource("ui/menu.fxml"));
        stage.setTitle("Bomberman Game by Rou and Mika");
        Scene Menuscene = new Scene(roots);
        String css = this.getClass().getClassLoader().getResource("ui/style.css").toExternalForm();
        Menuscene.getStylesheets().add(css);

        //Them icon
        Image image = new Image("icon.png");
        stage.getIcons().add(image);

        //chay ra man hinh menu
        // Ho tro viec ALT + F4
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Const.canvasWidth, Sprite.SCALED_SIZE * Const.canvasHeight);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(48);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        setSceneToShow(0);
        // Tao scene
        Scene Gamescene = new Scene(root);
        Gamescene.setOnKeyPressed(inputHandler);
        Gamescene.setOnKeyReleased(inputHandler);
        Gamescene.setFill(Color.LIGHTGREEN);

        // Them scene vao stage
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
        Image tmp = new Image("gameover.png");
        gameOverImage = new ImageView(tmp);
        gameOverImage.setVisible(false);
        Image tmp1 = new Image("victory.png");
        victoryImage = new ImageView(tmp1);
        victoryImage.setVisible(false);

        root.getChildren().add(level2);
        root.getChildren().add(gameOverImage);
        root.getChildren().add(victoryImage);

        stage.setScene(Menuscene);
        stage.show();
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
                        stage.setScene(Menuscene);
                        break;
                    case 1:
                        if (!gameStop && !gameOver) {
                            stage.setScene(Gamescene);
                            if(index <= 0) {
                                time--;
                                index = 120;
                                if (time == 0) {
                                    gameOver = true;
                                }
                            } else index--;
                            render();
                            update();
                        } else if (gameOver) {
                            sceneToShow = 3;
                        }
                        break;
                    case 2:
                        level2.setVisible(true);
                        if (delay <= 0) {
                            level2.setVisible(false);
                            nextLevel();
                        } else delay--;
                        break;
                    case 3:
                        gameOverImage.setVisible(true);
                        ScreenController.pauseMusic();
                        break;
                    case 4:
                        victoryImage.setVisible(true);
                        ScreenController.pauseMusic();
                        break;
                }

            }
        };
        gameLoop.start();
    }

    // LOGOUT
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Do you want to exit?");
        alert.getDialogPane().getStylesheets().add(this.getClass().getClassLoader().getResource("ui/alert.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
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
        sceneToShow = 1;
    }

    public static Bomber getBomberman() {
        return bomberman;
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

    public static int getLevel() {
        return level;
    }
}
