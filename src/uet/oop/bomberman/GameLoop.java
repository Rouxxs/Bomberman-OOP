package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import uet.oop.bomberman.entities.Entity;

import java.util.Vector;
import java.util.Iterator;



public class GameLoop {
    static double currentGameTime;
    static double oldGameTime;
    static double deltaTime;
    final static long startNanoTime = System.nanoTime();

    public static double getCurrentGameTime() {
        return currentGameTime;
    }
}

