
package cn.nukkit.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.window.element.Dropdown;
import cn.nukkit.window.element.Element;
import cn.nukkit.window.element.StepSlider;



/**
 *
 * @author Itsu
 *
 * @param id ウィンドウid
 * @param title タイトル
 * @param elements 設置するエレメント（部品）
 *
 * <h3>カスタムフォームウィンドウ - Jupiter ModalForm API</h3>
 * <p>このクラスは独自でデザインできるウィンドウを提供します。</p>
 * <p>ウィンドウの作成にはid、タイトル、エレメント配列を渡す必要があります。idは他のウィンドウと被らないid、タイトルは
 * お好みのもの、エレメント配列には設置したいエレメントを追加した配列をそれぞれ渡す必要があります。なお、ウィンドウに
 * 配置される順番は配列の0番目から順に上から配置されます。エレメントとはcn.nukkit.window.elementパッケージ内にある
 * 各種クラスのことを指します。</p>
 * <p>レスポンスを取得するにはgetResponses()を使用します。戻り値はMap<Integer, Object>で、Integerはインデックス値を
 * 表しています。このインデックス値の順番はインスタンスの引数として渡したElement[]に配置されたエレメントの順番と等しく、
 * その値として返ってきたものがObjectに入っています。</p>
 *
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 *
 * @see CustomFormWindow#getResponses()
 *
 */

public class CustomFormWindow extends FormWindow {

    private int id;
    protected Element[] elements;

    private String data;
    private List<Object> datas = new ArrayList<Object>();

    public CustomFormWindow(int id, String title, Element[] elements){
        this.id = id;
        this.title = title;
        this.elements = elements;
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
        data.put("type", WindowType.TYPE_CUSTOM_FORM);
        data.put("title", title);

        List<Element> elements = new ArrayList<Element>();

        for (Element e : this.elements) {
            elements.add(e);
        }
        data.put("content", elements);

        return gson.toJson(data);
    }

    /**
     * @return Map<Integer, Object> レスポンス
     * @author itsu
     *
     * <h3>getResponses() - Jupiter ModalForm API</h3>
     * <p>このメソッドではレスポンスを取得します。戻り値はMap<Integer, Object>で、Integerはインデックス値を
     * 表しています。このインデックス値の順番はインスタンスの引数として渡したElement[]に配置されたエレメントの順番と等しく、
     * その値として返ってきたものがObjectに入っています。</p>
     * <br>
     * <p>各エレメントの戻り値の型</p>
     * <li>Dropdown <b>String</b></li>
     * <li>Input <b>String</b></li>
     * <li>Label <b>null</b></li>
     * <li>Slider <b>double</b></li>
     * <li>StepSlider <b>String</b></li>
     * <li>Toggle <b>boolean</b></li>
     *
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     *
     */
    @Override
    public Map<Integer, Object> getResponses(){
         Map<Integer, Object> map = new LinkedHashMap<Integer, Object>();
         int i = 0;

        for(Object o : datas){
            switch(this.elements[i].getName()){
                case "Dropdown":
                    map.put(i, ((Dropdown) elements[i]).getOptions().get((int) (double) o));
                    break;

                case "Input":
                    map.put(i, (String) o);
                    break;

                case "Label":
                    map.put(i, null);
                    break;

                case "Slider":
                    map.put(i, (double) o);
                    break;

                case "StepSlider":
                    map.put(i,  ((StepSlider) elements[i]).getSteps().get((int) (double) o));
                    break;

                case "Toggle":
                    map.put(i, (boolean) o);
                    break;
            }
            ++i;
        }

        return map;
    }

    @Override
    public String getResponse() {
        return data;
    }

    @Override
    public void setResponse(String data) {
        this.data = data;
        this.datas.clear();
        if(this.toObject(data) != null){
            this.datas.addAll(this.toObject(data));
        }
    }

    /**
     * 
     * @author itsu
     * 
     * <h3>toObject() - Jupiter ModalForm API</h3>
     * <p>レスポンスとして返ってきたjsonデータをJavaのMap<String, Object>に変換します。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     */
    public List<Object> toObject(String data){
        return new Gson().fromJson(data, new TypeToken<List<Object>>(){}.getType());
    }
}
