package de.marc.towerDefenceGame.gameObjects.tower;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.gameObjects.enemy.EnemyComparator;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;
import de.marc.towerDefenceGame.gameObjects.sprites.AnimatedSprite;
import de.marc.towerDefenceGame.level.Tile;
import de.marc.towerDefenceGame.sound.SoundSource;
import de.marc.towerDefenceGame.utils.*;

import java.util.TreeSet;

public abstract class Tower implements Listener, Renderable {

    protected String name;
    public int cost;

    protected String turretTexture, baseTexture;

    protected Vector2 middle, lookVec, pos;
    protected Tile positionTile;
    protected SoundSource soundSource;

    protected double range, fireRate, turnSpeed, angle, angleThreshold = 2;
    protected RandomRange damage;
    protected Timer attackTimer;

    protected Enemy target;
    protected TargetMode targetMode;
    protected TreeSet<Enemy> possibleTargets;
    private EnemyComparator.CompareMode compareMode = EnemyComparator.CompareMode.TRAVELLED_DISTANCE;

    public Tower(String name, String turretTexture, String baseTexture, Tile positionTile, double middleX, double middleY, double range, double fireRate, double turnSpeed, RandomRange damage, TargetMode initialTargetMode) {
        this.name = name;

        this.turretTexture = turretTexture;
        this.baseTexture = baseTexture;
        this.positionTile = positionTile;

        this.range = range;
        this.damage = damage;
        this.middle = new Vector2(middleX, middleY);
        this.pos = new Vector2(middleX - 8, middleY - 8);

        this.fireRate = fireRate;
        this.attackTimer = new Timer(true);
        this.turnSpeed = turnSpeed;

        this.target = null;
        this.targetMode = initialTargetMode;
        this.possibleTargets = new TreeSet<Enemy>(new EnemyComparator(this.compareMode));

        this.lookVec = new Vector2(1, 0);
        this.angle = this.lookVec.getAngleDeg();
    }

    public String getName() {
        return this.name;
    }

    public void setTargetMode(TargetMode targetMode) {
        this.targetMode = targetMode;
        switch (this.targetMode) {
            case WEAKEST:
                this.compareMode = EnemyComparator.CompareMode.HEALTH_ASCENDING;
                break;
            case STRONGEST:
                this.compareMode = EnemyComparator.CompareMode.HEALTH_DESCENDING;
                break;
            default:
                this.compareMode = EnemyComparator.CompareMode.TRAVELLED_DISTANCE;
                break;
        }
        this.updateTargets();
    }

    public void onEvent(Event event) {
        if (event instanceof PreUpdateEvent) {
            PreUpdateEvent e = (PreUpdateEvent) event;
            this.updateTargets();
            this.updateTarget();
        }
    }
    public abstract void render();

    protected void drawBaseTexture() {
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), 16, 16, 0, 0, 1, 1, this.baseTexture, new Color(0.5f, 0.5f, 0.5f));
    }

    protected void drawTurretTexture() {
        GLUtils.drawTexturedRect(this.pos.getX(), this.pos.getY(), 16, 16, 0, 0, 1, 1, this.turretTexture, new Color(1, 1, 1));
    }

    protected void updateTargets() {
        this.possibleTargets = new TreeSet<Enemy>(new EnemyComparator(this.compareMode));
        if (!TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getEnemies().isEmpty()) {
            for (Enemy e : TowerDefenceGame.theGame.getGameManager().getCurrentGame().getLevel().getEnemies()) {
                double distance = Vector2.duplicate(e.getMiddle()).subtract(this.middle).getLength();
                if (distance <= this.range) {
                    this.possibleTargets.add(e);
                }
            }
        }
    }

    protected void updateTarget() {
        if (!this.possibleTargets.isEmpty()) {
            if (this.target != null && this.target.getHealth() > 0 && this.getDistanceToCurrentTarget() < this.range) {
                return;
            }
            if (this.targetMode == TargetMode.RANDOM) {
                int randIndex = (int)(Math.random() * this.possibleTargets.size());
                Enemy[] temp = new Enemy[this.possibleTargets.size()];
                temp = this.possibleTargets.toArray(temp);
                this.target = temp[randIndex];
            } else {
                this.target = this.possibleTargets.first();
            }
        } else {
            this.target = null;
        }
//        TowerDefenceGame.theGame.getLogger().debug("new Target");
    }

    protected boolean lookAt(Vector2 vecToLookAt, long partialMS) {
        Vector2 targetDirection = new Vector2(vecToLookAt).subtract(this.middle).normalize();
        double targetAngle = targetDirection.getAngleDeg360();
        double angleDistance = Math.toDegrees(Math.acos(targetDirection.dot(this.lookVec) / targetDirection.getLength() * this.lookVec.getLength()));
        double currentAngle = this.lookVec.getAngleDeg360();
        double direction = -(this.lookVec.getX()*targetDirection.getY() - this.lookVec.getY()*targetDirection.getX());
        double turnAngle = (direction / Math.abs(direction)) * this.turnSpeed;
        if (angleDistance <= Math.abs(this.turnSpeed)) {
            this.lookVec.setAngleDeg(Utils.wrapAngleTo180(targetAngle));
        } else {
            double shouldAngle = Utils.wrapAngleTo180(currentAngle + turnAngle);
            this.lookVec.setAngleDeg(shouldAngle);
        }
        this.angle = this.lookVec.getAngleDeg();
        return this.angle >= targetDirection.getAngleDeg() - this.angleThreshold && this.angle <= targetDirection.getAngleDeg() + this.angleThreshold;
    }

    protected void attackTarget() {
        Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle).normalize();
        if (targetDirection.getX() == this.lookVec.getX() && targetDirection.getY() == this.lookVec.getY()) {
            this.target.damage(this.damage.getValue());
        }
    }

    protected double getDistanceToCurrentTarget() {
        if (this.target == null) {
            return -1d;
        }
        Vector2 toTarget = Vector2.duplicate(this.pos).subtract(this.target.getMiddle());
        return toTarget.getLength();
    }

    public void onDestroyed() {
//        TowerDefenceGame.theGame.getLogger().info("Tower destroyed");
        this.attackTimer.destroy();
        new AnimatedSprite(this.pos, 16, 16, 24, "dustexplosion", 12, false, "towers");
    }

    public TargetMode getTargetMode() {
        return this.targetMode;
    }

    public enum TargetMode {
        FIRST, RANDOM, WEAKEST, STRONGEST
    }

}
