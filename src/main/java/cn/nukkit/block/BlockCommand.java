package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class BlockCommand extends BlockSolid {

    public BlockCommand() {
        this(0);
    }

    public BlockCommand(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Command Block";
    }

    @Override
    public int getId() {
        return COMMAND_BLOCK;
    }

    @Override
    public boolean isBreakable(Item item) {
        return false;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO
        return false;
    }

    @Override
    public double getHardness() {
        return -1;
    }

    @Override
    public double getResistance() {
        return 18000000;
    }
}
