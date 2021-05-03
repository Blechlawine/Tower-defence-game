package de.marc.towerDefenceGame.player.tools;

import de.marc.towerDefenceGame.level.Tile;

public interface Tool {

    void use(Tile target, int mouseButton);

    void deactivate();
}
