package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Enemy.AI.AILow;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        ai = new AILow();
        deadImg = Sprite.balloom_dead.getFxImage();
        points = 100;
        speed = 1;
        _direction = ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
            case 2:
                img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 240).getFxImage();
                break;
            case 3:
                img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 240).getFxImage();
                break;
        }
    }
}
