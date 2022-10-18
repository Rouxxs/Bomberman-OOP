package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {
    protected int _direction = -1;
    protected boolean _alive = true;
    protected boolean _moving = false;
    public int _timeAfter = 40;
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntity(float xUnit, float yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }
    @Override
    public abstract void update();
    public abstract void kill();

    public int get_direction() {
        return _direction;
    }

    public void set_direction(int _direction) {
        this._direction = _direction;
    }

    protected abstract void afterKill();
}
