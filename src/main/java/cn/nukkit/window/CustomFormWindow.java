package cn.nukkit.window;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.window.element.Element;

public class CustomFormWindow extends WindowBase{

    private int id;
    private String title;
    private Element[] elements;

    public CustomFormWindow(int id, String title, Element[] elements){
        this.id = id;
        this.title = title;
        this.elements = elements;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("content", elements);
        return this.toJson(title, WindowType.TYPE_CUSTOM_FORM, data);
    }

}
