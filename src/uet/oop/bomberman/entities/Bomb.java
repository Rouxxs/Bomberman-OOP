package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {
    private double timeToExplode = 120;
    private int timeAfter = 20;

    private boolean exploded = false;
    private boolean allowToPassThru = true;


    public Bomb(float xUnit, float yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        float xt = BombermanGame.bomberman.getX();
        float yt = BombermanGame.bomberman.getY();

        if (!Const.collision(xt, yt, 35, 44, x, y, 48, 48)) {
            solid = true;
        }

        if (timeToExplode > 0) {
            timeToExplode--;
        }
        else {
            if (!exploded) {

            }

            if (timeAfter > 0) {
                timeAfter--;
            }
        }


        animate();
    }

    @Override
    public void render(GraphicsContext gc) {
        if(exploded) {
            img = Sprite.bomb_exploded2.getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60).getFxImage();

        }
        gc.drawImage(img, x, y);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }
}
