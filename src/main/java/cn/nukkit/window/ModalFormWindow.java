package cn.nukkit.window;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModalFormWindow extends WindowBase{

    private int id;
    private String title = "ModalFormWindow Title";
    private String content = "ModalFormWindow text";
    private String upButtonText = "はい";
    private String downButtonText = "いいえ";
    
	private String data;
	private Object[] datas;

    public ModalFormWindow(int id, String title, String content, String upButtonText, String downButtonText){
        this.id = id;
        this.title = title;
        this.content = content;
        this.upButtonText = upButtonText;
        this.downButtonText = downButtonText;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("content", content);
        data.put("button1", upButtonText);
        data.put("button2", downButtonText);
        return this.toJson(title, WindowType.TYPE_MODAL, data);
    }
    
	@Override
	public void setResponse(String data) {
		this.data = data;
		this.datas = this.toObject(data);
	}

	//未調査
	@Override
	public Map<String, Object> getResponses() {
		return null;
	}

}
