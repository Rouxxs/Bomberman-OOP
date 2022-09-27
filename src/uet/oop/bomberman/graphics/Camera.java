package uet.oop.bomberman.graphics;

import uet.oop.bomberman.Const;

public class Camera {
    /**
     * the number of tiles in x-direction (width)
     */
    protected int numTilesX;
    /**
     * the number of tiles in y-direction (height)
     */
    protected int numTilesY;

    /**
     * the height of the map in pixel
     */
    protected int mapHeight;

    /**
     * the width of the map in pixel
     */
    protected int mapWidth;

    /**
     * the width of one tile of the map in pixel
     */
    protected int tileWidth;

    /**
     * the height of one tile of the map in pixel
     */
    protected int tileHeight;
    /**
     * the x-position of camera in pixel
     */
    protected float cameraX;

    /**
     * the y-position of camera in pixel
     */
    protected float cameraY;

    protected Point currentCenterPoint = new Point(0, 0);

    public Camera() {
        numTilesX = Const.canvasWidth;
        numTilesY = Const.canvasHeight;

        tileHeight = Sprite.DEFAULT_SIZE;
        tileWidth = Sprite.DEFAULT_SIZE;

        mapHeight = numTilesX * tileHeight;
        mapHeight = numTilesY * tileWidth;
    }

    public void centerOn(float x, float y) {
        cameraX = x - numTilesX / 2;
        cameraY = y - numTilesY / 2;

        //if the camera is at the right or left edge lock it to prevent a black bar
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraX + numTilesX > mapWidth) {
            cameraX = mapWidth - numTilesX;
        }

        //if the camera is at the top or bottom edge lock it to prevent a black bar
        if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraY + numTilesY > mapHeight) {
            cameraY = mapHeight - numTilesY;
        }
    }


}
