package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;

public class MusicManager {

    private SoundSource menuMusicSoundSource, ingameMusicSoundSource;
    private final int bpm = 134;

    public MusicManager() {

    }

    public void setup() {
        this.menuMusicSoundSource = TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourceFromName("menu1music");
        this.ingameMusicSoundSource = TowerDefenceGame.theGame.getSoundSourceManager().getSoundSourceFromName("ingame1music");
        this.menuMusicSoundSource.setGain(TowerDefenceGame.theGame.getSettings().musicVolume);
        this.ingameMusicSoundSource.setGain(TowerDefenceGame.theGame.getSettings().musicVolume);
    }

    public void startMenuMusic() {
        if (this.ingameMusicSoundSource.isPlaying()) {
            this.ingameMusicSoundSource.stop();
        }
        if (!this.menuMusicSoundSource.isPlaying() && !this.ingameMusicSoundSource.isPaused()) {
            this.menuMusicSoundSource.play();
        }
    }

    public void startIngameMusic() {
        if (this.menuMusicSoundSource.isPlaying()) {
            this.menuMusicSoundSource.stop();
        }
        if (!this.ingameMusicSoundSource.isPlaying()) {
            this.ingameMusicSoundSource.play();
        }
    }

    public void pauseIngameMusic() {
        if (this.ingameMusicSoundSource.isPlaying()) {
            this.ingameMusicSoundSource.pause();
        }
    }

    public void unpauseIngameMusic() {
        if (!this.ingameMusicSoundSource.isPlaying()) {
            this.ingameMusicSoundSource.play();
        }
    }
}
