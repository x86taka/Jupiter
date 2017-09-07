package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class BlockCommandChain extends BlockCommand {

    public BlockCommandChain() {
        this(0);
    }

    public BlockCommandChain(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Chain Command Block";
    }

    @Override
    public int getId() {
        return CHAIN_COMMAND_BLOCK;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO
        return false;
    }
}
