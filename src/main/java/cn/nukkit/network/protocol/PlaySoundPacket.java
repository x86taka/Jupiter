package cn.nukkit.network.protocol;

public class PlaySoundPacket extends DataPacket{
	
	public static final byte NETWORK_ID = ProtocolInfo.PLAY_SOUND_PACKET;
	
	public String sound;
	public int x;
	public int y;
	public int z;
	public float volume;
	public float float2;

	@Override
	public byte pid() {
		return NETWORK_ID;
	}

	@Override
	public void decode() {
		this.sound = this.getString();
		this.x = this.getBlockCoords().x;
		this.y = this.getBlockCoords().y;
		this.z = this.getBlockCoords().z;
		this.volume = this.getLFloat();
		this.float2 = this.getLFloat();
	}

	@Override
	public void encode() {
		this.reset();
		this.putString(this.sound);
		this.putBlockCoords(this.x, this.y, this.z);
		this.putLFloat(this.volume);
		this.putLFloat(this.float2);
	}

}
