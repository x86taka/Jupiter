
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
    private Object imageData;

    public ServerSettingsWindow(int id, String title, Element[] elements) {
        super(id, title, elements);
    }

    public ServerSettingsWindow(int id, String title, Element[] elements, String imageType, String imageData) {
        super(id, title, elements);
        if (imageType.equals("path")) {
            this.imageData = this.getImageByteArray(new File(imageData));
        } else {
            this.imageData = imageData;
        }

        this.imageType = imageType;
    }

    public void setImage(String imageType, String imageData) throws IOException {
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

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_CUSTOM_FORM);
        data.put("title", title);

        if (imageType != null && imageData != null) {
            data.put("icon", new LinkedHashMap<String, Object>() {
                {
                    put("type", imageType);
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
