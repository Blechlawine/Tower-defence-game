package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.render.Camera;
import de.marc.towerDefenceGame.utils.Vector2;

public class MouseMoveEvent extends Event {

    private static double absoluteX = 0, absoluteY = 0, mapPosX, mapPosY;
    private final double prevX, prevY;
    private final double dX, dY;

    public MouseMoveEvent(double x, double y) {
        this.prevX = absoluteX;
        this.prevY = absoluteY;
        absoluteX = x;
        absoluteY = y;
        this.dX = absoluteX - this.prevX;
        this.dY = absoluteY - this.prevY;
        Vector2 camPos = TowerDefenceGame.theGame.getRenderer().getLayerByName("level").getCameraPos();
        Vector2 camOrigin = TowerDefenceGame.theGame.getRenderer().getLayerByName("level").getCameraOrigin();
        double camScale = TowerDefenceGame.theGame.getRenderer().getLayerByName("level").getCameraScale();
        mapPosX = (absoluteX - camOrigin.getX()) / camScale - camPos.getX();
        mapPosY = (absoluteY - camOrigin.getY()) / camScale - camPos.getY();
    }

    /***
     * Returns x and y position of mouse with the camera transforms applied
     * @param camera camera of which to use the transforms
     * @return double
     */
    public static double[] getCameraTransformedPos(Camera camera) {
        Vector2 camPos = camera.getPos();
        Vector2 camOrigin = camera.getOrigin();
        double camScale = camera.getScale();
        double x = (absoluteX - camOrigin.getX()) / camScale - camPos.getX();
        double y = (absoluteY - camOrigin.getY()) / camScale - camPos.getY();
        return new double[] {x, y};
    }

    /***
     * Amount the mouse moved in the x-direction (delta)
     * @return double
     */
    public double getDX() {
        return this.dX;
    }

    /***
     * Amount the mouse moved in the y-direction (delta)
     * @return double
     */
    public double getDY() {
        return this.dY;
    }

    /***
     * Current mouse x-Position
     * @return double
     */
    public static double getAbsoluteY() {
        return absoluteY;
    }

    /***
     * Current mouse y-Position
     * @return double
     */
    public static double getAbsoluteX() {
        return absoluteX;
    }


    public static double getMapPosX() {
        return mapPosX;
    }

    public static double getMapPosY() {
        return mapPosY;
    }
}
