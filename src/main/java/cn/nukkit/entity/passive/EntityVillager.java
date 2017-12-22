package cn.nukkit.entity.passive;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityAgeable;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.TradingInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.AddEntityPacket;

/**
 * Created by Pub4Game on 21.06.2016.
 */
public class EntityVillager extends EntityCreature implements EntityAgeable, InventoryHolder {

    public static final int PROFESSION_FARMER = 0;
    public static final int PROFESSION_LIBRARIAN = 1;
    public static final int PROFESSION_PRIEST = 2;
    public static final int PROFESSION_BLACKSMITH = 3;
    public static final int PROFESSION_BUTCHER = 4;
    public static final int PROFESSION_GENERIC = 5;
    public static final int NETWORK_ID = 15;

    public EntityVillager(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.3f;
        }
        return 0.6f;
    }

    @Override
    public float getLength() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.975f;
        }
        return 1.95f;
    }

    @Override
    public String getName() {
        return "Villager";
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(20);
        super.initEntity();
        if (!this.namedTag.contains("Profession")) {
            this.setProfession(PROFESSION_GENERIC);
        }
    }

    public int getProfession() {
        return this.namedTag.getInt("Profession");
    }

    public void setProfession(int profession) {
        this.namedTag.putInt("Profession", profession);
    }

    @Override
    public boolean isBaby() {
        return this.getDataFlag(DATA_FLAGS, Entity.DATA_FLAG_BABY);
    }

    @Override
    public void spawnTo(Player player) {
        if (!this.hasSpawned.containsKey(player.getLoaderId())) {
            AddEntityPacket pk = new AddEntityPacket();
            pk.type = this.getNetworkId();
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

    @Override
    public boolean onInteract(Player player, Item item) {
        player.addWindow(this.getInventory());
        return true;
    }

    public void addTradeItems(byte rewardExp, int maxUses, int uses, Item buyA, Item sell) {
        this.addTradeItems(rewardExp, maxUses, uses, buyA, Item.get(0), sell);
    }

    public void addTradeItems(byte rewardExp, int maxUses, int uses, Item buyA, Item buyB, Item sell) {
        CompoundTag tag;
        if (this.namedTag.contains("Offers")) {
            tag = this.namedTag.getCompound("Offers");
        } else {
        	tag = new CompoundTag().putList(new ListTag<CompoundTag>("Recipes"));
        }
        CompoundTag nbt = new CompoundTag()
                .putByte("rewardExp", rewardExp)
                .putInt("maxUses", maxUses)
                .putInt("uses", uses)
                .putCompound("buyA", NBTIO.putItemHelper(buyA))
                .putCompound("buyB", NBTIO.putItemHelper(buyB))
                .putCompound("sell", NBTIO.putItemHelper(sell));
        tag.getList("Recipes", CompoundTag.class).add(nbt);
        this.namedTag.putCompound("Offers", tag);
    }

    public CompoundTag getOffers() {
        if (this.namedTag.contains("Offers")) {
            return this.namedTag.getCompound("Offers");
        }
        return null;
    }

    @Override
    public Inventory getInventory() {
        return new TradingInventory(this);
    }
}
