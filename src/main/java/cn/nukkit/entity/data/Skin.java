package cn.nukkit.entity.data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class Skin {

    public static final int SINGLE_SKIN_SIZE = 64 * 32 * 4;
    public static final int DOUBLE_SKIN_SIZE = 64 * 64 * 4;

    public static final String MODEL_STEVE = "Standard_Steve";
    public static final String MODEL_ALEX = "Standard_Alex";

    private String skinId;
    private byte[] skinData;
    private byte[] capeData;
    private String geometryName;
    private String geometryData;

    public Skin(String skinId, byte[] skinData, byte[] capeData, String geometryName, String geometryData) {
        this.skinId = skinId;
        this.skinData = skinData;
        this.capeData = capeData;
        this.geometryName = geometryName;
        this.geometryData = geometryData;
    }

    public Skin(byte[] data) {
        this(data, MODEL_STEVE);
    }

    public Skin(InputStream inputStream) {
        this(inputStream, MODEL_STEVE);
    }

    public Skin(ImageInputStream inputStream) {
        this(inputStream, MODEL_STEVE);
    }

    public Skin(File file) {
        this(file, MODEL_STEVE);
    }

    public Skin(URL url) {
        this(url, MODEL_STEVE);
    }

    public Skin(BufferedImage image) {
        this(image, MODEL_STEVE);
    }

    public Skin(byte[] data, String model) {
        this.setSkinData(data);
        this.setSkinId(model);
    }

    public Skin(InputStream inputStream, String model) {
        BufferedImage image;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.parseBufferedImage(image);
        this.setSkinId(model);
    }

    public Skin(ImageInputStream inputStream, String model) {
        BufferedImage image;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.parseBufferedImage(image);
        this.setSkinId(model);
    }

    public Skin(File file, String model) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.parseBufferedImage(image);
        this.setSkinId(model);
    }

    public Skin(URL url, String model) {
        BufferedImage image;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.parseBufferedImage(image);
        this.setSkinId(model);
    }

    public Skin(BufferedImage image, String model) {
        this.parseBufferedImage(image);
        this.setSkinId(model);
    }

    public Skin(String base64) {
        this(Base64.getDecoder().decode(base64));
    }

    public Skin(String base64, String model) {
        this(Base64.getDecoder().decode(base64), model);
    }

    public void parseBufferedImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), true);
                outputStream.write(color.getRed());
                outputStream.write(color.getGreen());
                outputStream.write(color.getBlue());
                outputStream.write(color.getAlpha());
            }
        }
        image.flush();
        this.setSkinData(outputStream.toByteArray());
    }

    public String getSkinId() {
        return this.skinId;
    }

    public void setSkinId(String skinId) {
        if (skinId == null || skinId.trim().isEmpty()) {
            skinId = MODEL_STEVE;
        }
        this.skinId = skinId;
    }

    public byte[] getSkinData() {
        return this.skinData;
    }

    public void setSkinData(byte[] skinData) {
        if (skinData.length != SINGLE_SKIN_SIZE && skinData.length != DOUBLE_SKIN_SIZE) {
            throw new IllegalArgumentException("Invalid skin");
        }
        this.skinData = skinData;
    }

    public byte[] getCapeData() {
        return this.capeData;
    }

    public void setCapeData(byte[] capeData) {
        this.capeData = capeData;
    }

    public String getGeometryName() {
        return this.geometryName;
    }

    public void setGeometryName(String geometryName) {
        this.geometryName = geometryName;
    }

    public String getGeometryData() {
        return this.geometryData;
    }

    public void setGeometryData(String geometryData) {
        this.geometryData = geometryData;
    }

    public boolean isValid() {
        return this.skinData.length == SINGLE_SKIN_SIZE || this.skinData.length == DOUBLE_SKIN_SIZE;
    }

}