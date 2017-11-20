package cn.nukkit.network.protocol;

import java.util.UUID;

import cn.nukkit.entity.data.Skin;

public class PlayerSkinPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_SKIN_PACKET;

    public UUID uuid;
    public Skin skin;
    public String oldSkinName = "";
    public String newSkinName = "";

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.uuid = this.getUUID();
        String skinId = this.getString();
        this.newSkinName = this.getString();
        this.oldSkinName = this.getString();
        byte[] skinData = this.getByteArray();
        byte[] capeData = this.getByteArray();
        String geometryModel = this.getString();
        String geometryData = this.getString();

        this.skin = new Skin(skinId, skinData, capeData, geometryModel, geometryData);
    }

    @Override
    public void encode() {
        this.reset();
        this.putUUID(this.uuid);
        this.putString(this.skin.getSkinId());
        this.putString(this.newSkinName);
        this.putString(this.oldSkinName);
        this.putByteArray(this.skin.getSkinData());
        this.putByteArray(this.skin.getCapeData());
        this.putString(this.skin.getGeometryName());
        this.putString(this.skin.getGeometryData());
    }

}
