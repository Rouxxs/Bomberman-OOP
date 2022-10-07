package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;

public class Bomber extends AnimatedEntity {

    private float dx = 0;
    private float dy = 0;
    private int _direction;
    private boolean _moving;
    private Keyboard input;
    public Bomber(int x, int y, Image img, Keyboard keyboard) {
        super( x, y, img);
        _moving = false;
        input = keyboard;
    }

    @Override
    public void update() {
        move();
        animate();
        changeImg();
    }

    /** Di chuyển Bomber. */
    public void move() //initiates/continues movement, should be called every frame
    {
        dx = 0;
        dy = 0;
        if(input.getActiveKeys().contains(KeyCode.W)) {
            dy -= Const.MOVINGSPEED;
            _direction = 0;
        } else if(input.getActiveKeys().contains(KeyCode.S)) {
            dy = Const.MOVINGSPEED;
            _direction = 1;
        } else if(input.getActiveKeys().contains(KeyCode.A)) {
            dx -= Const.MOVINGSPEED;
            _direction = 2;
        } else if(input.getActiveKeys().contains(KeyCode.D)) {
            dx = Const.MOVINGSPEED;
            _direction = 3;
        }

        if(dx != 0 || dy != 0) {
            _moving = true;

            if (canMove(dx, 0)) {
                BombermanGame.doCamera(dx);
                x += dx;
            }
            if (canMove(0, dy)) {
                y += dy;
            }
            ///System.out.println(String.format("Bomber: (" + x + ", " + y +")"));
        } else {
            _moving = false;
        }
    }

    private boolean canMove(float x, float y) {
            float xt = (this.x + x + 1);
            float yt = (this.y + y + 1);

            //System.out.println(String.format("X:" + xt + ", Y:" + yt));

            if (BombermanGame.solidTouch(xt, yt, 35, 44)) return false;
//            if (e instanceof Wall || e instanceof Brick) {
//                return false;
//            }

        return true;
    }

    /** Đổi ảnh dựa trên hướng đi và animation. */
    private void changeImg() {
        switch (_direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animate, 40).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_down.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animate, 40).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_left.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animate, 40).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_right.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animate, 40).getFxImage();
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (_alive) {
            changeImg();
        } else {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 40).getFxImage();
        }

        gc.drawImage(img, x, y);
        gc.strokeRect(x, y, 35, 44);
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy)
        {
            kill();
        }
        return true;
    }
}
