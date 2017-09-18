package cn.nukkit.network.protocol;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class AvailableCommandsPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.AVAILABLE_COMMANDS_PACKET;
    public String commands; //JSON-encoded command data
    public String unknown = "";
    
    public static final int ARG_FLAG_VALID = 0x100000;
    public static final int ARG_TYPE_INT       = 0x01;
    public static final int ARG_TYPE_FLOAT     = 0x02;
    public static final int ARG_TYPE_VALUE     = 0x03;
    public static final int ARG_TYPE_TARGET    = 0x04;
    public static final int ARG_TYPE_STRING    = 0x0c;
    public static final int ARG_TYPE_POSITION  = 0x0d;
    public static final int ARG_TYPE_RAWTEXT   = 0x10;
    public static final int ARG_TYPE_TEXT      = 0x12;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(this.commands);
        this.putString(this.unknown);
    }
}
