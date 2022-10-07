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
    protected boolean _removed = false;
    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
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
}
