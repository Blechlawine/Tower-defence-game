package de.marc.towerDefenceGame.enemy;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.enemy.enemies.BasicEnemy;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.Level;

public class EnemySpawner implements Listener {

    private int wave;

    private double xPos, yPos;
    private int size;
    private Level level;

    private boolean spawned = false;

    public EnemySpawner(double x, double y, int size, Level level) {
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.yPos = y;
        this.xPos = x;
        this.size = size;
        this.level = level;
    }

    public void spawnEnemy() {
        this.level.getEnemies().add(new BasicEnemy(level.getPath().getNode(0), 0, 0, level.getPath()));
    }

    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            if (!this.spawned) {
                this.spawnEnemy();
                this.spawned = true;
            }
        }
    }

}
