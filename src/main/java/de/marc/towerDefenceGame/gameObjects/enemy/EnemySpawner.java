package de.marc.towerDefenceGame.gameObjects.enemy;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.gameObjects.enemy.enemies.BasicEnemy;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.UpdateEvent;
import de.marc.towerDefenceGame.gameObjects.enemy.enemies.FastEnemy;
import de.marc.towerDefenceGame.gameObjects.enemy.enemies.ToughEnemy;
import de.marc.towerDefenceGame.level.Level;
import de.marc.towerDefenceGame.utils.Timer;

import java.util.*;

public class EnemySpawner implements Listener {

    private int currentWave, enemyAmount;
    private double waveNewEnemyProbability, prevNewEnemyProbability, nextWaveHealthMultiplier;

    private Stack<String> enemiesToSpawn;
    private List<String> possibleEnemyTypes;
    private Stack<String> possibleNewEnemyTypes;

    private double xPos, yPos;
    private int size;
    private float spawnWidth;
    private Level level;

    private Timer spawnTimer;
    private Timer nextWaveTimer;
    private long nextWaveDelay = 10000;
    private long spawnDelayMs = 700;

    public EnemySpawner(double x, double y, int size, Level level) {
        this.yPos = y;
        this.xPos = x;
        this.size = size;
        this.spawnWidth = this.size * 0.5f;
        this.level = level;
        this.spawnTimer = new Timer();
        this.nextWaveTimer = new Timer();
        this.enemiesToSpawn = new Stack<>();
        this.possibleEnemyTypes = new ArrayList<>();
        this.possibleEnemyTypes.add("basic");
        this.possibleNewEnemyTypes = new Stack<>();
        this.initializePossibleNewEnemyTypes();
        // Wave starting values
        this.currentWave = 1;
        this.enemyAmount = 5;
        this.waveNewEnemyProbability = 0;
        // Starting multipliers
        this.nextWaveHealthMultiplier = 2;

        // Enemies of wave 1
        this.fillEnemySpawnStack();
    }

    public void spawnEnemy() {
        double offsetX = Math.random() * this.spawnWidth - this.spawnWidth / 2;
        double offsetY = Math.random() * this.spawnWidth - this.spawnWidth / 2;
        if (this.enemiesToSpawn.empty()) {
            if(this.nextWaveTimer.hasReached(this.nextWaveDelay)) {
                TowerDefenceGame.theGame.getLogger().debug("Next Wave");
                this.nextWave();
            }
        } else {
            Enemy tempEnemy = null;
            switch (this.enemiesToSpawn.pop().toLowerCase()) {
                case "basic":
                    tempEnemy = new BasicEnemy(level.getPath().getNode(0), offsetX, offsetY, level.getPath(), this.currentWave);
                    break;
                case "fast":
                    tempEnemy = new FastEnemy(level.getPath().getNode(0), offsetX, offsetY, level.getPath(), this.currentWave);
                    break;
                case "tough":
                    tempEnemy = new ToughEnemy(level.getPath().getNode(0), offsetX, offsetY, level.getPath(), this.currentWave);
                    break;
            }
            if (this.enemiesToSpawn.empty()) {
                this.nextWaveTimer.reset();
            }
            this.level.getEnemies().add(tempEnemy);
        }
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

    public void nextWave() {
        this.currentWave++;
        if (this.currentWave % 2 == 0) {
            this.enemyAmount += Math.max(this.currentWave / 5, 3);
        }
        this.waveNewEnemyProbability = this.currentWave / 10d - this.prevNewEnemyProbability;
        this.fillEnemySpawnStack();
    }

    private void fillEnemySpawnStack() {
        double randNewEnemy = Math.random();
        if (randNewEnemy <= this.waveNewEnemyProbability && !this.possibleNewEnemyTypes.empty()) {
            String nextEnemyType = this.possibleNewEnemyTypes.pop();
            this.possibleEnemyTypes.add(nextEnemyType);
            this.prevNewEnemyProbability = this.waveNewEnemyProbability;
        }
        int randWhichEnemy = (int) Math.round(Math.random() * (this.possibleEnemyTypes.size() - 1));
//        TowerDefenceGame.theGame.getLogger().debug(this.possibleEnemyTypes.get(randWhichEnemy), this.currentWave);
        double difficultyDependentEnemyAmount = getEnemyTypeDifficulty(this.possibleEnemyTypes.get(randWhichEnemy));
        for (double i = 0; i < this.enemyAmount; i += difficultyDependentEnemyAmount) {
            this.enemiesToSpawn.push(this.possibleEnemyTypes.get(randWhichEnemy));
        }
    }

    private double getEnemyTypeDifficulty(String type) {
        switch (type) {
            case "tough":
                return 4;
            default:
                return 1;
        }
    }

    private void initializePossibleNewEnemyTypes() {
        this.possibleNewEnemyTypes.push("tough");
        this.possibleNewEnemyTypes.push("fast");
    }
}
