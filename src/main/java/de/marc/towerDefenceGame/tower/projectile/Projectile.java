package de.marc.towerDefenceGame.tower.projectile;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.enemy.Enemy;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.utils.Renderable;
import de.marc.towerDefenceGame.utils.TimerLegacy;
import de.marc.towerDefenceGame.utils.Vector2;

public abstract class Projectile implements Renderable, Listener {

    protected Vector2 pos, motion;
    protected double speed, damage, size;
    private final long ttl;
    private TimerLegacy ttlTimer;

    public Projectile(Vector2 pos, Vector2 motion, double speed, double damage, double size, long ttl) {
        this.pos = Vector2.duplicate(pos);
        this.motion = Vector2.duplicate(motion);
        this.size = size;
        this.speed = speed;
        this.damage = damage;
        this.ttl = ttl;
        this.ttlTimer = new TimerLegacy(UpdateEvent.lastMS);
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("projectiles").addElement(this);
    }

    public void onEvent(Event event) {
        if (event instanceof PreUpdateEvent) {
            PreUpdateEvent e = (PreUpdateEvent) event;
            this.ttlTimer.updateTime(e.ms);
        } else if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            Enemy collidedWith = null;
            for (Enemy enemy : TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getEnemies()) {
                Vector2 tempVec = Vector2.duplicate(this.pos);
                double distance = tempVec.subtract(enemy.getMiddle()).getLength();
                if (distance <= this.size + enemy.getSize()) {
                    collidedWith = enemy;
                    break;
                }
            }

            if (collidedWith == null) {
                this.pos.add(this.motion.normalize().multiply(this.speed * (e.partialMS / 10d)));
            } else {
                collidedWith.damage(this.damage);
                this.onDestroyed();
            }
            if (this.ttlTimer.hasReached(this.ttl)) {
                this.onDestroyed();
            }
        }
    }

    public void onDestroyed() {
        TowerDefenceGame.theGame.getEventManager().removeListener(this);
        TowerDefenceGame.theGame.getRenderer().getLayerByName("projectiles").removeElement(this);
    }

}
