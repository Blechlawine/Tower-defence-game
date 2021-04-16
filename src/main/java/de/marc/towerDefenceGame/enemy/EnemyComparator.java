package de.marc.towerDefenceGame.enemy;

import java.util.Comparator;

public class EnemyComparator implements Comparator<Enemy> {

    private CompareMode compareMode;

    public EnemyComparator(CompareMode compareMode) {
        this.compareMode = compareMode;
    }

    public void setCompareMode(CompareMode mode) {
        this.compareMode = mode;
    }

    @Override
    public int compare(Enemy e1, Enemy e2) {
        switch (this.compareMode) {
            case TRAVELLED_DISTANCE:
                return (int) (e2.travelledDistance * 100 - e1.travelledDistance * 100);
            case HEALTH_ASCENDING:
                return (int) (e1.getHealth() - e2.getHealth());
            case HEALTH_DESCENDING:
                return (int) (e2.getHealth() - e1.getHealth());
            default:
                return 0;
        }
    }

    public enum CompareMode {
        TRAVELLED_DISTANCE, HEALTH_ASCENDING, HEALTH_DESCENDING;
    }
}
