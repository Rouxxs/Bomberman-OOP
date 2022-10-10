package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Grass extends Entity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
        solid = false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
