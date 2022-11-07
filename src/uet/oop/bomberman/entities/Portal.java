package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;

public class Portal extends Entity {
    private Bomber bomberman;

    public Portal(float xUnit, float yUnit, Image img) {
        super(xUnit, yUnit, img);
        bomberman = BombermanGame.getBomberman();
    }

    @Override
    public void update() {
        if (BombermanGame.noEnemyLeft()) {
            solid = false;
        }

        if (BombermanGame.noEnemyLeft() &&
                Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            BombermanGame.setSceneToShow(2);
            BombermanGame.setGameStop(true);
            if (BombermanGame.getLevel() >= 2) {
                System.out.println("y");
                BombermanGame.setSceneToShow(4);
            }
        }
    }

    @Override
    public void collide() {

    }
}
