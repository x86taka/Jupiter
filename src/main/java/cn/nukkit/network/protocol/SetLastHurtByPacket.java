package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class SetLastHurtByPacket extends DataPacket {

    public int unknownVarInt;

    @Override
    public byte pid() {
        return ProtocolInfo.SET_LAST_HURT_BY_PACKET;
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
