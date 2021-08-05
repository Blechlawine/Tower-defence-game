package de.marc.towerDefenceGame.gameObjects.enemy;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public abstract class Enemy implements Listener, Renderable {

    protected Vector2 middle, motion, pathOffset, gotoPos;
    protected double speed, health, maxHealth, level, size, travelledDistance = 0;
    private double movementAccuracy = 2;
    protected int reward, score;
    protected Path path;
    private PathNode positionNode;
    protected String textureHandle;

    public Enemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path, double level, double size, double maxHealth, String textureHandle) {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("enemies").addElement(this);
        TowerDefenceGame.theGame.getEventManager().addGameListener(this);
        this.positionNode = positionNode;
        this.pathOffset = new Vector2(pathOffsetX, pathOffsetY);
        this.path = path;
        this.size = size;
        this.maxHealth = maxHealth * (1 + this.level / 10);
        this.health = this.maxHealth;
        this.level = level;
        this.textureHandle = textureHandle;

        this.middle = new Vector2(this.positionNode.getMiddleX() + this.pathOffset.getX(), this.positionNode.getMiddleY() + this.pathOffset.getY());
        this.motion = new Vector2(0, 0);
        this.gotoPos = new Vector2(this.middle.getX(), this.middle.getY());
    }

    public Vector2 getMiddle() {
        return this.middle;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }
    
    public void setMotion(double x, double y) {
        this.motion = new Vector2(x, y);
    }

    protected void renderTexture() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.middle.getX(), this.middle.getY(), 0);
        GLUtils.rotateAroundLocation(this.motion.getAngleDeg(), new Vector2(0, 0));
        GLUtils.drawCenteredTexturedRect(0, 0, this.size*2, this.size*2, 0, 0, 1, 1, this.textureHandle, new Color(1, 1, 1));
        GL11.glPopMatrix();
    }

    public void render() {
        this.renderTexture();
        // Render Healthbar
        double barWidth = 5;
        double barHeight = 1;
        Vector2 healthbarPos = Vector2.duplicate(this.middle).add(new Vector2(-barWidth / 2, -5));
        GL11.glPushMatrix();
        GL11.glTranslated(healthbarPos.getX(), healthbarPos.getY(), 0);
        // Background
        GLUtils.drawRect(0, 0, barWidth, barHeight, new Color(0, 0, 0 ));
        // Fill
        GLUtils.drawRect(0, 0, barWidth / this.maxHealth * this.health, barHeight, new Color(1, 0, 0 ));

        GL11.glPopMatrix();
    }

    public void update(long partialMS) {
        this.middle.add(this.motion.normalize().multiply(this.speed * (partialMS / 10d)));
        this.travelledDistance += this.motion.getLength();
    }

    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            this.update(e.partialMS);
        } else if (event instanceof PreUpdateEvent) {
            PreUpdateEvent e = (PreUpdateEvent) event;
//            TowerDefenceGame.theGame.getLogger().debug(this.positionNode.getMiddleX(), this.positionNode.next.getMiddleX());
            if (this.positionNode.next != null) {
                double newGotoX = this.positionNode.next.getMiddleX() + this.pathOffset.getX();
                double newGotoY = this.positionNode.next.getMiddleY() + this.pathOffset.getY();
                this.gotoPos = new Vector2(newGotoX, newGotoY);
                double motionX = this.gotoPos.getX() - this.middle.getX();
                double motionY = this.gotoPos.getY() - this.middle.getY();
                this.setMotion(motionX, motionY);

            }
//            TowerDefenceGame.theGame.getLogger().debug(motionY, motionX);
            if (this.middle.getX() <= this.gotoPos.getX() + this.movementAccuracy
                    && this.middle.getX() >= this.gotoPos.getX() - this.movementAccuracy
                    && this.middle.getY() <= this.gotoPos.getY() + this.movementAccuracy
                    && this.middle.getY() >= this.gotoPos.getY() - this.movementAccuracy) {
                if (this.positionNode.next != null) {
                    this.positionNode = this.positionNode.next;
                } else {
                    // Enemy is at the end of the path
                    this.reachGoal();
                }
            }
        }
    }

    public Vector2 predictPosInTime(long partialMS) {
        return Vector2.duplicate(this.middle).add(this.motion.normalize().multiply(this.speed * (partialMS / 10d)));
    }

    public void onDeath() {
        TowerDefenceGame.theGame.getPlayer().addMoney(this.reward);
        TowerDefenceGame.theGame.getPlayer().addKill();
        this.remove();
    }

    public void reachGoal() {
        TowerDefenceGame.theGame.getPlayer().removeHealth(this.score);
        this.remove();
    }

    public double getSize() {
        return this.size;
    }

    public void remove() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("enemies").removeElement(this);
        TowerDefenceGame.theGame.getEventManager().removeGameListener(this);
        TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getEnemies().remove(this);
    }

    public void damage(double value) {
        this.health -= Math.min(this.health, value);
        if (this.health <= 0) {
            this.onDeath();
        }
    }
}
