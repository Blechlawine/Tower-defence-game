package de.marc.towerDefenceGame.tower.projectile.projectiles;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.tower.projectile.Projectile;
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
        GLUtils.drawLine(this.pos.getX(), this.pos.getY(), this.pos.getX() - this.motion.getX(), this.pos.getY() - this.motion.getY(), 2, new Color(1, 0, 1));
    }
}
