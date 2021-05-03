package de.marc.towerDefenceGame.tower;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.enemy.Enemy;
import de.marc.towerDefenceGame.enemy.EnemyComparator;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.PreUpdateEvent;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.tower.projectile.projectiles.BasicProjectile;
import de.marc.towerDefenceGame.utils.*;

import java.util.TreeSet;

public abstract class Tower implements Listener, Renderable {

    protected String name;

    protected String turretTexture, baseTexture;

    protected Vector2 middle, lookVec, pos;

    protected double range, fireRate, turnSpeed, angle;
    protected RandomRange damage;
    protected Timer attackTimer;

    protected Enemy target;
    protected TargetMode targetMode;
    protected TreeSet<Enemy> possibleTargets;
    private EnemyComparator.CompareMode compareMode = EnemyComparator.CompareMode.TRAVELLED_DISTANCE;

    public Tower(String name, String turretTexture, String baseTexture, double middleX, double middleY, double range, double fireRate, double turnSpeed, RandomRange damage, TargetMode initialTargetMode) {
        this.name = name;

        this.turretTexture = turretTexture;
        this.baseTexture = baseTexture;

        this.range = range;
        this.damage = damage;
        this.middle = new Vector2(middleX, middleY);
        this.pos = new Vector2(middleX - 8, middleY - 8);

        this.fireRate = fireRate;
        this.attackTimer = new Timer();
        this.turnSpeed = turnSpeed;

        this.target = null;
        this.targetMode = initialTargetMode;
        this.possibleTargets = new TreeSet<Enemy>(new EnemyComparator(this.compareMode));

        this.lookVec = new Vector2(1, 0);
    }

    public String getName() {
        return this.name;
    }

    public void setTargetMode(TargetMode targetMode) {
        this.targetMode = targetMode;
        switch (this.targetMode) {
            case LOWEST_HEALTH:
                this.compareMode = EnemyComparator.CompareMode.HEALTH_ASCENDING;
                break;
            case HIGHEST_HEALTH:
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
        if (!TowerDefenceGame.theGame.currentLevel.getEnemies().isEmpty()) {
            for (Enemy e : TowerDefenceGame.theGame.currentLevel.getEnemies()) {
                double distance = Vector2.duplicate(e.getMiddle()).subtract(this.middle).getLength();
                if (distance <= this.range) {
                    this.possibleTargets.add(e);
                }
            }
        }
    }

    protected void updateTarget() {
        if (!this.possibleTargets.isEmpty()) {
            if (this.target != null && this.target.getHealth() > 0) {
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

    protected void lookAtTarget() {
        this.lookVec = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle).normalize();
        this.angle = lookVec.getAngleRad();
    }

    protected boolean lookAt(Vector2 vecToLookAt, long partialMS) {
        Vector2 targetDirection = Vector2.duplicate(vecToLookAt).subtract(this.middle).normalize();
        Vector2 toTarget = Vector2.duplicate(targetDirection).subtract(this.lookVec);
        Vector2 turnVec = Vector2.duplicate(toTarget).normalize().multiply(this.turnSpeed * partialMS / 10000d);
//        TowerDefenceGame.theGame.getLogger().debug(targetDirection);
        double toTargetDist = toTarget.getLength();
        double turnDist = turnVec.getLength();
        if(toTargetDist <= turnDist) {
            this.lookVec = targetDirection;
        } else {
            this.lookVec.add(turnVec);
        }
        this.angle = this.lookVec.getAngleRad();
        double tempAngleGoal = targetDirection.getAngleDeg();
        double tempAngle = this.lookVec.getAngleDeg();
        double angleThreshold = 2;
//        TowerDefenceGame.theGame.getLogger().debug();
        if (tempAngle >= tempAngleGoal - angleThreshold && tempAngle <= tempAngleGoal + angleThreshold) {
            return true;
        } else {
            return false;
        }
    }

    protected void attackTarget() {
        Vector2 targetDirection = Vector2.duplicate(this.target.getMiddle()).subtract(this.middle).normalize();
        if (targetDirection.getX() == this.lookVec.getX() && targetDirection.getY() == this.lookVec.getY()) {
            this.target.damage(this.damage.getValue());
        }
    }

    public void onDestroyed() {
        TowerDefenceGame.theGame.getLogger().info("Tower destroyed");
    }

    public enum TargetMode {
        FIRST, RANDOM, LOWEST_HEALTH, HIGHEST_HEALTH;
    }

}
