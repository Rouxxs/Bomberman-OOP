package uet.oop.bomberman;

public class Const {
    public static float MOVINGSPEED =  1;
    public static enum GameStatus {
        Running,Paused,GameOver
    }

    public static int canvasWidth = 31;
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
}
