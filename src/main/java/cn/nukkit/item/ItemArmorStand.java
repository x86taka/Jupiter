package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

public class ItemArmorStand extends Item {

    public ItemArmorStand() {
        this(0, 1);
    }

    public ItemArmorStand(Integer meta) {
        this(meta, 1);
    }

    public ItemArmorStand(Integer meta, int count) {
        super(ARMOR_STAND, meta, count, "ArmorStand");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        FullChunk chunk = level.getChunk((int) block.getX() >> 4, (int) block.getZ() >> 4);
        if (chunk == null) {
            return false;
        }

        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", block.getX() + 0.5))
                        .add(new DoubleTag("", block.getY()))
                        .add(new DoubleTag("", block.getZ() + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", this.getDirection(player.yaw)))
                        .add(new FloatTag("", 0)));

        if (this.hasCustomName()) {
            nbt.putString("CustomName", this.getCustomName());
        }

        Entity entity = Entity.createEntity("ArmorStand", chunk, nbt);

        if (entity != null) {
            if (player.isSurvival()) {
                Item item = player.getInventory().getItemInHand();
                item.setCount(item.getCount() - 1);
                player.getInventory().setItemInHand(item);
            }
            entity.spawnToAll();
            return true;
        }
        return false;
    }

    public float getDirection(double yaw) {
		double rotation = yaw % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if ((0 <= rotation && rotation < 22.5) || (337.5 <= rotation && rotation < 360)) {
			return 180f;
		} else if (22.5 <= rotation && rotation < 67.5) {
			return 225f;
		} else if (67.5 <= rotation && rotation < 112.5) {
			return 270f;
		} else if (112.5 <= rotation && rotation < 157.5) {
			return 315f;
		} else if (157.5 <= rotation && rotation < 202.5) {
			return 0f;
		} else if (202.5 <= rotation && rotation < 247.5) {
			return 45f;
		} else if (247.5 <= rotation && rotation < 292.5) {
			return 90f;
		} else if (292.5 <= rotation && rotation < 337.5) {
			return 135f;
		} else {
			return 0f;
		}
    }
}
