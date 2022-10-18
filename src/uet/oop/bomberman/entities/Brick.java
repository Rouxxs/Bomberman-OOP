package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends AnimatedEntity {
    public Brick(int x, int y, Image image) {
        super(x, y, image);
        solid = true;
    }

    @Override
    public void update() {
        if (touchFlame) {
            kill();
        }
        animate();

        if (!_alive) {
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, _animate, 30).getFxImage();
            afterKill();
        }
    }

    @Override
    public void kill() {
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) _timeAfter--;
        else this.remove();
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

}
