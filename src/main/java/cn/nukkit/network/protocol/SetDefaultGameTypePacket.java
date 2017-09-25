package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class SetDefaultGameTypePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.SET_DEFAULT_GAME_TYPE_PACKET;

    public int gamemode;

    @Override
    public void decode() {
        this.gamemode = (int) this.getUnsignedVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(this.gamemode);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
