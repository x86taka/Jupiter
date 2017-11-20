package cn.nukkit.network.protocol;

import java.util.UUID;

import cn.nukkit.entity.data.Skin;

/**
 * @author Nukkit Project Team
 */
public class PlayerListPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_LIST_PACKET;

    public static final byte TYPE_ADD = 0;
    public static final byte TYPE_REMOVE = 1;

    public byte type;
    public Entry[] entries = new Entry[0];

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putUnsignedVarInt(this.entries.length);
        for (Entry entry : this.entries) {
            if (type == TYPE_ADD) {
                this.putUUID(entry.uuid);
                this.putVarLong(entry.entityId);
                this.putString(entry.name);
                this.putString(entry.skin.getSkinId());
                this.putByteArray(entry.skin.getSkinData());
                this.putByteArray(entry.skin.getCapeData());
                this.putString(entry.skin.getGeometryName());
                this.putString(entry.skin.getGeometryData());
                this.putString(entry.xboxUserId);
            } else {
                this.putUUID(entry.uuid);
            }
        }

    }

    public static class Entry {

        public final UUID uuid;
        public long entityId = 0;
        public String name = "";
        public Skin skin;
        public String xboxUserId = ""; //TODO

        public Entry(UUID uuid) {
            this.uuid = uuid;
        }

        public Entry(UUID uuid, long entityId, String name, Skin skin) {
            this(uuid, entityId, name, skin, "");
        }

        public Entry(UUID uuid, long entityId, String name, Skin skin, String xboxUserId) {
            this.uuid = uuid;
            this.entityId = entityId;
            this.name = name;
            this.skin = skin;
            this.xboxUserId = xboxUserId == null ? "" : xboxUserId;
        }
    }

}
