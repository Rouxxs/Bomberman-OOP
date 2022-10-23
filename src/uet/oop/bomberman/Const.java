package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;

public class Const {
    public static int MOVINGSPEED =  1;
    public static enum GameStatus {
        Menu, Running, Paused, GameOver
    }

    public static int canvasWidth = 18;
    public static int canvasHeight = 13;

    public static int mapWidth = 31;

    public static int mapHeight = 13;


    public static boolean collision(float ax, float ay, float aw, float ah, float bx, float by, float bw, float bh){
        if (
            ax < bx + bw &&
            ax + aw > bx &&
            ay < by + bh &&
            ah + ay > by
        ) {
            return true;
        }
        return false;
    }

    public static boolean entityColision(Entity a, Entity b) {
        if (
                a.getX() < b.getX() + b.getW() &&
                        a.getX() + a.getW() > b.getX() &&
                        a.getY() < b.getY() + b.getH() &&
                        a.getH() + a.getY() > b.getY()
        ) {
            return true;
        }
        return false;
    }
}
