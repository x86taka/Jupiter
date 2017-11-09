package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class SetLastHurtByPacket extends DataPacket {

    public final static byte NETWORK_ID = ProtocolInfo.SET_LAST_HURT_BY_PACKET;

    public int unknownVarInt;

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
        this.putVarInt(unknownVarInt);
    }
}
