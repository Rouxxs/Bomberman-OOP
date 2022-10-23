package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Const;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Image image) {
        super(x, y, image);
    }

    public void update() {
        if (Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            if (!this.isRemoved()) {
                bomberman.increSpeed();
                remove();
            }
        }
    }
}
