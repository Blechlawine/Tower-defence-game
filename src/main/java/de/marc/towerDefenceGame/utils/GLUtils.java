package de.marc.towerDefenceGame.utils;

import org.lwjgl.BufferUtils;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.*;
import java.nio.DoubleBuffer;

public class GLUtils {

    public static double[] getCursorPos(long window) {
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, x, y);
        return new double[] { x.get(0), y.get(0) };
    }

    public static void drawRect(double left, double top, double width, double height, float[] color) {
        glPushMatrix();
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glLineWidth(10F);
        glVertex2d(left, top);
        glVertex2d(left + width, top);
        glVertex2d(left + width, top + height);
        glVertex2d(left, top + height);
        glEnd();
        glPopMatrix();
    }

}
