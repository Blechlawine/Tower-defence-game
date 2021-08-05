package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class MusicManager {

    private SoundSource musicSoundSource;
    private final int bpm = 134;

    public MusicManager() {

    }

    public void setup() {
        this.musicSoundSource = TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourceFromName("music");
    }

    public void startMusic() {
//        if (!this.musicSoundSource.isPlaying()) {
//            this.musicSoundSource.play();
//        }
    }

}
