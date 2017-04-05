package cn.nukkit.network.protocol;

public class StopSoundPacket extends DataPacket{
	
	public static final byte NETWORK_ID = ProtocolInfo.STOP_SOUND_PACKET;
	
	public String sound;
	public boolean stopAll;

	@Override
	public byte pid() {
		return NETWORK_ID;
	}

	@Override
	public void decode() {
		this.sound = this.getString();
		this.stopAll = this.getBoolean();
	}

	@Override
	public void encode() {
		this.reset();
		this.putString(this.sound);
		this.putBoolean(this.stopAll);
	}

}
