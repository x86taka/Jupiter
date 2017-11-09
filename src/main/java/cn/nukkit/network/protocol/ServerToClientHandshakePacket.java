package cn.nukkit.network.protocol;

public class ServerToClientHandshakePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SERVER_TO_CLIENT_HANDSHAKE_PACKET;

    public String publicKey;
    public String serverToken;
    public String privateKey;

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
        this.putString(this.publicKey);
        this.putString(this.serverToken);
    }
}
