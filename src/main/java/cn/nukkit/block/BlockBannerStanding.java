package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityBanner;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBanner;
import cn.nukkit.level.Level;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public class BlockBannerStanding extends BlockTransparent {

	public BlockBannerStanding() {
		this(0);
	}

	public BlockBannerStanding(int meta) {
		super(meta);
	}

	@Override
	public int getId() {
		return STANDING_BANNER;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public String getName() {
		return "Standing Banner";
	}

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
    	if (face == BlockFace.DOWN) {
    		return false;
    	}

    	CompoundTag nbt = new CompoundTag()
    			.putString("id", BlockEntity.BANNER)
                .putInt("x", (int) block.x)
                .putInt("y", (int) block.y)
                .putInt("z", (int) block.z)
                .putInt("Base", item.getDamage());

    	if (item.hasCompoundTag()) {
    		CompoundTag tag = item.getNamedTag();
    		if (tag.contains("Patterns")) {
    			nbt.putList(tag.getList("Patterns"));
    		}
    	}

        if (face == BlockFace.UP) {
            meta = (int) Math.floor(((player.yaw + 180) * 16 / 360) + 0.5) & 0x0f;
            getLevel().setBlock(block, new BlockBannerStanding(meta), true);
        } else {
            meta = face.getIndex();
            getLevel().setBlock(block, new BlockBannerWall(meta), true);
        }

        new BlockEntityBanner(getLevel().getChunk((int) block.x >> 4, (int) block.z >> 4), nbt);
        return true;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (down().getId() == Block.AIR) {
                getLevel().useBreakOn(this);

                return Level.BLOCK_UPDATE_NORMAL;
            }
        }

        return 0;
    }

    @Override
    public Item toItem() {
    	BlockEntity blockEntity = this.level.getBlockEntity(this);
    	if (blockEntity != null && blockEntity instanceof BlockEntityBanner) {
    		int id = ((BlockEntityBanner) blockEntity).getBase();
    		ListTag<CompoundTag> tag = ((BlockEntityBanner) blockEntity).getPatterns();
    		ItemBanner item = new ItemBanner(id, 1);
    		for (CompoundTag nbt : tag.getAll()) {
    			item.addPatterns(nbt);
    		}
    		return item;
    	} else {
    		return new ItemBanner();
    	}
    }
}
