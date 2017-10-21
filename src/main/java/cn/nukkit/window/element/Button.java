package cn.nukkit.window.element;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.nukkit.utils.MainLogger;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;

public class Button {

    private final String type = "button";
    private String text;
    private String imageType;
    private Object imageData;

    public Button(String text) {
        this.text = text;
    }

    public Button(String text, String imageType, String imageData) {
        this.text = text;
        if (imageType.equals("path")) {
            this.imageData = this.getImageByteArray(new File(imageData));
        } else {
            this.imageData = imageData;
        }

        this.imageType = imageType;
    }

    public void setImage(String imageType, String imageData){
        if(!(imageType.equals("path")) || !(imageType.equals("url"))) {
            try {
                throw new Exception("許可されていないタイプの画像です！pathもしくはurlのみが許可されています！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (imageType.equals("path")) {
            this.imageData = this.getImageByteArray(new File(imageData));
        } else {
            this.imageData = imageData;
        }

        this.imageType = imageType;
    }

    public byte[] getImageByteArray(File file) {
        try {
            BufferedImage image = ImageIO.read(file);

            if (image.getHeight() != 128 || image.getWidth() != 128) {
                image = new BufferedImage(128, 128, image.getType());
                Graphics2D g = image.createGraphics();
                g.drawImage(image, 0, 0, 128, 128, null);
                g.dispose();
            }

            FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
            ImageIO.write(image, file.getName().substring(file.getName().lastIndexOf(".") + 1), baos);

            return baos.array;
        } catch (IOException e) {
            MainLogger.getLogger().logException(e);
        }

        return null;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public String getName() {
        return "Button";
    }

}
