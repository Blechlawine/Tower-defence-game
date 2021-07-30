package de.marc.towerDefenceGame.gameObjects.tower.projectile.projectiles;

import de.marc.towerDefenceGame.gameObjects.tower.projectile.Projectile;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class BasicProjectile extends Projectile {

    private static final double speed = 1d;
    private final Color color = new Color("8BC34A");

    public BasicProjectile(Vector2 pos, Vector2 motion, double damage, long ttl) {
        super(pos, motion, speed, damage, 1, ttl);
    }

    public static double getSpeed() {
        return speed;
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        GLUtils.rotateAroundLocation(this.motion.getAngleDeg(), this.pos);
        GLUtils.drawRectCentered(this.pos.getX(), this.pos.getY(), this.size, this.size, this.color);
        GL11.glPopMatrix();
    }
}
