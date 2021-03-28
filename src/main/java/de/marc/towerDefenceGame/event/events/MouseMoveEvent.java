package de.marc.towerDefenceGame.event.events;

import de.marc.towerDefenceGame.event.Event;

public class MouseMoveEvent extends Event {

    private static double absoluteX = 0, absoluteY = 0;
    private final double prevX, prevY;
    private final double dX, dY;

    public MouseMoveEvent(double x, double y) {
        this.prevX = absoluteX;
        this.prevY = absoluteY;
        absoluteX = x;
        absoluteY = y;
        this.dX = absoluteX - this.prevX;
        this.dY = absoluteY - this.prevY;
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

}
