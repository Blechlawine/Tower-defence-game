package de.marc.towerDefenceGame.sound;

import de.marc.towerDefenceGame.utils.FileUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbisInfo;
import java.nio.ShortBuffer;

public class SoundBuffer {

    private final int bufferID;

    public SoundBuffer(String file) {
        this.bufferID = AL10.alGenBuffers();
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = FileUtils.readVorbis(file, 32 * 1024, info);

            AL10.alBufferData(this.bufferID, info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, pcm, info.sample_rate());
        } catch (Exception e) {

        }
    }

    public int getBufferID() {
        return this.bufferID;
    }

    public void cleanup() {
        AL10.alDeleteBuffers(this.bufferID);
    }

}
