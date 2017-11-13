package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.InventoryContentPacket;
import cn.nukkit.network.protocol.InventorySlotPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.network.protocol.types.ContainerIds;

public class PlayerOffhandInventory extends BaseInventory {

	public PlayerOffhandInventory(EntityHumanType holder) {
		super(holder, InventoryType.OFFHAND);
	}

	@Override
	public EntityHuman getHolder() {
		return (EntityHuman) super.getHolder();
	}

	@Override
	public void onSlotChange(int index, Item before, boolean send) {
		EntityHuman holder = this.getHolder();
		if (holder instanceof Player && !((Player) holder).spawned) {
			return;
		}
		sendOffhandItem(this.getHolder().getViewers().values().stream().toArray(Player[]::new));
		super.onSlotChange(index, before, send);
	}

	public void sendContents(Player... players) {
		InventoryContentPacket pk = new InventoryContentPacket();
		pk.slots = new Item[this.getSize()];
		for (int i = 0; i < this.getSize(); ++i) {
			pk.slots[i] = this.getItem(i);
		}

		for (Player player : players) {
			int id = player.getWindowId(this);
			if (id == -1 || !player.spawned) {
				this.close(player);
				continue;
			}
			pk.inventoryId = id;
			player.dataPacket(pk.clone());
		}
	}

	@Override
	public void sendSlot(int index, Player... players) {
		InventorySlotPacket pk = new InventorySlotPacket();
		pk.slot = index;
		pk.item = this.getItem(index).clone();

		for (Player player : players) {
			if (player.equals(this.getHolder())) {
				pk.inventoryId = ContainerIds.OFFHAND;
				player.dataPacket(pk);
			} else {
				int id = player.getWindowId(this);
				if (id == -1) {
					this.close(player);
					continue;
				}
				pk.inventoryId = id;
				player.dataPacket(pk.clone());
			}
		}
	}

	public void sendOffhandItem(Player player) {
		sendOffhandItem(new Player[]{player});
	}

	public void sendOffhandItem(Player[] players) {
		Item item = this.getItem(0);

		MobEquipmentPacket pk = new MobEquipmentPacket();
		pk.item = item;
		pk.inventorySlot = pk.hotbarSlot = 0;
		pk.windowId = ContainerIds.OFFHAND;

		for (Player player : players) {
			pk.entityRuntimeId = this.getHolder().getId();
			player.dataPacket(pk);
		}
    }
}
