package cn.nukkit.network.protocol;


/**
 * Created by on 15-10-13.
 */
public class LoginPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.LOGIN_PACKET;

    public String username;
    public int protocol;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.protocol = this.getInt();
        this.setBuffer(this.getByteArray(), 0);
    }

    @Override
    public void encode() {

    }

    public int getProtocol() {
        return protocol;
    }

}
