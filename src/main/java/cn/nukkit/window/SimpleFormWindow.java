package cn.nukkit.window;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.window.element.Button;

public class SimpleFormWindow extends WindowBase{

    private int id;
    private String title;
    private String content;
    private Button[] buttons;
    private int data;
    private List<Object> datas;


    public SimpleFormWindow(int id, String title, String content, Button[] buttons){
        this.id = id;
        this.title = title;
        this.content = content;
        this.buttons = buttons;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_SIMPLE_FORM);
        data.put("title", this.title);
        data.put("content", this.content);
        data.put("buttons", this.buttons);
        return this.toJson(title, WindowType.TYPE_SIMPLE_FORM, data);
    }

    @Override
    public Map<Integer, Object> getResponses() {
        Map<Integer, Object> out = new LinkedHashMap<Integer, Object>();
        for (int i = 0; i <= buttons.length; ++i) {
            out.put(i, i == data);
        }
        return out;
    }

    @Override
    public void setResponse(String data) {
        this.data = Integer.valueOf(data);
    }

}
