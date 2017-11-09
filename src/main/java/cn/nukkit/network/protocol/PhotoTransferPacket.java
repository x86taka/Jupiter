package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class PhotoTransferPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PHOTO_TRANSFER_PACKET;

    public String photoName;
    public String photoData;
    public String bookId;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.photoName = this.getString();
        this.photoData = this.getString();
        this.bookId = this.getString();
    }

    @Override
    public void encode() {

    }

}
