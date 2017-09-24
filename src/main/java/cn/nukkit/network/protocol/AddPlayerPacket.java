package cn.nukkit.network.protocol;

import java.util.UUID;

import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Binary;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class AddPlayerPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.ADD_PLAYER_PACKET;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public UUID uuid;
    public String username;
    public long entityUniqueId;
    public long entityRuntimeId;
    public float x;
    public float y;
    public float z;
    public float speedX;
    public float speedY;
    public float speedZ;
    public float pitch;
    public float yaw;
    public Item item;
    public EntityMetadata metadata = new EntityMetadata();
    
    public long ubarInt1 = 0;
    public long ubarInt2 = 0;
    public long ubarInt3 = 0;
    public long ubarInt4 = 0;
    public long ubarInt5 = 0;
    
    public long long1;

    @Override
    public void decode() {
    	this.uuid = this.getUUID();
    	this.username = this.getString();
    	this.entityUniqueId = this.getVarLong();
    	this.entityRuntimeId = this.getVarLong();
    	this.x = this.getVector3f().x;
    	this.y = this.getVector3f().y;
    	this.z = this.getVector3f().z;
    	this.speedX = this.getVector3f().x;
    	this.speedY = this.getVector3f().y;
    	this.speedZ = this.getVector3f().z;
    	this.pitch = this.getLFloat();
    	this.yaw = this.getLFloat();
    	this.item = this.getSlot();
    	
    	this.long1 = this.getLLong();
    	
    	this.ubarInt1 = (int) this.getUnsignedVarInt();
    	this.ubarInt2 = (int) this.getUnsignedVarInt();
    	this.ubarInt3 = (int) this.getUnsignedVarInt();
    	this.ubarInt4 = (int) this.getUnsignedVarInt();
    	this.ubarInt5 = (int) this.getUnsignedVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putUUID(this.uuid);
        this.putString(this.username);
        this.putVarLong(this.entityUniqueId);
        this.putVarLong(this.entityRuntimeId);
        this.putVector3f(this.x, this.y, this.z);
        this.putVector3f(this.speedX, this.speedY, this.speedZ);
        this.putLFloat(this.pitch);
        this.putLFloat(this.yaw); //TODO headrot
        this.putLFloat(this.yaw);
        this.putSlot(this.item);
        
        this.putLLong(long1);
        
        this.putUnsignedVarInt(ubarInt1);
        this.putUnsignedVarInt(ubarInt2);
        this.putUnsignedVarInt(ubarInt3);
        this.putUnsignedVarInt(ubarInt4);
        this.putUnsignedVarInt(ubarInt5);

        this.put(Binary.writeMetadata(this.metadata));
    }
}
