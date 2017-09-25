package cn.nukkit.network.protocol;

/**
 * Created on 15-10-22.
 */
public class SetEntityLinkPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SET_ENTITY_LINK_PACKET;

    public static final byte TYPE_REMOVE = 0;
    public static final byte TYPE_RIDE = 1;
    public static final byte TYPE_PASSENGER = 2;

    public long rider;
    public long riding;
    public int type;

    @Override
    public void decode() {
    	this.rider = this.getVarLong();
        this.riding = this.getVarLong();
        this.type = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarLong(this.rider);
        this.putVarLong(this.riding);
        this.putVarInt(this.type);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
