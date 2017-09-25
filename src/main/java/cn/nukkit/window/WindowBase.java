package cn.nukkit.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import cn.nukkit.window.element.Button;
import cn.nukkit.window.element.Element;

public abstract class WindowBase {

    public WindowBase(){

    }

    public String toJson(String title, String type, Map<String, Object> content){
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.clear();

        switch(type){
            case WindowType.TYPE_MODAL:
                data.put("type", WindowType.TYPE_MODAL);
                data.put("title", title);
                data.put("content", content.get("content"));
                data.put("button1", content.get("button1"));
                data.put("button2", content.get("button2"));

                return new Gson().toJson(data);

            case WindowType.TYPE_CUSTOM_FORM:
                if(!(content.get("content") instanceof Element[])){
                    throw new WrongElementException(title);
                }
                 data.put("type", WindowType.TYPE_CUSTOM_FORM);
                 data.put("title", title);

                 Element[] elements = (Element[]) content.get("content");
                 List<Element> datas = new ArrayList<Element>();

                 for(Element e : elements){
                    datas.add(e);
                 }
                 data.put("content", datas);

                 data.put("button1", "");
                 data.put("button2", "");

                 return new Gson().toJson(data);
                 
            case WindowType.TYPE_SIMPLE_FORM:
            	data.put("type", WindowType.TYPE_SIMPLE_FORM);
            	data.put("title", title);
            	data.put("content", content.get("content"));
            	
            	Button[] buttons = (Button[]) content.get("buttons");
                List<Button> buttonDatas = new ArrayList<Button>();

                for(Button b : buttons){
                	buttonDatas.add(b);
                }
                data.put("buttons", buttonDatas);
                
                return new Gson().toJson(data);

            default:
                return null;
        }
    }

    public abstract int getId();

    public abstract String toJson();

}
