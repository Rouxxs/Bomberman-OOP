package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity{
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }
}
