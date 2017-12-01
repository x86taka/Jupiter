package cn.nukkit.window.element;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.nukkit.utils.MainLogger;

public class Button {

    private final String type = "button";
    private String text;
    private Map<String, Object> image = new HashMap<>();

    public Button(String text) {
        this.text = text;
        image.put("data", "");
        image.put("type", "");
    }

    public Button(String text, String imageType, String imageData) {
        this.text = text;

        if (imageType.equals("path")) {
            if(!image.containsKey("type")) this.image.put("type", "path");
            if(!image.containsKey("data")) this.image.put("data", this.getImageByteArray(new File(imageData)));

        } else if(imageType.equals("url")){
            if(!image.containsKey("type")) this.image.put("type", "url");
            if(!image.containsKey("data")) this.image.put("data", imageData);

        } else {
            try {
                throw new Exception("許可されていないタイプの画像です！pathもしくはurlのみが許可されています！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
            this.image.put("type", "path");
            this.image.put("data", this.getImageByteArray(new File(imageData)));

        } else if(imageType.equals("url")){
            this.image.put("type", "url");
            this.image.put("data", imageData);

        }
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

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, file.getName().substring(file.getName().lastIndexOf(".") + 1), baos);

            return baos.toByteArray();
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
