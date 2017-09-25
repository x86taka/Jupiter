package cn.nukkit.network.protocol;

public class ShowProfilePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SHOW_PROFILE_PACKET;

    public String unknownStr1;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.unknownStr1 = this.getString();
    }

    @Override
    public void encode() {
        this.putString(this.unknownStr1);
    }
}
