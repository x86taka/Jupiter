package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class InitiateWebSocketConnectionPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.INITIATE_WEB_SOCKET_CONNECTION_PACKET;

    public String unknownString;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }
}
