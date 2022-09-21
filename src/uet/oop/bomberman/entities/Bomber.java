package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import uet.oop.bomberman.Const;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Set;

public class Bomber extends AnimatedEntity {

    private float dx = 0;
    private float dy = 0;
    private int _direction;
    private boolean _moving;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        _moving = false;
    }

    @Override
    public void update() {
        animate();
        changeImg();
    }

    /** Di chuyển Bomber. */
    public void move(Set<KeyCode> activeKeys) //initiates/continues movement, should be called every frame
    {
        dx = 0;
        dy = 0;
        if(activeKeys.contains(KeyCode.W)) {
            dy -= Const.MOVINGSPEED;
            _direction = 0;
        } else if(activeKeys.contains(KeyCode.S)) {
            dy = Const.MOVINGSPEED;
            _direction = 1;
        } else if(activeKeys.contains(KeyCode.A)) {
            dx -= Const.MOVINGSPEED;
            _direction = 2;
        } else if(activeKeys.contains(KeyCode.D)) {
            dx = Const.MOVINGSPEED;
            _direction = 3;
        }
        if(dx != 0 || dy != 0) {
            _moving = true;
            x += dx;
            y += dy;
        } else {
            _moving = false;
        }
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
        }
    }
}
