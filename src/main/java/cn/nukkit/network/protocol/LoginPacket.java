package cn.nukkit.network.protocol;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.entity.data.Skin;


/**
 * Created by on 15-10-13.
 */
public class LoginPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.LOGIN_PACKET;

    public String username;
    public int protocol;
    public byte gameEdition;
    public UUID clientUUID;
    public long clientId;
    public String deviceModel;
    
    public String gameVersion;
    public String language;
    public int guiScale;

    public Skin skin;
    
    //デバッグ用
	private String data = "";

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.protocol = this.getInt();
        this.gameEdition = (byte) this.getByte();
        this.setBuffer(this.getByteArray(), 0);
        decodeChainData();
        decodeSkinData();
        /* デバッグ用
         * Json出力
        try {
			Utils.writeFile("./logindata/data.json", data);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		*/
    }

    @Override
    public void encode() {

    }

    public int getProtocol() {
        return protocol;
    }

    private void decodeChainData() {
        Map<String, List<String>> map = new Gson().fromJson(new String(this.get(getLInt()), StandardCharsets.UTF_8),
                new TypeToken<Map<String, List<String>>>() {
                }.getType());
        if (map.isEmpty() || !map.containsKey("chain") || map.get("chain").isEmpty()) return;
        List<String> chains = map.get("chain");
        for (String c : chains) {
            JsonObject chainMap = decodeToken(c);
            data += new Gson().toJson(chainMap);
            if (chainMap == null) continue;
            if (chainMap.has("extraData")) {
                JsonObject extra = chainMap.get("extraData").getAsJsonObject();
                data += new Gson().toJson(extra);
                if (extra.has("displayName")) this.username = extra.get("displayName").getAsString();
                if (extra.has("deviceModel")) this.deviceModel = extra.get("deviceModel").getAsString();
                if (extra.has("identity")) this.clientUUID = UUID.fromString(extra.get("identity").getAsString());
                
                if (extra.has("GameVersion")) this.gameVersion = extra.get("GameVersion").getAsString();
                //日本語=ja_JP
                if (extra.has("LanguageCode")) this.language = extra.get("LanguageCode").getAsString();
                if (extra.has("GuiScale")) this.guiScale = extra.get("GuiScale").getAsInt();
            }
        }
    }

    private void decodeSkinData() {
        JsonObject skinToken = decodeToken(new String(this.get(this.getLInt())));
        data += new Gson().toJson(skinToken);
        String skinId = null;
        if (skinToken.has("ClientRandomId")) this.clientId = skinToken.get("ClientRandomId").getAsLong();
        if (skinToken.has("SkinId")) skinId = skinToken.get("SkinId").getAsString();
        if (skinToken.has("deviceModel")) this.deviceModel = skinToken.get("deviceModel").getAsString();
        if (skinToken.has("SkinData")) this.skin = new Skin(skinToken.get("SkinData").getAsString(), skinId);
    }

    private JsonObject decodeToken(String token) {
        String[] base = token.split("\\.");
        if (base.length < 2) return null;
        return new Gson().fromJson(new String(Base64.getDecoder().decode(base[1]), StandardCharsets.UTF_8), JsonObject.class);
    }

    @Override
    public Skin getSkin() {
        return this.skin;
    }
}
