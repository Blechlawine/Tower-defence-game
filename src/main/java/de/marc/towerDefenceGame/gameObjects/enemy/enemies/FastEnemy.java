package de.marc.towerDefenceGame.gameObjects.enemy.enemies;

import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class FastEnemy extends Enemy {
    public FastEnemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path, double level) {
        super(positionNode, pathOffsetX, pathOffsetY, path, level, 2, 5, "fastenemy");
        this.speed = 0.2;
        this.reward = 10;
        this.score = 1;
    }
}
