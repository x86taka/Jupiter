package cn.nukkit.network.protocol;

public class SubClientLoginPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SUB_CLIENT_LOGIN_PACKET;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        //TODO
    }
}
