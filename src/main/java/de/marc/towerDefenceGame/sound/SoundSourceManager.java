package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.MapManager;
import de.marc.towerDefenceGame.utils.Vector2;

public class SoundSourceManager extends MapManager<String, SoundSource> {
    @Override
    public void setup() {
        this.addSoundSource("ost", "ost", true);
        this.addSoundSource("coin", "coin",false);
    }

    public void addSoundSource(String name, String soundBufferName, boolean loop) {
        SoundSource soundSource = this.createSoundSource(soundBufferName, loop);
        this.content.put(name, soundSource);
    }

    public SoundSource createSoundSource(String soundBufferName, boolean loop) {
        SoundBuffer buffer = TowerDefenceGame.theGame.getSoundBufferManager().getSoundFromName(soundBufferName);
        SoundSource soundSource = new SoundSource(buffer.getBufferID(), loop, true);
        soundSource.setPosition(new Vector2(0, 0));
        return soundSource;
    }

    public SoundSource getSoundSourceFromName(String name) {
        return this.content.get(name);
    }

}
