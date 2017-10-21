
package cn.nukkit.window;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.nukkit.utils.MainLogger;
import cn.nukkit.window.element.Element;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;


public class ServerSettingsWindow extends CustomFormWindow {

    private String imageType;
    private String imagePath;
    private byte[] imageData;

    public ServerSettingsWindow(int id, String title, Element[] elements) {
        super(id, title, elements);
    }

    public ServerSettingsWindow(int id, String title, Element[] elements, String imageType, String imagePath) {
        super(id, title, elements);
        this.imageType = imageType;
        this.imagePath = imagePath;
    }

    public void setImage(String type, String data) throws IOException {
        if(!type.equals("path") || !(type.equals("url"))){
            try {
                throw new Exception("許可されていないタイプの画像です！pathもしくはurlのみが許可されています！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (type.equals("path")) {
            this.imageData = this.getImageByteArray(new File(data));
        }
        this.imageType = type;
        this.imagePath = data;
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

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_CUSTOM_FORM);
        data.put("title", title);

        if (imageType.equals("url")) {
            data.put("icon", new LinkedHashMap<String, String>() {
                {
                    put("type", "url");
                    put("data", imagePath);
                }
            });
        } else if (imageType.equals("path")) {
            data.put("icon", new LinkedHashMap<String, Object>() {
                {
                    put("type", "path");
                    put("data", imageData);
                }
            });
        }

        ObjectList<Element> elements = new ObjectArrayList<Element>();
        for (Element e : this.elements) {
            elements.add(e);
        }
        data.put("content", elements);

        return gson.toJson(data);
    }
}
