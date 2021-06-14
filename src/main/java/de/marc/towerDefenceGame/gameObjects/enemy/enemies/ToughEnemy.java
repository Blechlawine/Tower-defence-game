package de.marc.towerDefenceGame.gameObjects.enemy.enemies;

import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class ToughEnemy extends Enemy {
    public ToughEnemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path, double level) {
        super(positionNode, pathOffsetX, pathOffsetY, path, level, 3, 80);
        this.speed = 0.04;
        this.reward = 20;
        this.score = 2;
    }

    public void render() {
        GLUtils.drawCircleCentered(this.getMiddle().getX(), this.getMiddle().getY(), 2, 16, new Color(1, 0, 0 ));
        super.render();
    }
}
