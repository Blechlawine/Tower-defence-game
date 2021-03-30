package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

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
//        glLineWidth(10F);
        glVertex2d(left, top);
        glVertex2d(left + width, top);
        glVertex2d(left + width, top + height);
        glVertex2d(left, top + height);
        glEnd();
        glPopMatrix();
    }

    public static void drawLine(double startX, double startY, double endX, double endY, float[] color) {
        glPushMatrix();
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_LINE_LOOP);
        glVertex2d(startX, startY);
        glVertex2d(endX, endY);
        glEnd();
        glPopMatrix();
    }

    public static void drawTexturedRect(double left, double top, double width, double height, double u, double v, double uvWidth, double uvHeight) {
//        int[] textureSize = TowerDefenceGame.theGame.getTextureHandler().getTileTextureSize();
        glPushMatrix();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        TowerDefenceGame.theGame.getTextureHandler().bindTexture();
        glBegin(GL_QUADS);

        glTexCoord2d(u, v);
        glVertex2d(left, top);

        glTexCoord2d(u + uvWidth, v);
        glVertex2d(left + width, top);

        glTexCoord2d(u + uvWidth, v + uvHeight);
        glVertex2d(left + width, top + height);

        glTexCoord2d(u, v + uvHeight);
        glVertex2d(left, top + height);

//        Das nochmal? - Nein, anscheinend nicht
//        glTexCoord2d(u, v);
//        glVertex2d(left, top);

        glEnd();

        TowerDefenceGame.theGame.getTextureHandler().unbindTexture();
        glPopMatrix();
    }

}
