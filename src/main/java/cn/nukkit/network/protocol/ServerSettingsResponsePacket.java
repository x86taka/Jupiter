package cn.nukkit.network.protocol;

public class ServerSettingsResponsePacket extends DataPacket {

    public int formId;
    public String data;

    @Override
    public byte pid() {
        return ProtocolInfo.SERVER_SETTINGS_RESPONSE_PACKET;
    }

    @Override
    public void decode() {
    	this.formId = (int) this.getUnsignedVarInt();
    	this.data = this.getString();
    }

    @Override
    public void encode() {
        this.putVarInt(this.formId);
        this.putString(this.data);
    }
}