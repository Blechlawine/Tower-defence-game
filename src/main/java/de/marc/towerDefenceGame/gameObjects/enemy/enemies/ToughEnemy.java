package de.marc.towerDefenceGame.gameObjects.enemy.enemies;

import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class ToughEnemy extends Enemy {
    public ToughEnemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path) {
        super(positionNode, pathOffsetX, pathOffsetY, path, 3);
        this.speed = 0.04;
        this.maxHealth = 200;
        this.health = this.maxHealth;
        this.reward = 20;
        this.score = 2;
    }

    public void render() {
        GLUtils.drawCircleCentered(this.getMiddle().getX(), this.getMiddle().getY(), 2, 16, new Color(1, 0, 0 ));
        super.render();
    }
}
