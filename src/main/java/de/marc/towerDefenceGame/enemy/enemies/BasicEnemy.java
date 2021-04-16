package de.marc.towerDefenceGame.enemy.enemies;

import de.marc.towerDefenceGame.enemy.Enemy;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.GLUtils;

public class BasicEnemy extends Enemy {

    public BasicEnemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path) {
        super(positionNode, pathOffsetX, pathOffsetY, path);
        this.speed = 0.1;
        this.maxHealth = 100;
        this.health = this.maxHealth;
    }

    public void render() {
        GLUtils.drawCircleCentered(this.getMiddle().getX(), this.getMiddle().getY(), 2, 16, new float[] { 1, 1, 0 });
        super.render();
    }
}
