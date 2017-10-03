package cn.nukkit.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.window.element.Button;
import cn.nukkit.window.element.Element;

/**
 * @author itsu
 * 
 * <h3>WindowBase - Jupiter ModalForm API</h3>
 * <p>このクラスはCustomFormWindow、ModalFormWindow、SimpleFormWindowのもとになっているクラス
 * です。そのため、型比較(instanceof)で比較した後では安全にそれらのウィンドウにキャストするこ
 * とができます。toJson()などのウィンドウをjsonに変換する処理などを提供していますが、プラグイ
 * ン側からこのようなメソッドを実行するのは推奨していません。</p>
 * 
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 * 
 * @see CustomFormWindow
 * @see ModalFormWindow
 * @see SimpleFormWindow
 * 
 */

public abstract class WindowBase {

    public WindowBase(){

    }
    
    /**
     * 
     * @author itsu
     * @return jsonデータ(String)
     * 
     * @param title タイトル
     * @param type ウィンドウタイプ(WindowTypeのいずれか)
     * @param content json変換するマップ
     * 
     * <h3>toJson() - Jupiter ModalForm API</h3>
     * <p>ウィンドウをJSONデータ化します。変換にはGoogleのGsonライブラリを使用しています。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     * @see CustomFormWindow#toJson()
     * @see ModalFormWindow#toJson()
     * @see SimpleFormWindow#toJson()
     * 
     * @see WindowType#TYPE_MODAL
     * @see WindowType#TYPE_CUSTOM_FORM
     * @see WindowType#TYPE_SIMPLE_FORM
     * 
     */

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

    /**
     * @return Map<Integer, Object> レスポンス
     * @author itsu
     * 
     * <h3>[abstract] getId() - Jupiter ModalForm API</h3>
     * <p>このメソッドではウィンドウidを取得します。各ウィンドウで定義されています。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     * @see CustomFormWindow#getId()
     * @see ModalFormWindow#getId()
     * @see SimpleFormWindow#getId()
     * 
     */
    
    public abstract int getId();

    /**
     * 
     * @author itsu
     * @return jsonデータ(String)
     * 
     * <h3>[abstract] toJson() - Jupiter ModalForm API</h3>
     * <p>ウィンドウをJSONデータ化します。各ウィンドウで定義されています。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     * @see CustomFormWindow#toJson()
     * @see ModalFormWindow#toJson()
     * @see SimpleFormWindow#toJson()
     * 
     */
    
    public abstract String toJson();
    
    /**
     * @return Map<Integer, Object> レスポンス
     * @author itsu
     * 
     * <h3>[abstract] getResponses() - Jupiter ModalForm API</h3>
     * <p>このメソッドではレスポンスを取得します。各ウィンドウで定義されています。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     * @see CustomFormWindow#getResponses()
     * @see ModalFormWindow#getResponses()
     * @see SimpleFormWindow#getResponses()
     * 
     */
    
    public abstract Map<Integer, Object> getResponses();

    /**
     * 
     * @author itsu
     * 
     * <h3>[abstract] setResponse() - Jupiter ModalForm API</h3>
     * <p>レスポンスとして返ってきたjsonデータをウィンドウに格納します。プラグイン側からの使用は非推奨です。</p>
     * 
     * <p>Jupiter Project by JupiterDevelopmentTeam</p>
     * 
     */
    
    public abstract void setResponse(String data);

}
