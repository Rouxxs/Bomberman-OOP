package uet.oop.bomberman.level;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLevelLoad {
    private int width;
    private int height;
    private int level;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public static char[][] map;

    public FileLevelLoad(List<Entity> entities, List<Entity> stillObjects) {
        this.entities = entities;
        this.stillObjects = stillObjects;
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
                        Wall w =  new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(w);
                        break;
                    case '*':
                        Brick b = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(b);
                        break;

                    default:
                        Grass g = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(g);
                        break;
                }
            }
            //System.out.println("");
        }
    }
}