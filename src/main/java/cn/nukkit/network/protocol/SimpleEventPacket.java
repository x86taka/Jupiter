package cn.nukkit.network.protocol;

public class SimpleEventPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SIMPLE_EVENT_PACKET;

    public short unknown;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.unknown = (short) this.getShort();
    }

    @Override
    public void encode() {
        this.reset();
        this.putShort(this.unknown);
    }
}
