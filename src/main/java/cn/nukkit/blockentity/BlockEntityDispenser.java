package cn.nukkit.blockentity;

import java.util.ArrayList;
import java.util.Random;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.inventory.DispenserInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author Megapix96
 */
public class BlockEntityDispenser extends BlockEntitySpawnable implements InventoryHolder, BlockEntityContainer, BlockEntityNameable {

    protected final DispenserInventory inventory;

    public BlockEntityDispenser(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.inventory = new DispenserInventory(this);

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
        return this.getBlock().getId() == Block.DISPENSER;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getName() {
        return this.hasName() ? this.namedTag.getString("CustomName") : "Dispenser";
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
                .putString("id", BlockEntity.DISPENSER)
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
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", this.x + motion.x * 2 + 0.5))
                        .add(new DoubleTag("", this.y + (motion.y > 0 ? motion.y : 0.5)))
                        .add(new DoubleTag("", this.z + motion.z * 2 + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", motion.x))
                        .add(new DoubleTag("", motion.y))
                        .add(new DoubleTag("", motion.z)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)));
        Entity entity;
        switch (dropItem.getId()) {
            case Item.ARROW:
                entity = Entity.createEntity("Arrow", this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
                entity.setMotion(motion.multiply(1.5));
                entity.spawnToAll();
                return;
            case Item.SNOWBALL:
                entity = Entity.createEntity("SnowBall", this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
                entity.setMotion(motion.multiply(1.5));
                entity.spawnToAll();
                return;
            case Item.EGG:
                entity = Entity.createEntity("Egg", this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
                entity.setMotion(motion.multiply(1.5));
                entity.spawnToAll();
                return;
            case Item.SPLASH_POTION:
                entity = Entity.createEntity("ThrownPotion", this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
                entity.setMotion(motion.multiply(1.5));
                entity.spawnToAll();
                return;
            case Item.EXPERIENCE_BOTTLE:
                entity = Entity.createEntity("ThrownExpBottle", this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
                entity.setMotion(motion.multiply(1.5));
                entity.spawnToAll();
                return;
            case Item.BUCKET:
                Block block = this.getLevel().getBlock(new Vector3(this.x + motion.x, this.y + motion.y, this.z + motion.z));
                switch (item.getDamage()) {
                    case 0:
                        if (block.getDamage() != 0) {
                            break;
                        }
                        switch (block.getId()) {
                            case 9:
                                this.getLevel().setBlock(block, Block.get(0));
                                dropItem = Item.get(325, 8, 1);
                                if (this.getInventory().canAddItem(dropItem)) {
                                    this.getInventory().addItem(dropItem);
                                    return;
                                }
                                break;
                            case 10:
                                this.getLevel().setBlock(block, Block.get(0));
                                dropItem = Item.get(325, 10, 1);
                                if (this.getInventory().canAddItem(dropItem)) {
                                    this.getInventory().addItem(dropItem);
                                    return;
                                }
                                break;
                        }
                        break;
                    case 8:
                        if (block.getId() == 0) {
                            this.getLevel().setBlock(block, Block.get(9));
                            this.getInventory().setItem(index, Item.get(325, 0, 1));
                            return;
                            }
                        break;
                    case 10:
                        if (block.getId() == 0) {
                            this.getLevel().setBlock(block, Block.get(10));
                            this.getInventory().setItem(index, Item.get(325, 0, 1));
                            return;
                        }
                        break;
                }
                break;
        }
        this.getLevel().dropItem(new Vector3(this.x + motion.x * 2 + 0.5, this.y + (motion.y < 0 ? motion.y : 0.5), this.z + motion.z * 2 + 0.5), dropItem, motion.multiply(0.3));
        for (int i = 0; i < 10; i++) {
            this.getLevel().addParticle(new SmokeParticle(motion.add(motion.x * i * 0.3 + 0.5, motion.y == 0 ? 0.5 : motion.y * i * 0.3, motion.z * i * 0.3 + 0.5)));
        }
    }
}
