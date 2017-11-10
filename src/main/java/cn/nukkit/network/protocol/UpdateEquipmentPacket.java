package cn.nukkit.network.protocol;

public class UpdateEquipmentPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.UPDATE_EQUIPMENT_PACKET;

    public int windowId;
    public int windowType;
    public int unknown; //TODO: find out what this is (vanilla always sends 0)
    public long entityRuntimeId;
    public byte[] namedtag;


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
        this.putByte((byte) this.windowId);
        this.putByte((byte) this.windowType);
        this.putEntityUniqueId(this.entityRuntimeId);
        this.put(this.namedtag);
    }
}
