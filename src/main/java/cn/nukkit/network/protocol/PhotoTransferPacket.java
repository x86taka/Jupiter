package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class PhotoTransferPacket extends DataPacket {

    public String unknownString1;
    public String unknownString2;
    public String unknownString3;

    public static final byte NETWORK_ID = ProtocolInfo.PHOTO_TRANSFER_PACKET;

    @Override
    public void decode() {
        this.unknownString1 = this.getString();
        this.unknownString2 = this.getString();
        this.unknownString3 = this.getString();
    }

    @Override
    public void encode() {
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

}
