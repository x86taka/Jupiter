
package cn.nukkit.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.window.element.Element;


public class ServerSettingsWindow extends CustomFormWindow {

    private String imageType;
    private String imagePath;

    public ServerSettingsWindow(int id, String title, Element[] elements) {
        super(id, title, elements);
    }

    public ServerSettingsWindow(int id, String title, Element[] elements, String imageType, String imagePath) {
        super(id, title, elements);
        this.imageType = imageType;
        this.imagePath = imagePath;
    }

    public void setImage(String type, String path) {
        if(!type.equals("path") || !(type.equals("url"))){
            try {
                throw new Exception("許可されていないタイプの画像です！pathもしくはurlのみが許可されています！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.imageType = type;
        this.imagePath = path;
    }

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_CUSTOM_FORM);
        data.put("title", title);

        List<Element> datas = new ArrayList<Element>();

        for (Element e : elements) {
           datas.add(e);
        }
        data.put("content", datas);

        //TODO images

        return gson.toJson(data);
    }
}
