package cn.nukkit.network.protocol;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BatchPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.BATCH_PACKET;

    public byte[] payload;
    public boolean compressed = false;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        //this.payload = this.getByteArray();
    	//this.payload = this.get(1);
    }

    @Override
    public void encode() {
    	/*
        this.reset();
        //this.putByteArray(this.payload);
        this.put(payload);
        */
    }
}
