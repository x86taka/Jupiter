package cn.nukkit.block;

import java.util.Random;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRedstone;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.Level;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.utils.BlockColor;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockOreRedstone extends BlockSolid {

    public BlockOreRedstone() {
        this(0);
    }

    public BlockOreRedstone(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return REDSTONE_ORE;
    }

    @Override
    public double getHardness() {
        return 3;
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Redstone Ore";
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_IRON) {
            if (item.isSilkTouch()){
                return new Item[]{
                        this.toItem()
                };
            } else {
                return new Item[]{
                        new ItemRedstone(0, new Random().nextInt(1) + 4)
                };
            }
        } else {
            return new Item[0];
        }
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_TOUCH) { //type == Level.BLOCK_UPDATE_NORMAL ||
            this.getLevel().setBlock(this, new BlockOreRedstone(this.meta), false, false);

            return Level.BLOCK_UPDATE_WEAK;
        }

        return 0;
    }

    @Override
    public int getDropExp() {
        return new NukkitRandom().nextRange(1, 5);
    }
}
