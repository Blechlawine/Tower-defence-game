package de.marc.towerDefenceGame.utils;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.texture.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.stb.STBVorbisInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.stb.STBVorbis.*;

public class FileUtils {

    public static JSONObject readJSONResource(String fileName) {
        InputStream stream = getFileFromResourceAsStream(fileName);

        JSONTokener tokener = new JSONTokener(stream);
        return new JSONObject(tokener);
    }

    public static void writeJSONFile(String fileName, JSONObject content) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                TowerDefenceGame.theGame.getLogger().info(String.format("File created: %s", fileName));
            } else {
                TowerDefenceGame.theGame.getLogger().info(String.format("File already exists: %s", fileName));
            }
            TowerDefenceGame.theGame.getLogger().info(String.format("Writing file: %s", fileName));
            FileWriter writer = new FileWriter(fileName);
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if(inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        } else {
            return inputStream;
        }
    }

    public static File getFileFromResource(String resourceName) throws URISyntaxException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + resourceName);
        } else {
            return new File(resource.toURI());
        }
    }

    public static Element readXMLFile(String fileName) {
        InputStream stream = getFileFromResourceAsStream(fileName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);
            return document.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Texture readTexturePNG(String fileName, String name) {
        InputStream stream = getFileFromResourceAsStream(fileName);
        try {
            PNGDecoder decoder = new PNGDecoder(stream);
            ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            // Buffer is now ready to read
            int id = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            return new Texture(id, name, decoder.getWidth(), decoder.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] readTxtResource(String resource) {
        List<String> lineList;
        try {
            File file = getFileFromResource(resource);
            lineList = Files.readAllLines(file.toPath());
            return lineList.toArray(new String[0]);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(vorbis, error, null);
        if (decoder == 0) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }
        stb_vorbis_get_info(decoder, info);

        int channels = info.channels();
        ShortBuffer pcm = BufferUtils.createShortBuffer(stb_vorbis_stream_length_in_samples(decoder) * channels);

        stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
        stb_vorbis_close(decoder);

        return pcm;
    }

    // Code from https://stackoverflow.com/questions/42322382/glfw-window-icon
    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if ( Files.isReadable(path) ) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while ( fc.read(buffer) != -1 ) ;
            }
        } else {
            try (
                    InputStream source = getFileFromResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while ( true ) {
                    int bytes = rbc.read(buffer);
                    if ( bytes == -1 )
                        break;
                    if ( buffer.remaining() == 0 )
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}
