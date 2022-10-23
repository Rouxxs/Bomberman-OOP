package uet.oop.bomberman.level;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.Balloon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLevelLoad {
    private int width;
    private int height;
    private int level;
    public static char[][] map;

    public FileLevelLoad() {
    }
    public void loadLevel(int level) {
        try {
             FileReader file = new FileReader("res/levels/Level" + Integer.toString(level) + ".txt");
             Scanner sc = new Scanner(file);
             this.level = sc.nextInt();
             height = sc.nextInt();
             width = sc.nextInt();
             sc.nextLine();
             map = new char[height][width];

             for (int i = 0; i < height; i++) {
                 String line = sc.nextLine();
                 for (int j = 0; j < width; j++) {
                     map[i][j] = line.charAt(j);
                     //System.out.println(line.charAt(j));
                 }
             }
            sc.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createEntity() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = map[i][j];
                //System.out.print(c);
                switch (c) {
                    case '#':
                        BombermanGame.addWall(new Wall(j, i, Sprite.wall.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case '*':
                        BombermanGame.addBrick(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case '1':
                        BombermanGame.addEnemy(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case '2':
                        BombermanGame.addEnemy(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case 'b':
                        BombermanGame.addBrick(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.addItem(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case 'f':
                        BombermanGame.addBrick(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.addItem(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case 's':
                        BombermanGame.addBrick(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.addItem(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case 'x':
                        BombermanGame.addBrick(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.addPortal(new Portal(j, i, Sprite.portal.getFxImage()));
                        BombermanGame.addGrass(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    default:
                        Grass grass1 = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.addGrass(grass1);
                        break;
                }
            }
            //System.out.println("");
        }
    }

    public static char[][] getMap() {
        return map;
    }

    public static void setMap(char[][] m) {
        map = m;
    }
}