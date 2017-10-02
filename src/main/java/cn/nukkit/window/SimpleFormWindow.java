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
    private String data;
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

    //未調査
    @Override
    public Map<Integer, Object> getResponses() {
        return null;
    }

    @Override
    public void setResponse(String data) {
        this.data = data;
        this.datas = this.toObject(data);
    }

}
