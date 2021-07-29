package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.MapManager;
import de.marc.towerDefenceGame.utils.Vector2;

public class SoundSourceManager extends MapManager<String, SoundSource> {
    @Override
    public void setup() {
        this.addSoundSource("ost", "ost", new Vector2(0, 0), true, true);
    }

    public void addSoundSource(String name, String soundBufferName, Vector2 pos, boolean loop, boolean relative) {
        SoundBuffer buffer = TowerDefenceGame.theGame.getSoundBufferManager().getSoundFromName(soundBufferName);
        SoundSource soundSource = new SoundSource(buffer.getBufferID(), loop, relative);
        soundSource.setPosition(pos);
        this.content.put(name, soundSource);
    }

    public SoundSource getSoundSourceFromName(String name) {
        return this.content.get(name);
    }

}
