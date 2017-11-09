package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class PurchaseReceiptPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PURCHASE_RECEIPT_PACKET;

    public String[] unknownStringArray;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        //TODO
    }

    @Override
    public void encode() {
        
    }
}
