package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public Item(int x, int y, Image image) {
        super(x, y, image);
        solid = false;
        w = 45;
        h = 45;
    }
    @Override
    public void update() {

    }

    @Override
    public void collide() {

    }
}
