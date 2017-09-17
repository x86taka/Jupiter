package cn.nukkit.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.network.protocol.LoginPacket;

public final class ClientChainData {

    public final static int DEVICE_OS_ANDROID = 1;
    public final static int DEVICE_OS_IOS = 2;
    public final static int DEVICE_OS_OSX = 3;
    public final static int DEVICE_OS_FIREOS = 4;
    public final static int DEVICE_OS_GEARVR = 5;
    public final static int DEVICE_OS_HOLOLENS = 6;
    public final static int DEVICE_OS_WINDOWS10 = 7;
    public final static int DEVICE_OS_WINDOWS32 = 8;
    public final static int DEVICE_OS_DEDICATED = 9;
    /* TODO 1.2 Xbox OneとNintendo Switch追加
    public final static int DEVICE_OS_XBOXONE = x;
    public final static int DEVICE_OS_NINTENDOSWITCH = x;
    */

    public final static int GUI_SCALE_MINIMUM = -2;
    public final static int GUI_SCALE_MEDIUM = -1;
    public final static int GUI_SCALE_MAXIMUM = 0;
    
    public final static int INPUT_MODE_KEYBOARD = 1;
    public final static int INPUT_MODE_TOUCH = 2;
    public final static int INPUT_MODE_CONTROLLER = 3;

    //TODO 1.2でなくなる可能性。
    public final static int UI_PROFILE_CLASSIC = 0;
    public final static int UI_PROFILE_POCKET = 1;

    private String username;
    private UUID clientUUID;
    private String xuid;
    private String identityPublicKey;

    private long clientId;
    private String serverAddress;
    private String deviceModel;
    private int deviceOS;
    private String gameVersion;
    private int guiScale;
    private String languageCode;
    private int currentInputMode;
    private int defaultInputMode;
    private String ADRole;
    private String tenantId;
    private int UIProfile;
    private Skin skin;
    private int protocol;
    private byte gameEdition;

    private BinaryStream bs = new BinaryStream();

    public String getUsername() {
        return this.username;
    }

    public UUID getClientUUID() {
        return this.clientUUID;
    }

    public String getIdentityPublicKey() {
        return this.identityPublicKey;
    }

    public long getClientId() {
        return this.clientId;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public int getDeviceOS() {
        return this.deviceOS;
    }

    //TODO: 1.2 Update
    public String getDeviceOSByName(){
        switch(this.deviceOS){
            case DEVICE_OS_ANDROID:
                return "Android";

            case DEVICE_OS_IOS:
                return "iOS";

            case DEVICE_OS_OSX:
                return "OSX";

            case DEVICE_OS_FIREOS:
                return "FireOS";

            case DEVICE_OS_GEARVR:
                return "Gear VR";

            case DEVICE_OS_HOLOLENS:
                return "HoloLens";

            case DEVICE_OS_WINDOWS10:
                return "Windows 10";

            case DEVICE_OS_WINDOWS32:
                return "Windows 32";

            case DEVICE_OS_DEDICATED:
                return "Dedicated";
            /* TODO 1.2 Xbox OneとNintendo Switch追加
            case DEVICE_OS_XBOXONE:
                return "Xbox One";
                
            case DEVICE_OS_NINTENDOSWITCH:
                return "Nintendo Switch";
             */

            default:
                return "unknown";
        }
    }

    public String getGameVersion() {
        return this.gameVersion;
    }

    public int getGuiScale() {
        return this.guiScale;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public String getXUID() {
        return this.xuid;
    }

    public int getCurrentInputMode() {
        return this.currentInputMode;
    }

    public int getDefaultInputMode() {
        return this.defaultInputMode;
    }

    public String getADRole() {
        return this.ADRole;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public int getUIProfile() {
        return this.UIProfile;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public byte getGameEdition() {
        return this.gameEdition;
    }

    @Deprecated
    public static ClientChainData of(byte[] buffer) {
        return new ClientChainData(buffer);
    }

    @Deprecated
    public static ClientChainData read(LoginPacket pk) {
        return of(pk.getBuffer());
    }

    @Deprecated
    private ClientChainData(byte[] buffer) {
        bs.setBuffer(buffer, 0);
        this.decodeChainData();
        this.decodeSkinData();
    }

    public ClientChainData(LoginPacket pk) {
        bs.setBuffer(pk.getBuffer(), 0);
        this.decodeChainData();
        this.decodeSkinData();
        this.protocol = pk.getProtocol();
        this.gameEdition = pk.gameEdition;
    }

    private void decodeChainData() {
        Map<String, List<String>> map = new Gson().fromJson(new String(bs.get(bs.getLInt()), StandardCharsets.UTF_8),
                        new TypeToken<Map<String, List<String>>>() {}.getType());
        if (map.isEmpty() || !map.containsKey("chain") || map.get("chain").isEmpty()) return;
        List<String> chains = map.get("chain");
        for (String c : chains) {
            JsonObject chainMap = decodeToken(c);
            if (chainMap == null) continue;
            if (chainMap.has("extraData")) {
                JsonObject extra = chainMap.get("extraData").getAsJsonObject();
                if (extra.has("displayName")) this.username = extra.get("displayName").getAsString();
                if (extra.has("identity")) this.clientUUID = UUID.fromString(extra.get("identity").getAsString());
                if (extra.has("XUID")) this.xuid = extra.get("XUID").getAsString();
            }
            if (chainMap.has("identityPublicKey"))
                this.identityPublicKey = chainMap.get("identityPublicKey").getAsString();
        }
    }

    private void decodeSkinData() {
        JsonObject skinToken = decodeToken(new String(bs.get(bs.getLInt())));
        if(skinToken == null) return;
        if (skinToken.has("ClientRandomId")) this.clientId = skinToken.get("ClientRandomId").getAsLong();
        if (skinToken.has("ServerAddress")) this.serverAddress = skinToken.get("ServerAddress").getAsString();
        if (skinToken.has("DeviceModel")) this.deviceModel = skinToken.get("DeviceModel").getAsString();
        if (skinToken.has("DeviceOS")) this.deviceOS = skinToken.get("DeviceOS").getAsInt();
        if (skinToken.has("GameVersion")) this.gameVersion = skinToken.get("GameVersion").getAsString();
        if (skinToken.has("GuiScale")) this.guiScale = skinToken.get("GuiScale").getAsInt();
        if (skinToken.has("LanguageCode")) this.languageCode = skinToken.get("LanguageCode").getAsString();
        if (skinToken.has("CurrentInputMode")) this.currentInputMode = skinToken.get("CurrentInputMode").getAsInt();
        if (skinToken.has("DefaultInputMode")) this.defaultInputMode = skinToken.get("DefaultInputMode").getAsInt();
        if (skinToken.has("ADRole")) this.ADRole = skinToken.get("ADRole").getAsString();
        if (skinToken.has("TenantId")) this.tenantId = skinToken.get("TenantId").getAsString();
        if (skinToken.has("UIProfile")) this.UIProfile = skinToken.get("UIProfile").getAsInt();
        String skinId = null;
        if (skinToken.has("SkinId")) skinId = skinToken.get("SkinId").getAsString();
        if (skinToken.has("SkinData")) this.skin = new Skin(skinToken.get("SkinData").getAsString(), skinId);
    }

    private JsonObject decodeToken(String token) {
        String[] base = token.split("\\.");
        if (base.length < 2) return null;
        String json = new String(Base64.getDecoder().decode(base[1]), StandardCharsets.UTF_8);
        return new Gson().fromJson(json, JsonObject.class);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ClientChainData && Objects.equals(bs, ((ClientChainData) obj).bs);
    }

    @Override
    public int hashCode() {
        return bs.hashCode();
    }
}
