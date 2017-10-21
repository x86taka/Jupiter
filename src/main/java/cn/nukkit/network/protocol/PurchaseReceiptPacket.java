package cn.nukkit.network.protocol;

/**
 * @author Megapix96
 */
public class PurchaseReceiptPacket extends DataPacket {

    public String[] unknownStringArray;

    @Override
    public byte pid() {
        return ProtocolInfo.PURCHASE_RECEIPT_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        
    }
}
