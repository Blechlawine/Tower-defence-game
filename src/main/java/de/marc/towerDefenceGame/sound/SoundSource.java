package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.openal.AL10;

public class SoundSource {

    private final int  sourceID;

    public SoundSource(int bufferID, boolean looping, boolean relative) {
        this.sourceID = AL10.alGenSources();
        if (looping) {
            AL10.alSourcei(this.sourceID, AL10.AL_LOOPING, AL10.AL_TRUE);
        }
        if (relative) {
            AL10.alSourcei(this.sourceID, AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        }
        this.setBuffer(bufferID);
    }

    public void setBuffer(int bufferID) {
        this.stop();
        AL10.alSourcei(this.sourceID, AL10.AL_BUFFER, bufferID);
    }

    public void setPosition(Vector2 pos) {
        AL10.alSource3f(this.sourceID, AL10.AL_POSITION, (float)pos.getX(), 0F, (float)pos.getY());
    }

    public void setGain(float gain) {
        AL10.alSourcef(this.sourceID, AL10.AL_GAIN, gain);
    }

    public void setProperty(int param, float value) {
        AL10.alSourcef(this.sourceID, param, value);
    }

    // Sound play controls
    public void play() {
        AL10.alSourcePlay(this.sourceID);
    }

    public void stop() {
        AL10.alSourceStop(this.sourceID);
    }

    public void pause() {
        AL10.alSourcePause(this.sourceID);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(this.sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void cleanup() {
        this.stop();
        AL10.alDeleteSources(this.sourceID);
    }

}
