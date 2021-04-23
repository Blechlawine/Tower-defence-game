package de.marc.towerDefenceGame.tower.towers;

import de.marc.towerDefenceGame.tower.Tower;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.RandomRange;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class BasicTower extends Tower {

    public BasicTower(double x, double y) {
        super("Basic tower", x, y, 200D, 2D, Math.toRadians(1), new RandomRange(2, 5), TargetMode.RANDOM);
    }

    public void render() {
        GL11.glPushMatrix();
        GLUtils.rotateAroundLocation(Math.toDegrees(this.angle), this.middle);
        GLUtils.drawTriangle(Vector2.duplicate(this.middle).add(new Vector2(0, 5)),
                Vector2.duplicate(this.middle).add(new Vector2(0, -5)),
                Vector2.duplicate(this.middle).add(new Vector2(10, 0)),
                new float[] { 0, 1, 1 });
        GL11.glPopMatrix();
    }
}
