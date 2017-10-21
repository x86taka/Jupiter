package cn.nukkit.entity.item;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.MobArmorEquipmentPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;

/**
 * @author tedo0627
 */
public class EntityArmorStand extends EntityInteractable {

    public static final int NETWORK_ID = 61;

	public EntityArmorStand(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);

		if (!nbt.contains("HandItems")) {
			nbt.putCompound("HandItems", NBTIO.putItemHelper(Item.get(0)));
		}
		if (!nbt.contains("ArmorItems")) {
			ListTag<CompoundTag> tag = new ListTag<CompoundTag>("ArmorItems")
					.add(NBTIO.putItemHelper(Item.get(0)))
					.add(NBTIO.putItemHelper(Item.get(0)))
					.add(NBTIO.putItemHelper(Item.get(0)))
					.add(NBTIO.putItemHelper(Item.get(0)));
			nbt.putList(tag);
		}

		this.setHealth(2);
		this.setMaxHealth(2);
	}

	@Override
	public int getNetworkId() {
		return NETWORK_ID;
	}

	@Override
	public void spawnTo(Player player) {
		AddEntityPacket pk = new AddEntityPacket();
		pk.entityRuntimeId = this.getId();
		pk.entityUniqueId = this.getId();
		pk.type = this.getNetworkId();
		pk.x = (float) this.x;
		pk.y = (float) this.y;
		pk.z = (float) this.z;
		pk.speedX = (float) this.motionX;
		pk.speedY = (float) this.motionY;
		pk.speedZ = (float) this.motionZ;
		pk.yaw = (float) this.yaw;
		pk.pitch = (float) this.pitch;
		player.dataPacket(pk);

		sendArmor(player);
		sendHand(player);

		super.spawnTo(player);
	}

    public boolean onInteract(Player player, Item item) {
    	Item change;
    	if (item.getId() == 0) {
    		this.sendHand(player);
    		this.sendArmor(player);
    		player.getInventory().sendContents(player);
    		return false;
    	} else if (item.isHelmet()) {
    		change = this.getHelmet();
    		this.setHelmet(item.clone());
    	} else if (item.isChestplate()) {
    		change = this.getChestPlate();
    		this.setChestPlate(item.clone());
    	} else if (item.isLeggings()) {
    		change = this.getLeggins();
    		this.setLeggins(item.clone());
    	} else if (item.isBoots()) {
    		change = this.getBoots();
    		this.setBoots(item.clone());
    	} else {
    		if (item.getCount() > 1) {
    			change = item.clone();
    			change.setCount(change.getCount() - 1);
    	    	player.getInventory().setItemInHand(change);
    			player.getInventory().addItem(this.getHand());
    			item.setCount(1);
    			this.setHand(item.clone());
    			return false;
    		} else {
    			change = this.getHand();
    			this.setHand(item);
    		}
    	}
    	player.getInventory().setItemInHand(change);
        return false;
    }

	@Override
	public String getInteractButtonText() {
		return "装備";
	}

	@Override
	public boolean canDoInteraction() {
		return false;
	}

    @Override
    public boolean onUpdate(int currentTick) {
    	if (super.onUpdate(currentTick)) {
    		if (this.level.getBlock(this.down(), false).getId() == 0) {
    			this.setMotion(this.down());
    		}
    		return true;
    	}
    	return false;
    }

	@Override
	public void kill() {
		this.level.dropItem(this, Item.get(Item.ARMOR_STAND));
		this.level.dropItem(this, this.getHand());
		this.level.dropItem(this, this.getHelmet());
		this.level.dropItem(this, this.getChestPlate());
		this.level.dropItem(this, this.getLeggins());
		this.level.dropItem(this, this.getBoots());
		super.kill();
	}

	public void sendHand(Player player) {
		MobEquipmentPacket pk = new MobEquipmentPacket();
		pk.entityRuntimeId = this.getId();
		pk.inventorySlot = 0;
		pk.hotbarSlot = 0;
		pk.item = this.getHand();
		player.dataPacket(pk);
	}

	public void sendArmor(Player player) {
		MobArmorEquipmentPacket pk = new MobArmorEquipmentPacket();
		pk.eid = this.getId();
		pk.slots = new Item[]{this.getHelmet(), this.getChestPlate(), this.getLeggins(), this.getBoots()};
		player.dataPacket(pk);
	}

	public void sendArmorAll() {
        for (Player player : this.level.getChunkPlayers(this.chunk.getX(), this.chunk.getZ()).values()) {
            if (player.isOnline()) {
                this.sendArmor(player);
            }
        }
	}

	public void setHand(Item item) {
		this.namedTag.putCompound("HandItems", NBTIO.putItemHelper(item));
		this.sendArmorAll();
	}

	public Item getHand() {
		return NBTIO.getItemHelper(this.namedTag.getCompound("HandItems"));
	}

	public void setHelmet(Item item) {
		this.namedTag.getList("ArmorItems", CompoundTag.class).add(0, NBTIO.putItemHelper(item));
		this.sendArmorAll();
	}

	public Item getHelmet() {
		return NBTIO.getItemHelper(this.namedTag.getList("ArmorItems", CompoundTag.class).get(0));
	}

	public void setChestPlate(Item item) {
		this.namedTag.getList("ArmorItems", CompoundTag.class).add(1, NBTIO.putItemHelper(item));
		this.sendArmorAll();
	}

	public Item getChestPlate() {
		return NBTIO.getItemHelper(this.namedTag.getList("ArmorItems", CompoundTag.class).get(1));
	}

	public void setLeggins(Item item) {
		this.namedTag.getList("ArmorItems", CompoundTag.class).add(2, NBTIO.putItemHelper(item));
		this.sendArmorAll();
	}

	public Item getLeggins() {
		return NBTIO.getItemHelper(this.namedTag.getList("ArmorItems", CompoundTag.class).get(2));
	}

	public void setBoots(Item item) {
		this.namedTag.getList("ArmorItems", CompoundTag.class).add(3, NBTIO.putItemHelper(item));
		this.sendArmorAll();
	}

	public Item getBoots() {
		return NBTIO.getItemHelper(this.namedTag.getList("ArmorItems", CompoundTag.class).get(3));
	}
}
