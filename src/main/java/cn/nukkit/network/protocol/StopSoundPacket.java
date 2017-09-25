package cn.nukkit.network.protocol;

public class StopSoundPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.STOP_SOUND_PACKET;

    public String name;
    public boolean stopAll;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
    	this.name = this.getString();
    	this.stopAll = this.getBoolean();
    }

    @Override
    public void encode() {
        this.putString(this.name);
        this.putBoolean(this.stopAll);
    }
}
