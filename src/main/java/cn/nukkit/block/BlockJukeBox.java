package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;

public class BlockJukeBox extends BlockSolid {

    public BlockJukeBox() {
        this(0);
    }

    public BlockJukeBox(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "JukeBox";
    }

    @Override
    public int getId() {
        return JUKEBOX;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 30;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{ toItem() };
    }
}
