package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.texture.Texture;
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

    public static void drawRect(double left, double top, double width, double height, Color color) {
        glPushMatrix();
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glBegin(GL_QUADS);
//        glLineWidth(10F);
        glVertex2d(left, top);
        glVertex2d(left + width, top);
        glVertex2d(left + width, top + height);
        glVertex2d(left, top + height);
        glEnd();
        glPopMatrix();
    }
    public static void drawRectCentered(double x, double y, double width, double height, Color color) {
        drawRect(x - width / 2d, y - height / 2d, width, height, color);
    }

    public static void drawRectOutline(double left, double top, double width, double height, float lineWidth, Color color) {
        glPushMatrix();
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glBegin(GL_LINE_LOOP);
        glLineWidth(lineWidth);
        glVertex2d(left, top);
        glVertex2d(left + width, top);
        glVertex2d(left + width, top + height);
        glVertex2d(left, top + height);
        glEnd();
        glPopMatrix();
    }

    public static void drawCircleCentered(double middleX, double middleY, double radius, int pointAmount, Color color) {
        glPushMatrix();
        glTranslated(middleX, middleY, 0);
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glBegin(GL_TRIANGLE_FAN);
        for (int i = 0; i < pointAmount; i++) {
            float angle = (float) (2F * Math.PI / pointAmount * i);
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            glVertex2d(x, y);
        }
        glEnd();
        glPopMatrix();
    }

    public static void drawCircleCenteredOutline(double middleX, double middleY, double radius, int pointAmount, float lineWidth, Color color) {
        glPushMatrix();
        glTranslated(middleX, middleY, 0);
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glLineWidth(lineWidth);
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < pointAmount; i++) {
            float angle = (float) (2F * Math.PI / pointAmount * i);
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            glVertex2d(x, y);
        }
        glEnd();
        glPopMatrix();
    }

    public static void drawLineO(double startX, double startY, double endX, double endY, float lineWidth, float opacity, Color color) {
        glPushMatrix();
        float[] colorA = color.getAsArray();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(colorA[0], colorA[1], colorA[2], opacity);
        glLineWidth(lineWidth);
        glBegin(GL_LINE_LOOP);
        glVertex2d(startX, startY);
        glVertex2d(endX, endY);
        glEnd();
        glPopMatrix();
    }

    public static void drawLine(double startX, double startY, double endX, double endY, float lineWidth, Color color) {
        glPushMatrix();
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glLineWidth(lineWidth);
        glBegin(GL_LINE_LOOP);
        glVertex2d(startX, startY);
        glVertex2d(endX, endY);
        glEnd();
        glPopMatrix();
    }

    public static void drawTexturedRect(double left, double top, double width, double height, double u, double v, double uvWidth, double uvHeight, String textureName, Color colorOverride) {
//        int[] textureSize = TowerDefenceGame.theGame.getTextureHandler().getTileTextureSize();
        glPushMatrix();
        float[] color = colorOverride.getAsArray();
        glColor3f(color[0], color[1], color[2]);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        TowerDefenceGame.theGame.getTextureHandler().bindTexture();
        TowerDefenceGame.theGame.getTextureManager().bindTexture(textureName);
        glBegin(GL_QUADS);

        glTexCoord2d(u, v);
        glVertex2d(left, top);

        glTexCoord2d(u + uvWidth, v);
        glVertex2d(left + width, top);

        glTexCoord2d(u + uvWidth, v + uvHeight);
        glVertex2d(left + width, top + height);

        glTexCoord2d(u, v + uvHeight);
        glVertex2d(left, top + height);

        glEnd();

        TowerDefenceGame.theGame.getTextureManager().unbindTexture();
        glPopMatrix();
    }

    public static void drawTriangle(Vector2 a, Vector2 b, Vector2 c, Color color) {
        glPushMatrix();
        float[] colorA = color.getAsArray();
        glColor3f(colorA[0], colorA[1], colorA[2]);
        glBegin(GL_TRIANGLES);
        glVertex2d(a.getX(), a.getY());
        glVertex2d(b.getX(), b.getY());
        glVertex2d(c.getX(), c.getY());
        glEnd();
        glPopMatrix();
    }

    public static void rotateAroundLocation(double angle, Vector2 loc) {
        glTranslated(loc.getX(), loc.getY(), 0);
        glRotated(angle, 0, 0, 1);
        glTranslated(-loc.getX(), -loc.getY(), 0);
    }

    public static void drawAnimatedTexturedRect(Vector2 pos, double width, double height, int frameCount, int currentFrame, String textureName, Color colorOverride) {
        Texture texture = TowerDefenceGame.theGame.getTextureManager().getTextureFromName(textureName);
        double uvWidth = 1D / frameCount;
        double u = uvWidth * currentFrame;
        drawTexturedRect(pos.getX(), pos.getY(), width, height, u, 0, uvWidth, 1, textureName, colorOverride);
    }

}
