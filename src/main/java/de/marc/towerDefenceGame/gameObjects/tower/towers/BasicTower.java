package de.marc.towerDefenceGame.gameObjects.tower.towers;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.gameObjects.tower.Tower;
import de.marc.towerDefenceGame.gameObjects.tower.projectile.projectiles.BasicProjectile;
import de.marc.towerDefenceGame.sound.SoundSource;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.RandomRange;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class BasicTower extends Tower {

    public BasicTower(Tile positionTile, double x, double y) {
        super("Basic tower",
                "basicTowerTurret",
                "basicTowerBase",
                positionTile,
                x, y,
                40D,
                2D,
                1D,
                new RandomRange(2, 5),
                TargetMode.FIRST);
        this.cost = 10;
        this.soundSource = TowerDefenceGame.theGame.getSoundSourceManager().createSoundSource("basictowershot",false, SoundSource.SoundSourceCategory.SFX);
        this.soundSource.setGain(TowerDefenceGame.theGame.getSettings().sfxVolume);
    }

    public void render() {
        this.drawBaseTexture();
        if (TowerDefenceGame.theGame.getSettings().debugMode) {
            Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle);
            long time = Math.round(BasicProjectile.getSpeed() * targetDirection.getLength() * 10000);
            Vector2 predictedPos = this.target.predictPosInTime(time);
            GLUtils.drawLine(this.middle.getX(), this.middle.getY(), predictedPos.getX(), predictedPos.getY(), 2, new Color(0.5f, 0.5f, 0.5f));
        }
        GL11.glPushMatrix();
        GLUtils.rotateAroundLocation(this.angle, this.middle);
        this.drawTurretTexture();
        GL11.glPopMatrix();
        if (Tile.selectedTile == this.positionTile) {
            GLUtils.drawCircleCenteredOutline(this.middle.getX(), this.middle.getY(), this.range, 32, 1, new Color(1, 1, 1));
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            if (this.target != null) {
                Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle);
                long time = Math.round(BasicProjectile.getSpeed() * targetDirection.getLength() * 10);
                Vector2 predictedPos = this.target.predictPosInTime(time);
                boolean isLookingAtFinalPos = this.lookAt(predictedPos, e.partialMS);
//                TowerDefenceGame.theGame.getLogger().debug(predictedPos.subtract(this.target.getMiddle()));
                if (this.attackTimer.hasReached((long) (1000 / this.fireRate)) && isLookingAtFinalPos) {
                    this.soundSource.play();
                    new BasicProjectile(this.middle, Vector2.duplicate(this.lookVec.normalize()), this.damage.getValue(), time * 10);
                    this.attackTimer.reset();
                }
            }
        }
    }
}
