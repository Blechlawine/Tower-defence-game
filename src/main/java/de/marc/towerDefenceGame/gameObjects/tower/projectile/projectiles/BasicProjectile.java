package de.marc.towerDefenceGame.gameObjects.tower.projectile.projectiles;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gameObjects.tower.projectile.Projectile;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;

public class BasicProjectile extends Projectile {

    private static double speed = 1d;

    public BasicProjectile(Vector2 pos, Vector2 motion, double damage, long ttl) {
        super(pos, motion, speed, damage, 0.5, ttl);
    }

    public static double getSpeed() {
        return speed;
    }

    @Override
    public void render() {
        float lineWidth = 1 * (float)TowerDefenceGame.theGame.getPlayer().getScale();
        GLUtils.drawLine(this.pos.getX(), this.pos.getY(), this.pos.getX() - this.motion.getX(), this.pos.getY() - this.motion.getY(), lineWidth, new Color("8BC34A"));
    }
}
