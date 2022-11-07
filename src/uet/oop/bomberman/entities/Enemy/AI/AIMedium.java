package uet.oop.bomberman.entities.Enemy.AI;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoad;

public class AIMedium extends AI {
    private Enemy e;
    public AIMedium(Enemy e) {
        this.e = e;
    }
    @Override
    public int calculateDirection() {
        if (BombermanGame.getBomberman() == null || BombermanGame.getBomberman().isRemoved()) return getRandomNumber(0, 4);
        int destX = (int) Math.round(BombermanGame.getBomberman().getX() / Sprite.SCALED_SIZE);
        int destY = (int) Math.round(BombermanGame.getBomberman().getY() / Sprite.SCALED_SIZE);
//        System.out.println("Bomber: " + destX + ", " + destY);

        int srcX = (int) Math.round(e.getX() / Sprite.SCALED_SIZE);
        int srcY = (int) Math.round(e.getY() / Sprite.SCALED_SIZE);
//        System.out.println(srcX + ",s " + srcY);
        int check = Math.abs(destX - srcX);

        if (check < Const.canvasWidth) {
            Pair<Integer, Integer> dir = AStar.aStarSearch(FileLevelLoad.map, new Pair<>(srcY, srcX), new Pair<>(destY, destX));
            if (dir != null) {
                int dy = dir.getKey();
                int dx = dir.getValue();
//                System.out.println(dx + ", " + dy);

                if (dy < srcY) {
                    System.out.println("up");
                    return 0;
                }
                if (dy > srcY) {
                    System.out.println("down");
                    return 1;
                }
                if (dx < srcX) {
                    System.out.println("left");
                    return 2;
                }
                if (dx > srcX) {
                    System.out.println("right");
                    return 3;
                }
            }
        }

        return getRandomNumber(0, 4);
    }
}
