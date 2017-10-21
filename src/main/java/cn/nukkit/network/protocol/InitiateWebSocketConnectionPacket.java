package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class InitiateWebSocketConnectionPacket extends DataPacket {

    public String unknownString;

    @Override
    public byte pid() {
        return ProtocolInfo.INITIATE_WEB_SOCKET_CONNECTION_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putString(unknownString);
    }
}
