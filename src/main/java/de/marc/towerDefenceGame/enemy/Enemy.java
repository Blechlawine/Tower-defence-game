package de.marc.towerDefenceGame.enemy;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public abstract class Enemy implements Listener, Renderable {

    private Vector2 middle, motion, pathOffset, gotoPos;
    protected double speed, health, maxHealth, travelledDistance = 0;
    private double movementAccuracy = 2;
    protected int reward, score;
    protected Path path;
    private PathNode positionNode;

    public Enemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path) {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("enemies").addElement(this);
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.positionNode = positionNode;
        this.pathOffset = new Vector2(pathOffsetX, pathOffsetY);
        this.path = path;

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
    
    public void setMotion(double x, double y) {
        this.motion = new Vector2(x, y).normalize().multiply(this.speed);
    }

    public void render() {
        // Render Healthbar
        double barWidth = 5;
        double barHeight = 1;
        Vector2 healthbarPos = Vector2.duplicate(this.middle).add(new Vector2(-barWidth / 2, -5));
        GL11.glPushMatrix();
        GL11.glTranslated(healthbarPos.getX(), healthbarPos.getY(), 0);
        // Background
        GLUtils.drawRect(0, 0, barWidth, barHeight, new float[] { 0, 0, 0 });
        // Fill
        GLUtils.drawRect(0, 0, barWidth / this.maxHealth * this.health, barHeight, new float[] { 1, 0, 0 });

        GL11.glPopMatrix();
    }

    public void update(long partialMS) {
        this.middle.add(this.motion);
        this.travelledDistance += this.motion.getLength();
    }

    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            this.update(e.partialMS);
        } else if (event instanceof PreUpdateEvent) {
            PreUpdateEvent e = (PreUpdateEvent) event;
            if (this.middle.getX() <= this.gotoPos.getX() + this.movementAccuracy
                    && this.middle.getX() >= this.gotoPos.getX() - this.movementAccuracy
                    && this.middle.getY() <= this.gotoPos.getY() + this.movementAccuracy
                    && this.middle.getY() >= this.gotoPos.getY() - this.movementAccuracy) {
                if (this.positionNode.next != null) {
//                    TowerDefenceGame.theGame.getLogger().debug(this.positionNode.getMiddleX(), this.positionNode.next.getMiddleX());
                    double newGotoX = this.positionNode.next.getMiddleX() + this.pathOffset.getX();
                    double newGotoY = this.positionNode.next.getMiddleY() + this.pathOffset.getY();
                    double motionX = newGotoX - this.middle.getX();
                    double motionY = newGotoY - this.middle.getY();
                    this.gotoPos = new Vector2(newGotoX, newGotoY);
//                    TowerDefenceGame.theGame.getLogger().debug(newGotoX, newGotoY, this.pos.getX(), this.pos.getY());
                    this.setMotion(motionX, motionY);
                    this.positionNode = this.positionNode.next;
                } else {
                    // Enemy is at the end of the path
                    this.reachGoal();
                }
            }
        }
    }

    public void onDeath() {
        TowerDefenceGame.theGame.getPlayer().addMoney(this.reward);
        this.remove();
    }

    public void reachGoal() {
        TowerDefenceGame.theGame.getPlayer().removeHealth(this.score);
        this.remove();
    }

    public void remove() {
        TowerDefenceGame.theGame.getRenderer().getLayerByName("enemies").removeElement(this);
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
        TowerDefenceGame.theGame.currentLevel.getEnemies().remove(this);
    }

    public void damage(double value) {
        this.health -= Math.min(this.health, value);
        if (this.health <= 0) {
            this.onDeath();
        }
    }
}
