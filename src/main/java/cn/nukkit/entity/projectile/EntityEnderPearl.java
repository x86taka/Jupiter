package cn.nukkit.entity.projectile;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

public class EntityEnderPearl extends EntityProjectile{

	public static final int NETWORK_ID = 87;

	public float width = 0.25f;
	public float length = 0.25f;
	public float height = 0.25f;

	protected float gravity = 0.03f;
	protected float drag = 0.01f;

	private boolean hasTeleportedShooter = false;

	public EntityEnderPearl(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
		super(chunk, nbt, shootingEntity);
	}

	public EntityEnderPearl(FullChunk chunk, CompoundTag nbt){
		this(chunk, nbt, null);
	}

	@Override
	public int getNetworkId() {
		return NETWORK_ID;
	}

	@Override
	public float getWidth(){
		return width;
	}

	@Override
	public float getLength(){
		return length;
	}

	@Override
	public float getHeight(){
		return height;
	}

	@Override
	protected float getGravity(){
		return gravity;
	}

	@Override
	protected float getDrag(){
		return drag;
	}

	public void teleportShooter(){
		if(!this.hasTeleportedShooter){
			this.hasTeleportedShooter = true;
			if(this.shootingEntity instanceof Player && this.y > 0){
				this.shootingEntity.attack(new EntityDamageEvent(this.shootingEntity, DamageCause.FALL, 5));
				this.shootingEntity.teleport(this.getLocation());
			}
			this.kill();
		}
	}

	@Override
	public boolean onUpdate(int currentTick){
		if(this.closed){
			return false;
		}

		this.timing.startTiming();

		boolean hasUpdate = super.onUpdate(currentTick);

        if (this.age > 1200 || this.isCollided) {
            this.teleportShooter();
            hasUpdate = true;
        }

        this.timing.stopTiming();

        return hasUpdate;
	}

	@Override
	public void spawnTo(Player player){
        AddEntityPacket pk = new AddEntityPacket();
        pk.type = EntityEnderPearl.NETWORK_ID;
        pk.entityUniqueId = this.getId();
        pk.entityRuntimeId = this.getId();
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.speedX = (float) this.motionX;
        pk.speedY = (float) this.motionY;
        pk.speedZ = (float) this.motionZ;
        pk.metadata = this.dataProperties;
        player.dataPacket(pk);

        super.spawnTo(player);
	}
}
