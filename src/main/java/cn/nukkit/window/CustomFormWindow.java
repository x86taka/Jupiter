package cn.nukkit.window;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.window.element.Element;

public class CustomFormWindow extends WindowBase{

    private int id;
    private String title;
    private Element[] elements;

    private String data;
    private Object[] datas;

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

    @Override
    public Map<String, Object> getResponses(){
         Map<String, Object> map = new LinkedHashMap<String, Object>();
         int i = 0;

        for(Object o : datas){
            switch(this.elements[i].getName()){
                case "Button":
                    map.put(i + ":Button", o);
                    break;

                case "Dropdown":
                    map.put(i + ":Dropdown", o);
                    break;

                case "Input":
                    map.put(i + ":Input", (String) o);
                    break;

                case "Label":
                    map.put(i + ":Label", null);
                    break;

                case "Slider":
                    map.put(i + ":Slider", (float) o);
                    break;

                case "StepSlider":
                    map.put(i + ":StepSlider", (float) o);
                    break;

                case "Toggle":
                    map.put(i + ":Input", (boolean) o);
                    break;
            }
        }

        return map;
    }

    @Override
    public void setResponse(String data) {
        this.data = data;
        this.datas = this.toObject(data);
    }

}
