package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.MapManager;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class SoundBufferManager extends MapManager<String, SoundBuffer> {

    private long device;

    @Override
    public void setup() {
        // Music
        this.loadSound("assets/sound/ingame1.ogg", "ingame1music");
        this.loadSound("assets/sound/menu1.ogg", "menu1music");
        // Soundeffects
        this.loadSound("assets/sound/ui/click.ogg", "click");
        this.loadSound("assets/sound/ingame/coin.ogg", "coin");
        this.loadSound("assets/sound/ingame/basictowershot.ogg", "basictowershot");
        this.loadSound("assets/sound/ingame/snipertowershot.ogg", "snipertowershot");
    }

    public void loadSound(String path, String name) {
        TowerDefenceGame.theGame.getLogger().info("Loading Sound: \"" + path + "\" as name: \"" + name + "\"");
        this.content.put(name, new SoundBuffer(path));
    }

    public SoundBuffer getSoundFromName(String name) {
        return this.content.get(name);
    }

    public void initSoundSystem() {
        this.device = ALC10.alcOpenDevice((ByteBuffer) null);
        if (device == 0) {
            throw new IllegalStateException("Failed to open the default OpenAL device!");
        }

        ALCCapabilities capabilities = ALC.createCapabilities(this.device);
        long context = ALC10.alcCreateContext(this.device, (IntBuffer) null);
        if (context == 0) {
            throw new IllegalStateException("Failed to create OpenAL context!");
        }
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(capabilities);
    }

    public void cleanup() {
        for (SoundBuffer buffer : this.content.values()) {
            buffer.cleanup();
        }
        ALC10.alcCloseDevice(this.device);
    }
}
