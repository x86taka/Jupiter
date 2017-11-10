package cn.nukkit.entity.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

/**
 * @author Megapix96
 */
//TODO: 残留ポーション
public class EntityAreaEffectCloud extends Entity {

    public static final int NETWORK_ID = 95;

    @Override
    public boolean canCollide() {
        return false;
    }

    public EntityAreaEffectCloud(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean onUpdate(int currentTick) {
		return true;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        super.initEntity();
    }

    @Override
    public void spawnTo(Player player) {
        AddEntityPacket packet = new AddEntityPacket();
        packet.type = EntityAreaEffectCloud.NETWORK_ID;
        packet.entityUniqueId = this.getId();
        packet.entityRuntimeId = getId();
        packet.x = (float) x;
        packet.y = (float) y;
        packet.z = (float) z;
        packet.speedX = (float) motionX;
        packet.speedY = (float) motionY;
        packet.speedZ = (float) motionZ;
        packet.yaw = (float) yaw;
        packet.pitch = (float) pitch;
        packet.metadata = dataProperties;
        player.dataPacket(packet);
        super.spawnTo(player);
    }

}
