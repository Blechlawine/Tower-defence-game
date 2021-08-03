package de.marc.towerDefenceGame.gameObjects.enemy.enemies;

import de.marc.towerDefenceGame.gameObjects.enemy.Enemy;
import de.marc.towerDefenceGame.level.path.Path;
import de.marc.towerDefenceGame.level.path.PathNode;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.GLUtils;

public class BasicEnemy extends Enemy {

    public BasicEnemy(PathNode positionNode, double pathOffsetX, double pathOffsetY, Path path, double level) {
        super(positionNode, pathOffsetX, pathOffsetY, path, level, 2, 10, "basicenemy");
        this.speed = 0.1;
        this.reward = 10;
        this.score = 1;
    }
}
