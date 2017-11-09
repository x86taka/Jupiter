package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;

/**
 * Created on 15-10-15.
 */
public class InteractPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.INTERACT_PACKET;

    public static final int ACTION_VEHICLE_EXIT = 3;
    public static final int ACTION_MOUSEOVER = 4;

    public static final int ACTION_OPEN_INVENTORY = 6;

    public int action;
    public long target;
    public Vector3f targetPosition;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.action = this.getByte();
        this.target = this.getEntityRuntimeId();
        if(this.action == ACTION_MOUSEOVER) {
            this.targetPosition = this.getVector3f();
        }
    }

    @Override
    public void encode() {

    }

}
