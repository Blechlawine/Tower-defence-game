package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.utils.Vector2;
import org.lwjgl.openal.AL10;

public class SoundListener {

    public SoundListener() {
        this(new Vector2(0, 0));
    }

    public SoundListener(Vector2 pos) {
        AL10.alListener3f(AL10.AL_POSITION, (float)pos.getX(), 0, (float)pos.getY());
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public void setPosition(Vector2 pos) {
        AL10.alListener3f(AL10.AL_POSITION, (float)pos.getX(), 0, (float)pos.getY());
    }
}
