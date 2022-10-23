package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class Portal extends Entity {

    public Portal(float xUnit, float yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (BombermanGame.noEnemyLeft()) {
            solid = false;
        }

        if (BombermanGame.noEnemyLeft() &&
                Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            BombermanGame.setSceneToShow(1);
            BombermanGame.setGameStop(true);
        }
    }

    @Override
    public void collide() {

    }
}
