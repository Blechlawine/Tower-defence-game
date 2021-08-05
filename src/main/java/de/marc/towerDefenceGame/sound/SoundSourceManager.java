package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.MapManager;
import de.marc.towerDefenceGame.utils.Vector2;

import java.util.HashMap;
import java.util.Map;

public class SoundSourceManager extends MapManager<String, SoundSource> {
    @Override
    public void setup() {
        this.addSoundSource("menu1music", "menu1music", false, SoundSource.SoundSourceCategory.MUSIC);
        this.addSoundSource("ingame1music", "ingame1music", false, SoundSource.SoundSourceCategory.MUSIC);
        this.addSoundSource("coin", "coin",false, SoundSource.SoundSourceCategory.SFX);
    }

    public void addSoundSource(String name, String soundBufferName, boolean loop, SoundSource.SoundSourceCategory category) {
        SoundSource soundSource = this.createSoundSource(soundBufferName, loop, category);
        this.content.put(name, soundSource);
    }

    public SoundSource createSoundSource(String soundBufferName, boolean loop, SoundSource.SoundSourceCategory category) {
        SoundBuffer buffer = TowerDefenceGame.theGame.getSoundBufferManager().getSoundFromName(soundBufferName);
        SoundSource soundSource = new SoundSource(buffer.getBufferID(), loop, true, category);
        soundSource.setPosition(new Vector2(0, 0));
        return soundSource;
    }

    public SoundSource getSoundSourceFromName(String name) {
        return this.content.get(name);
    }

    public HashMap<String, SoundSource> getSoundSourcesFromCategory(SoundSource.SoundSourceCategory category) {
        HashMap<String, SoundSource> result = new HashMap<>();
        for (Map.Entry<String, SoundSource> entry : this.content.entrySet()) {
            if (entry.getValue().category == category) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public void cleanup() {
        for (SoundSource source : this.content.values()) {
            source.cleanup();
        }
    }
}
