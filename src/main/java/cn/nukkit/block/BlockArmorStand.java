package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityArmorStand;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmorStand;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.BlockColor;

public class BlockArmorStand extends BlockTransparent{

    public BlockArmorStand() {
        this(0);
    }

    public BlockArmorStand(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return 0;
    }

	@Override
	public String getName() {
		return "Armor Stand";
	}
	
    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 10;
    }
    
    @Override
    public int getBurnChance() {
        return 5;
    }

    @Override
    public int getBurnAbility() {
        return 10;
    }
    
    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        return this.place(item, block, target, face, fx, fy, fz, null);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
    	if (face != BlockFace.DOWN) {
            CompoundTag nbt = new CompoundTag()
                    .putString("id", BlockEntity.ARMOR_STAND)
                    .putInt("x", (int) block.x)
                    .putInt("y", (int) block.y)
                    .putInt("z", (int) block.z);

            if (face == BlockFace.UP) {
                meta = (int) Math.floor(((player.yaw + 180) * 16 / 360) + 0.5) & 0x0f;
                getLevel().setBlock(block, new BlockArmorStand(meta), true);
            }

            if (player != null) {
                nbt.putString("Creator", player.getUniqueId().toString());
            }

            if (item.hasCustomBlockData()) {
                for (Tag aTag : item.getCustomBlockData().getAllTags()) {
                    nbt.put(aTag.getName(), aTag);
                }
            }

            new BlockEntityArmorStand(getLevel().getChunk((int) block.x >> 4, (int) block.z >> 4), nbt);

            return true;
        }

        return false;
    }

    @Override
    public Item toItem() {
        return new ItemArmorStand(this.meta & 0x03);
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.WOOD_BLOCK_COLOR;
    }
}
