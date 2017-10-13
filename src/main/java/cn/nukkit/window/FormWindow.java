package cn.nukkit.window;

import java.util.Map;

import com.google.gson.Gson;

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

public abstract class FormWindow {

    public final Gson gson = new Gson();

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

    public abstract Object getResponse();

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
