package cn.nukkit.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.window.element.Button;

/**
 *
 * @author Itsu
 *
 * @param id ウィンドウid
 * @param title タイトル
 * @param content 内容となる文章
 * @param buttons 設置するボタンの配列
 *
 * <h3>シンプルフォームウィンドウ - Jupiter ModalForm API</h3>
 * <p>このクラスはシンプルなボタンリストのウィンドウを提供します。</p>
 * <p>ウィンドウの作成には他のウィンドウと被らないid、タイトル、内容として表示される文章、設置したい
 * ボタンの配列をコンストラクタに送ります。実際の表示では上からボタンが並びます。</p>
 *
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 *
 * @see SimpleFormWindow#getResponses()
 */

public class SimpleFormWindow extends FormWindow {

    private int id;
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

    /**
     *
     * @author itsu
     * @return id(int)
     *
     * <h3>getId() - Jupiter ModalForm API</h3>
     * <p>このメソッドではウィンドウidを取得します。</p>
     *
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     *
     */

    @Override
    public int getId() {
        return this.id;
    }

    /**
     *
     * @author itsu
     * @return jsonデータ(String)
     *
     * <h3>toJson() - Jupiter ModalForm API</h3>
     * <p>ウィンドウをJSONデータ化します。</p>
     *
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     *
     */

    @Override
    public String toJson() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("type", WindowType.TYPE_SIMPLE_FORM);
        data.put("title", title);
        data.put("content", content);

        List<Button> buttonDatas = new ArrayList<Button>();

        for(Button b : this.buttons){
            buttonDatas.add(b);
        }
        data.put("buttons", buttonDatas);

        return gson.toJson(data);
    }

    @Override
    public Map<Integer, Object> getResponses() {
        Map<Integer, Object> out = new LinkedHashMap<Integer, Object>();
        for (int i = 0; i < buttons.length; ++i) {
            out.put(i, i == data);
        }
        return out;
    }

    @Override
    public Integer getResponse() {
        return this.data;
    }

    @Override
    public void setResponse(String data) {
        this.data = Integer.parseInt(data.trim());
    }

}
