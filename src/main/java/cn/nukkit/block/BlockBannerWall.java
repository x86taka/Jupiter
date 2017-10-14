package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;

public class BlockBannerWall extends BlockBannerStanding{

	public BlockBannerWall() {
		this(0);
	}

	public BlockBannerWall(int meta) {
		super(meta);
	}

    @Override
    public int getId() {
        return WALL_BANNER;
    }

    @Override
    public String getName() {
        return "Wall Banner";
    }

    @Override
    public int onUpdate(int type) {
        int[] faces = {
                3,
                2,
                5,
                4,
        };
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (this.meta >= 2 && this.meta <= 5) {
                if (this.getSide(BlockFace.fromIndex(faces[this.meta - 2])).getId() == Item.AIR) {
                    this.getLevel().useBreakOn(this);
                }
                return Level.BLOCK_UPDATE_NORMAL;
            }
        }
        return 0;
    }
}
