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
    public void decode() {
    	this.type = (byte) this.getByte();
    	int count = (int) this.getUnsignedVarInt();
    	for(int i=0;i<count;i++){
    		entries[i] = new Entry(this.getUUID());
    		if(this.type == TYPE_ADD){
    			entries[i].entityId = this.getVarLong();
    			entries[i].name = this.getString();
    			entries[i].skin = this.getSkin();
    			entries[i].capeData = this.getString();
    			entries[i].geometryModel = this.getString();
    			entries[i].geometryData = this.getString();
    			entries[i].xboxUserId = this.getString();
    		}
    	}
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
                this.putSkin(entry.skin);
                this.putString(entry.capeData);
                this.putString(entry.geometryModel);
                this.putString(entry.geometryData);
                this.putString(entry.xboxUserId);
            } else {
                this.putUUID(entry.uuid);
            }
        }

    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public static class Entry {

        public final UUID uuid;
        public long entityId = 0;
        public String name = "";
        public Skin skin;
        
        public String capeData = "";
        public String xboxUserId = "";
        public String geometryModel = "";
        public String geometryData = "";

        public Entry(UUID uuid) {
            this.uuid = uuid;
        }

        public Entry(UUID uuid, long entityId, String name, Skin skin) {
            this.uuid = uuid;
            this.entityId = entityId;
            this.name = name;
            this.skin = skin;
        }
    }
}
