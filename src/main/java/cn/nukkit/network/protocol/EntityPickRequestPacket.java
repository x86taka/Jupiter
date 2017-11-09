package cn.nukkit.network.protocol;

public class EntityPickRequestPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.ENTITY_PICK_REQUEST_PACKET;

    public long entityType;

    public int slot;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.entityType = this.getLong();
        this.slot = this.getByte();
    }

    @Override
    public void encode() {

    }
}
