package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.block.BlockSpreadEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.object.ObjectTallGrass;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.BlockColor;

/**
 * author: Angelic47
 * Nukkit Project
 */
public class BlockGrass extends BlockDirt {

    public BlockGrass() {
        this(0);
    }

    public BlockGrass(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return GRASS;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public double getResistance() {
        return 3;
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public boolean onActivate(Item item) {
        return this.onActivate(item, null);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == Item.DYE && item.getDamage() == 0x0F) {
            item.count--;
            ObjectTallGrass.growGrass(this.getLevel(), this, new NukkitRandom(), 15, 10);
            return true;
        } else if (item.isHoe()) {
            item.useOn(this);
            this.getLevel().setBlock(this, new BlockFarmland());
            return true;
        } else if (item.isShovel()) {
            item.useOn(this);
            this.getLevel().setBlock(this, new BlockGrassPath());
            return true;
        }

        return false;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            int time = this.level.getTime();
            Block block = this.level.getBlock(this.up());
            if (1000 < time && time < 12000) {
                if (block.isSolid()) {
                    BlockSpreadEvent ev = new BlockSpreadEvent(block, this, new BlockDirt());
                    Server.getInstance().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        this.level.setBlock(this, ev.getNewState());
                    }
                } else {
                    for (int l = 0; l < 4; ++l) {
                        NukkitRandom random = new NukkitRandom();
                        int x = random.nextRange((int) this.x - 1, (int) this.x + 1);
                        int y = random.nextRange((int) this.y - 2, (int) this.y + 2);
                        int z = random.nextRange((int) this.z - 1, (int) this.z + 1);
                        Block target = this.level.getBlock(new Vector3(x, y, z));
                        if (target.getId() == Block.DIRT && target.getDamage() == 0 && !this.level.getBlock(target.up()).isSolid()) {
                            BlockSpreadEvent ev = new BlockSpreadEvent(target, this, new BlockGrass());
                            Server.getInstance().getPluginManager().callEvent(ev);
                            if (!ev.isCancelled()) {
                                this.level.setBlock(target, ev.getNewState());
                            }
                        }
                    }
                }
            } else {
                if (block.isSolid()) {
                    BlockSpreadEvent ev = new BlockSpreadEvent(block, this, new BlockDirt());
                    Server.getInstance().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        this.level.setBlock(this, ev.getNewState());
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                item.isSilkTouch() ? this.toItem() : new ItemBlock(new BlockDirt())
        };
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.GRASS_BLOCK_COLOR;
    }
}
