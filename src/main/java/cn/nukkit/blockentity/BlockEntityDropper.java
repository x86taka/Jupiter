package cn.nukkit.blockentity;

import java.util.ArrayList;
import java.util.Random;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.inventory.DropperInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author Megapix96
 */
public class BlockEntityDropper extends BlockEntitySpawnable implements InventoryHolder, BlockEntityContainer, BlockEntityNameable {

    protected final DropperInventory inventory;

    public BlockEntityDropper(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.inventory = new DropperInventory(this);

        if (!namedTag.contains("Items") || !(namedTag.get("Items") instanceof ListTag)) {
            namedTag.putList(new ListTag<CompoundTag>("Items"));
        }

        for (int i = 0; i < getSize(); i++) {
            inventory.setItem(i, this.getItem(i));
        }
    }

    @Override
    public void saveNBT() {
        namedTag.putList(new ListTag<CompoundTag>("Items"));
        for (int index = 0; index < getSize(); index++) {
            this.setItem(index, inventory.getItem(index));
        }
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getBlock().getId() == Block.DROPPER;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getName() {
        return this.hasName() ? this.namedTag.getString("CustomName") : "Dropper";
    }

    @Override
    public boolean hasName() {
        return namedTag.contains("CustomName");
    }

    @Override
    public void setName(String name) {
        if (name == null || name.equals("")) {
            namedTag.remove("CustomName");
            return;
        }

        namedTag.putString("CustomName", name);
    }

    protected int getSlotIndex(int index) {
        ListTag<CompoundTag> list = this.namedTag.getList("Items", CompoundTag.class);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getByte("Slot") == index) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Item getItem(int index) {
        int i = this.getSlotIndex(index);
        if (i < 0) {
            return new ItemBlock(new BlockAir(), 0, 0);
        } else {
            CompoundTag data = (CompoundTag) this.namedTag.getList("Items").get(i);
            return NBTIO.getItemHelper(data);
        }
    }

    @Override
    public void setItem(int index, Item item) {
        int i = this.getSlotIndex(index);

        CompoundTag d = NBTIO.putItemHelper(item, index);

        if (item.getId() == Item.AIR || item.getCount() <= 0) {
            if (i >= 0) {
                this.namedTag.getList("Items").getAll().remove(i);
            }
        } else if (i < 0) {
            (this.namedTag.getList("Items", CompoundTag.class)).add(d);
        } else {
            (this.namedTag.getList("Items", CompoundTag.class)).add(i, d);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public CompoundTag getSpawnCompound() {
        CompoundTag nbt = new CompoundTag()
                .putString("id", BlockEntity.DROPPER)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z);

        if (this.hasName()) {
            nbt.put("CustomName", namedTag.get("CustomName"));
        }

        return nbt;
    }

    public Vector3 getMotion() {
        switch (this.getBlock().getDamage()) {
            case 0:
                return new Vector3(0, -1, 0);
            case 1:
                return new Vector3(0, 1, 0);
            case 2:
                return new Vector3(0, 0, -1);
            case 3:
                return new Vector3(0, 0, 1);
            case 4:
                return new Vector3(-1, 0, 0);
            case 5:
                return new Vector3(1, 0, 0);
            default:
                return new Vector3(0, 0, 0);
        }
    }

    public void activate() {
        ArrayList<Integer> itemIndex = new ArrayList<Integer>();
        for (int index = 0; index < this.getSize(); index++) {
            Item item = this.getInventory().getItem(index);
            if (item.getId() != 0) {
                itemIndex.add(index);
            }
        }
        Item item = null;
        int index;
        int max = itemIndex.size() - 1;
        if (max < 0) {
            return;
        } else if (max == 0) {
            index = itemIndex.get(0);
            item = this.getInventory().getItem(index);
        } else {
            index = itemIndex.get(new Random().nextInt(itemIndex.size()));
            item = this.getInventory().getItem(index);
        }
        if (item == null) {
            return;
        }
        item.setCount(item.getCount() - 1);
        this.getInventory().setItem(index, item.getCount() > 0 ? item : Item.get(0));
        Item dropItem = Item.get(item.getId(), item.getDamage(), 1);
        Vector3 motion = this.getMotion();
        Block block = this.getLevel().getBlock(new Vector3(this.x + motion.x, this.y + motion.y, this.z + motion.z));
        switch (block.getId()) {
            case Block.CHEST:
            case Block.TRAPPED_CHEST:
            case Block.DROPPER:
            case Block.DISPENSER:
            case Block.BREWING_STAND_BLOCK:
            case Block.FURNACE:
                BlockEntity blockEntity = this.getLevel().getBlockEntity(block);
                if (blockEntity instanceof InventoryHolder) {
                    if (((InventoryHolder) blockEntity).getInventory().canAddItem(dropItem)) {
                        ((InventoryHolder) blockEntity).getInventory().addItem(dropItem);
                        return;
                    }
                }
        }
        this.getLevel().dropItem(new Vector3(this.x + motion.x * 2 + 0.5, this.y + (motion.y < 0 ? motion.y : 0.5), this.z + motion.z * 2 + 0.5), dropItem, motion.multiply(0.3));
        for (int i = 0; i < 10; i++) {
            this.getLevel().addParticle(new SmokeParticle(motion.add(motion.x * i * 0.3 + 0.5, motion.y == 0 ? 0.5 : motion.y * i * 0.3, motion.z * i * 0.3 + 0.5)));
        }
    }
}
