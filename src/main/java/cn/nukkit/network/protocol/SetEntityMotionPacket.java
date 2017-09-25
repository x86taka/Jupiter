package cn.nukkit.network.protocol;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class SetEntityMotionPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.SET_ENTITY_MOTION_PACKET;

    public long eid;
    public float motionX;
    public float motionY;
    public float motionZ;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
    	this.eid = this.getVarLong();
    	this.motionX = this.getVector3f().x;
    	this.motionY = this.getVector3f().y;
    	this.motionZ = this.getVector3f().z;
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarLong(this.eid);
        this.putVector3f(this.motionX, this.motionY, this.motionZ);
    }
}
