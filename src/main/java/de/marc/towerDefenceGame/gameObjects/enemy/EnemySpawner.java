package de.marc.towerDefenceGame.gameObjects.enemy;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gameObjects.enemy.enemies.BasicEnemy;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.utils.Timer;

public class EnemySpawner implements Listener {

    private int currentWave, waveHealth, waveAverageSpeed;

    private double xPos, yPos;
    private int size;
    private float spawnWidth;
    private Level level;

    private Timer spawnTimer;
    private long spawnDelayMs = 700;

    public EnemySpawner(double x, double y, int size, Level level) {
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.yPos = y;
        this.xPos = x;
        this.size = size;
        this.spawnWidth = this.size * 0.5f;
        this.level = level;
        this.spawnTimer = new Timer();
    }

    public void spawnEnemy() {
        double offsetX = Math.random() * this.spawnWidth - this.spawnWidth / 2;
        double offsetY = Math.random() * this.spawnWidth - this.spawnWidth / 2;
        this.level.getEnemies().add(new BasicEnemy(level.getPath().getNode(0), offsetX, offsetY, level.getPath()));
    }

    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            if (this.spawnTimer.hasReached(this.spawnDelayMs)) {
                this.spawnEnemy();
                this.spawnTimer.reset();
            }
        }
    }

}
