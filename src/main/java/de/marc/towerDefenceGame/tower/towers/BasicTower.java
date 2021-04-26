package de.marc.towerDefenceGame.tower.towers;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.tower.Tower;
import de.marc.towerDefenceGame.tower.projectile.projectiles.BasicProjectile;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.RandomRange;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class BasicTower extends Tower {

    public BasicTower(double x, double y) {
        super("Basic tower", x, y, 200D, 2D, Math.toRadians(1), new RandomRange(2, 5), TargetMode.FIRST);
    }

    public void render() {

        this.drawBaseTexture("basicTowerBase");
        if (TowerDefenceGame.theGame.getRenderDebugStuff()) {
            Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle);
            long time = Math.round(BasicProjectile.getSpeed() * targetDirection.getLength() * 10000);
            Vector2 predictedPos = this.target.predictPosInTime(time);
            GLUtils.drawLine(this.middle.getX(), this.middle.getY(), predictedPos.getX(), predictedPos.getY(), 2, new float[] {0.5f, 0.5f, 0.5f});
        }
        GL11.glPushMatrix();
        GLUtils.rotateAroundLocation(Math.toDegrees(this.angle), this.middle);
        this.drawTurretTexture("basicTowerTurret");
//        GLUtils.drawTriangle(Vector2.duplicate(this.middle).add(new Vector2(0, 5)),
//                Vector2.duplicate(this.middle).add(new Vector2(0, -5)),
//                Vector2.duplicate(this.middle).add(new Vector2(10, 0)),
//                new float[] { 0, 1, 1 });
        GL11.glPopMatrix();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            if (this.target != null) {
                Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle);
                long time = Math.round(BasicProjectile.getSpeed() * targetDirection.getLength() * 10000);
                Vector2 predictedPos = this.target.predictPosInTime(time);
                this.lookAt(predictedPos, e.partialMS);
//                TowerDefenceGame.theGame.getLogger().debug(predictedPos.subtract(this.target.getMiddle()));
                if (this.attackTimer.hasReached((long) (1000 / this.fireRate))) {
                    new BasicProjectile(this.middle, Vector2.duplicate(this.lookVec.normalize()), this.damage.getValue(), time / 100);
                    this.attackTimer.reset();
                }
            }
        }
    }
}
