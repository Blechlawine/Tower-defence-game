package de.marc.towerDefenceGame.gameObjects.tower.towers;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.gameObjects.tower.Tower;
import de.marc.towerDefenceGame.sound.SoundSource;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;
import de.marc.towerDefenceGame.utils.RandomRange;
import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.opengl.GL11;

public class SniperTower extends Tower {

    private float shotOpacity = 0F;
    private Vector2 lastTargetPosition, lastAttackVec;

    public SniperTower(Tile positionTile, double middleX, double middleY) {
        super("Snipertower",
                "sniperTowerTurret",
                "sniperTowerBase",
                positionTile,
                middleX, middleY,
                200D,
                0.3d,
                0.5,
                new RandomRange(50, 75),
                TargetMode.FIRST);
        this.cost = 20;
        this.soundSource = TowerDefenceGame.theGame.getSoundSourceManager().createSoundSource("snipertowershot",false, SoundSource.SoundSourceCategory.SFX);
        this.soundSource.setGain(TowerDefenceGame.theGame.getSettings().sfxVolume);
    }

    @Override
    public void render() {
        this.drawBaseTexture();
        if (this.lastTargetPosition != null) {
            Vector2 shotLineStart = new Vector2(this.middle).add(new Vector2(this.lastAttackVec).multiply(8));
            float lineWidth = 1 * (float) TowerDefenceGame.theGame.getPlayer().getScale();
            GLUtils.drawLineO(shotLineStart.getX(), shotLineStart.getY(), this.lastTargetPosition.getX(), this.lastTargetPosition.getY(), lineWidth, this.shotOpacity, new Color("42A5F5"));
            if (this.shotOpacity > 0) {
                this.shotOpacity -= 0.05F;
            }
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
                Vector2 targetPos = this.target.getMiddle();
                boolean isLookingAtFinalPos = this.lookAt(targetPos, e.partialMS);
                if (this.attackTimer.hasReached((long) (1000 / this.fireRate)) && isLookingAtFinalPos) {
                    this.shotOpacity = 1F;
                    this.soundSource.play();
                    this.target.damage(this.damage.getValue());
                    this.lastTargetPosition = Vector2.duplicate(this.target.getMiddle());
                    this.lastAttackVec = new Vector2(this.lookVec);
                    this.attackTimer.reset();
                }
            }
        }
    }
}
