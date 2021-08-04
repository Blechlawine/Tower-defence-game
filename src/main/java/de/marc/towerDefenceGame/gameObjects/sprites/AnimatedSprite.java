package de.marc.towerDefenceGame.gameObjects.sprites;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.utils.*;

public class AnimatedSprite implements Renderable, Listener {


    private Vector2 pos;
    private String textureName, renderLayerName;
    private double width, height;
    private int fps, textureFrameCount;
    private boolean loop;

    private int currentFrame = 0;
    private Timer frameTimer;

    public AnimatedSprite(Vector2 pos, double width, double height, int fps, String textureName, int textureFrameCount,  boolean loop, String renderLayerName) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.textureName = textureName;
        this.textureFrameCount = textureFrameCount;
        this.loop = loop;
        this.renderLayerName = renderLayerName;
        this.frameTimer = new Timer();
        TowerDefenceGame.theGame.getEventManager().addGameListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName(this.renderLayerName).addElement(this);
    }

    public void destroy() {
        TowerDefenceGame.theGame.getEventManager().removeGameListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName(this.renderLayerName).removeElement(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            if (this.frameTimer.hasReached(1000 / this.fps)) {
                this.currentFrame++;
                if (!this.loop && this.currentFrame == this.textureFrameCount) {
                    this.destroy();
                }
                this.currentFrame %= this.textureFrameCount;
                this.frameTimer.reset();
            }
        }
    }

    @Override
    public void render() {
        GLUtils.drawAnimatedTexturedRect(this.pos, this.width, this.height, this.textureFrameCount, this.currentFrame, this.textureName, new Color(1, 1, 1));
    }
}
