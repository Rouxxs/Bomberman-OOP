package uet.oop.bomberman.entities.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Enemy.AI.AI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends AnimatedEntity {
    int slow = 0;
    protected AI ai;
    protected int points;
    protected Image deadImg;
    protected double speed = 1;
    protected int countCall = 0;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        animate();

        if(!_alive) {
            afterKill();
            return;
        }
        countCall = countCall > 100 ? 0 : countCall + 1;
        move();
    }
    @Override
    public void render(GraphicsContext gc) {
        if(_alive)
            chooseSprite();
        else {
            if(_timeAfter > 0) {
                img = deadImg;
                _animate = 0;
            } else {
                img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 120).getFxImage();
            }
        }
        //gc.strokeRect(x, y, 35, 44);
        gc.drawImage(img, x, y);
    }

    public void move() {
        slow = slow > 100 ? 0 : slow + 1;
        if (slow % 3 == 0)
            switch (_direction) {
                case 0:
                    goUp();
                    break;
                case 1:
                    goDown();
                    break;
                case 2:
                    goLeft();
                    break;
                case 3:
                    goRight();
                    break;
            }
//        float dx = 0;
//        float dy = 0;
//
//        if (steps <= 0) {
//            _direction = ai.calculateDirection();
//            //System.out.println(_direction);
//            steps = 48;
//        }

//        if (_direction == 0) dy -= speed;
//        if (_direction == 1) dy += speed;
//        if (_direction == 2) dx -= speed;
//        if (_direction == 3) dx += speed;

//        if (canMove(dx, dy)) {
//            _moving = true;
//            steps -= speed;
//            x += dx;
//            y += dy;
//        } else {
//            _moving = false;
//            steps = 0;
//        }
    }

    public boolean canMove(float x, float y) {
        float xt = (this.x + x);
        float yt = (this.y + y);
        //System.out.println(String.format("X:" + xt + ", Y:" + yt));

        if (BombermanGame.wallCheck(xt, yt, this.w, this.h) ||
                BombermanGame.brickCheck(xt, yt, this.w, this.h, false) ||
                BombermanGame.bombCheck(xt, yt, this.w, this.h)) {
            return false;
        }
//            if (e instanceof Wall || e instanceof Brick) {
//                return false;
//            }
        //System.out.println("yes1");
        return true;
    }



    @Override
    public void collide() {
    }

    @Override
    public void kill() {
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if(_finalAnimation > 0) --_finalAnimation;
            else
                remove();
        }
    }

    protected abstract void chooseSprite();
    public void goUp() {
        countCall = countCall > 100 ? 0 : countCall + 1;
        for (int i = 1; i <= speed; ++i) {
            y -= 1;
            if (BombermanGame.wallCheck(x, y, this.w, this.h) ||
                    BombermanGame.brickCheck(x, y, this.w, this.h, false) ||
                    BombermanGame.bombCheck(x, y, this.w, this.h)) y += 1;
            if (y % Sprite.SCALED_SIZE == 0) _direction = ai.calculateDirection();
        }

    }

    public void goDown() {
        countCall = countCall > 100 ? 0 : countCall + 1;
        for (int i = 1; i <= speed; ++i) {
            y += 1;
            if (BombermanGame.wallCheck(x, y, this.w, this.h) ||
                    BombermanGame.brickCheck(x, y, this.w, this.h, false) ||
                    BombermanGame.bombCheck(x, y, this.w, this.h)) y -= 1;
            if (y % Sprite.SCALED_SIZE == 0) _direction = ai.calculateDirection();
        }

    }

    public void goLeft() {
        countCall = countCall > 100 ? 0 : countCall + 1;
        for (int i = 1; i <= speed; ++i) {
            x -= 1;
            if (BombermanGame.wallCheck(x, y, this.w, this.h) ||
                    BombermanGame.brickCheck(x, y, this.w, this.h, false) ||
                    BombermanGame.bombCheck(x, y, this.w, this.h)) x += 1;
            if (x % Sprite.SCALED_SIZE == 0 ) _direction = ai.calculateDirection();
        }

    }

    public void goRight() {
        countCall = countCall > 100 ? 0 : countCall + 1;
        for (int i = 1; i <= speed; ++i) {
            x += 1;
            if (BombermanGame.wallCheck(x, y, this.w, this.h) ||
                    BombermanGame.brickCheck(x, y, this.w, this.h, false) ||
                    BombermanGame.bombCheck(x, y, this.w, this.h)) x -= 1;
            if (x % Sprite.SCALED_SIZE == 0 ) _direction = ai.calculateDirection();
        }

    }

    public int getPoints() {
        return points;
    }
}
