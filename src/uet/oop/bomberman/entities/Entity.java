package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Set;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected float x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected float y;

    protected float w = 48;

    protected float h = 48;
    protected boolean _removed = false;
    protected Image img;
    protected boolean solid;
    protected boolean touchFlame = false;

    public Entity() {

    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( float xUnit, float yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
    public abstract boolean collide(Entity e);
    public void remove() {
        _removed = true;
    }

    public boolean isRemoved() {
        return _removed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean getSolid() {
        return solid;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }

    public void setTouchFlame() {
        touchFlame = true;
    }
}
