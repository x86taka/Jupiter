package cn.nukkit.block;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityMobSpawner;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.BlockColor;

/**
 * Created by Pub4Game on 27.12.2015.
 */
public class BlockMobSpawner extends BlockSolid {

    public BlockMobSpawner() {
        this(0);
    }

    public BlockMobSpawner(int meta) {
        super(0);
    }

    @Override
    public String getName() {
        return "Monster Spawner";
    }

    @Override
    public int getId() {
        return MONSTER_SPAWNER;
    }

    @Override
    public BlockColor getColor(){
    	return BlockColor.BLACK_BLOCK_COLOR;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public double getResistance() {
        return 25;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean onActivate(Item item) {
        return this.onActivate(item, null);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (this.getDamage() == 0 && item.getId() == Item.SPAWN_EGG) {
            BlockEntity blockEntity = this.getLevel().getBlockEntity(this);
            if (blockEntity instanceof BlockEntityMobSpawner) {
                this.setDamage(item.getDamage());
                ((BlockEntityMobSpawner) blockEntity).setNetworkId(item.getDamage());
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        this.getLevel().setBlock(block, this, true, true);
        CompoundTag nbt = new CompoundTag("")
                .putString("id", BlockEntity.MOB_SPAWNER)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putInt("EntityId", 0);

        if (item.hasCustomBlockData()) {
            Map<String, Tag> customData = item.getCustomBlockData().getTags();
            for (Map.Entry<String, Tag> tag : customData.entrySet()) {
                nbt.put(tag.getKey(), tag.getValue());
            }
        }

        new BlockEntityMobSpawner(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[0];
    }
}
